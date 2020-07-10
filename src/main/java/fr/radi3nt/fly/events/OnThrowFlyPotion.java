package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import fr.radi3nt.fly.timer.TempCheck;
import fr.radi3nt.fly.utilis.Reason;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static fr.radi3nt.fly.commands.Fly.FlyMethod;

public class OnThrowFlyPotion implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    @EventHandler
    public void OnThrowFlyPotion(PotionSplashEvent e) {

        File locations = new File("plugins/FlyPlugin", "flyers.yml");
        if (!locations.exists()) {
            try {
                locations.createNewFile();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
        FileConfiguration loc = YamlConfiguration.loadConfiguration(locations);


        Boolean Stackable = plugin.getConfig().getBoolean("stack-tempfly");


        if (e.getPotion().getItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Fly Potion ")) {
            if (e.getPotion().getShooter() instanceof Player) {
                if (!((Player) e.getPotion().getShooter()).hasPermission("fly.throwpotion")) {
                    e.setCancelled(true);
                    //No-perm
                }
            }
            for (Entity entity : e.getAffectedEntities()) {
                Double intensity = e.getIntensity((LivingEntity) entity);


                String[] split = e.getPotion().getItem().getItemMeta().getDisplayName().split(" ");
                ArrayList<String> NameSplitted = new ArrayList<>();
                NameSplitted.addAll(Arrays.asList(split));
                int Tsec = 0;
                for (int i = 0; i < NameSplitted.size(); i++) {
                    String name = NameSplitted.get(i);
                    if (name.equals("sec")) {
                        Tsec = Integer.parseInt(NameSplitted.get(i - 1).trim());
                    }
                }
                int Tmin = 0;
                if (NameSplitted.contains("min")) {
                    for (int i = 0; i < NameSplitted.size(); i++) {
                        String name = NameSplitted.get(i);
                        if (name.equals("min")) {
                            Tmin = Integer.parseInt(NameSplitted.get(i - 1).trim());
                        }
                    }
                }
                int Th = 0;
                if (NameSplitted.contains("h")) {
                    for (int i = 0; i < NameSplitted.size(); i++) {
                        String name = NameSplitted.get(i);
                        if (name.equals("h")) {
                            Th = Integer.parseInt(NameSplitted.get(i - 1).trim());
                        }
                    }
                }
                Tmin = Tmin * 60;
                Th = Th * 3600;

                int all = Tsec + Tmin + Th;

                int second = (int) (all * intensity);

                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    if (Stackable) {
                        if (Tempfly.time.containsKey(player.getName())) {
                            Tempfly.time.put(player.getName(), (int) (second + ((Tempfly.timer.get(player.getName()) / 1000) + Tempfly.time.get(player.getName())) - (System.currentTimeMillis() / 1000)));
                        } else {
                            Tempfly.time.put(player.getName(), second);
                        }
                    } else {
                        Tempfly.time.put(player.getName(), second);
                    }
                    Fly.flyers.remove(player.getName());
                    loc.set("flyers." + player.getName(), Tempfly.time.get(player.getName()));
                    FlyMethod(player, true);
                    TempCheck.timem.put(player, 100000);
                    Tempfly.timer.put(player.getName(), System.currentTimeMillis());
                    if (e.getEntity().getShooter() instanceof Player) {
                        MainFly.Logger.logTempFly(player.getAllowFlight(), Tempfly.time.get(player.getName()), (Player) e.getEntity().getShooter(), player, Reason.SPLASH_POTION);
                    } else {
                        MainFly.Logger.logTempFly(player.getAllowFlight(), Tempfly.time.get(player.getName()), player, Reason.SPLASH_POTION);
                    }
                } else {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, second * 4, 1, false, false));
                }
            }
        }
        try {
            loc.save(locations);
        } catch (IOException event) {
            event.printStackTrace();
        }
    }

}
