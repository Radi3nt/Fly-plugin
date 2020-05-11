package fr.radi3nt.fly.timer;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.FlyAlert;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class checker extends BukkitRunnable {

    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;
    ArrayList<String> flyers = Fly.flyers;
    Plugin plugin = MainFly.getPlugin(MainFly.class);
    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);



    String TimeHigh = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("timefly-high") + ChatColor.RESET);
    String TimeMedium = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("timefly-medium") + ChatColor.RESET);
    String TimeLow = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("timefly-low") + ChatColor.RESET);

    String TimeLeftMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tempfly-timeleft") + ChatColor.RESET);
    String NoTimeLeftMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tempfly-notimeleft") + ChatColor.RESET);


    Sound SoundHigh = Sound.valueOf(plugin.getConfig().getString("temp-sound-high"));
    Sound SoundMedium = Sound.valueOf(plugin.getConfig().getString("temp-sound-medium"));
    Sound SoundLow = Sound.valueOf(plugin.getConfig().getString("temp-sound-low"));
    Sound SoundLast = Sound.valueOf(plugin.getConfig().getString("temp-sound-last"));
    Sound SoundNo = Sound.valueOf(plugin.getConfig().getString("temp-sound-no"));


    HashMap<Player, Boolean> NotifyChat = FlyAlert.NotifyChat;
    HashMap<Player, Boolean> NotifyTitle = FlyAlert.NotifyTitle;
    HashMap<Player, Boolean> NotifyBossBar = FlyAlert.NotifyBossBar;
    HashMap<Player, Boolean> NotifySounds = FlyAlert.NotifySounds;

    int timem = 86400;

    @Override
    public void run() {
        File locations = new File("plugins/FlyPlugin", "flyers.yml");
        if (!locations.exists()) {
            try {
                locations.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration loc = YamlConfiguration.loadConfiguration(locations);
        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < list.size(); i++) {
            if (timer.containsKey(list.get(i).getName())) {
                Player player = list.get(i);
                int secondes = time.get(list.get(i).getName());
                long timeleft = ((timer.get(list.get(i).getName()) / 1000) + secondes) - (System.currentTimeMillis() / 1000);
                loc.set("flyers." + player.getName() , timeleft);
                if (timeleft > timem){
                    timem = (int) timeleft;
                }
                if (timeleft == 3600 && timem > 3600) {
                    High(player, timeleft);
                    timem = 3600;
                }
                if (timeleft == 1800 && timem > 1800) {
                    High(player, timeleft);
                    timem = 1800;
                }
                if (timeleft == 900 && timem > 900) {
                    High(player, timeleft);
                    timem = 900;
                }
                if (timeleft == 300 && timem > 300) {
                    High(player, timeleft);
                    timem = 300;
                }
                if (timeleft == 60 && timem > 60) {
                    Medium(player, timeleft);
                    timem = 60;
                }
                if (timeleft == 30 && timem > 30) {
                    Medium(player, timeleft);
                    timem = 30;
                }
                if (timeleft == 15 && timem > 15) {
                    Medium(player, timeleft);
                    timem = 15;
                }
                if (timeleft == 5 && timem > 5) {
                    Medium(player, timeleft);
                    timem = 5;
                }
                if (timeleft == 3 && timem > 3) {
                    Low(player, timeleft);
                    timem = 3;
                }
                if (timeleft == 2 && timem > 2) {
                    Low(player, timeleft);
                    timem = 2;
                }
                if (timeleft == 1 && timem > 1) {
                    Low(player, timeleft);
                    timem = 1;
                }
                if (timeleft <= 0) {
                    loc.set("flyers." + player.getName() , null);
                    String NoTimeLeftU = NoTimeLeftMessage.toUpperCase();
                    player.sendTitle(NoTimeLeftU, "", 20,30,20);
                    player.sendMessage(Prefix + " " + NoTimeLeftMessage);
                    player.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.playSound(player.getLocation(), SoundNo, 100, 1);
                    flyers.remove(player.getName());
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setInvulnerable(false);
                    Tempfly.timer.remove(player.getName());
                    timer.remove(player.getName());
                    Tempfly.time.remove(player.getName());
                    time.remove(player.getName());
                    timem = 100;
                }
            }
        }
        try {
            loc.save(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PlayHighSound(Player player) {
        player.playSound(player.getLocation(), SoundHigh, SoundCategory.AMBIENT, 100, (float) 1);
    }

    public void PlayMediumSound(Player player) {
        player.playSound(player.getLocation(), SoundMedium, SoundCategory.AMBIENT, 100, (float) 1);
    }

    public void PlayLowSound(Player player) {
        player.playSound(player.getLocation(), SoundLow, SoundCategory.AMBIENT, 100, (float) 1);
    }


    public void High(Player player, long timeleft) {


        if (NotifySounds.get(player)) {
            PlayHighSound(player);
        }
        int heures = (int) (timeleft / 3600);
        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
        int seconds = (int) (timeleft - (heures*3600 + minutes*60));
        if (timeleft<3600) {
            if (NotifyChat.get(player)) {
                String TimeleftR = TimeMedium.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                player.sendMessage(Prefix + " " + TimeleftR);
            }
        } else {
            if (NotifyChat.get(player)) {
                String TimeleftR = TimeHigh.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                player.sendMessage(Prefix + " " + TimeleftR);
            }
        }

    }

    public void Medium(Player player, long timeleft) {


        if (NotifySounds.get(player)) {
            PlayMediumSound(player);
        }
        int heures = (int) (timeleft / 3600);
        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
        int seconds = (int) (timeleft - (heures*3600 + minutes*60));
        if (timeleft<60) {
            if (NotifyChat.get(player)) {
                String TimeleftR = TimeLow.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                player.sendMessage(Prefix + " " + TimeleftR);
            }
            if (NotifyTitle.get(player)) {
                player.sendTitle(TimeLeftMessage, ChatColor.GOLD + String.valueOf(timeleft), 20, 30, 20);
            }
        } else {
            if (NotifyChat.get(player)) {
                String TimeleftR = TimeMedium.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                player.sendMessage(Prefix + " " + TimeleftR);
            }
            if (NotifyTitle.get(player)) {
                player.sendTitle(TimeLeftMessage, ChatColor.GOLD + String.valueOf(timeleft), 20, 30, 20);
            }
        }

    }


    public void Low(Player player, long timeleft) {


        if (NotifySounds.get(player)) {
            PlayLowSound(player);
        }
        int heures = (int) (timeleft / 3600);
        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
        int seconds = (int) (timeleft - (heures*3600 + minutes*60));
        if (NotifyChat.get(player)) {
            String TimeleftR = TimeLow.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
            player.sendMessage(Prefix + " " + TimeleftR);
        }
        if (NotifyTitle.get(player)) {
            player.sendTitle(TimeLeftMessage, ChatColor.RED + String.valueOf(timeleft), 20,30,20);
        }

    }


}
