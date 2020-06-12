package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import fr.radi3nt.fly.timer.TempCheck;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static fr.radi3nt.fly.commands.Fly.FlyMethod;

public class OnDrinkFlyPotion implements Listener {

    @EventHandler
    public void OnDrinkFlyPotion(PlayerItemConsumeEvent e) {
        if (MainFly.getPlugin(MainFly.class).getConfig().getBoolean("fly-potions")) {

            if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Fly Potion ")) {

                String[] split = e.getItem().getItemMeta().getDisplayName().split(" ");
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


                if (all != 0) {
                    Plugin plugin = MainFly.getPlugin(MainFly.class);
                    Map<String, Long> timer = Tempfly.timer;
                    Map<String, Integer> time = Tempfly.time;
                    ArrayList<String> flyers = Fly.flyers;
                    HashMap<Player, Integer> timem = TempCheck.timem;
                    Boolean Stackable = plugin.getConfig().getBoolean("stack-tempfly");



                    File locations = new File("plugins/FlyPlugin", "flyers.yml");
                    if (!locations.exists()) {
                        try {
                            locations.createNewFile();
                        } catch (IOException event) {
                            event.printStackTrace();
                        }
                    }
                    FileConfiguration loc = YamlConfiguration.loadConfiguration(locations);


                    Player player = e.getPlayer();
                    if (Stackable) {
                        if (time.containsKey(player.getName())) {
                            time.put(player.getName(), (int) (all + ((timer.get(player.getName()) / 1000) + time.get(player.getName())) - (System.currentTimeMillis() / 1000)));
                        } else {
                            time.put(player.getName(), all);
                        }
                    } else {
                        time.put(player.getName(), all);
                    }
                    flyers.remove(player.getName());
                    loc.set("flyers." + player.getName(), time.get(player.getName()));
                    FlyMethod(player, true);
                    timem.put(player, 100000);
                    timer.put(player.getName(), System.currentTimeMillis());
                }

            }
        }
    }

}
