package fr.radi3nt.fly.timer;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.FlyAlert;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

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


    String TempLeft = plugin.getConfig().getString("temp-left");
    String TempMinute = plugin.getConfig().getString("temp-minutes");
    String TempSecond = plugin.getConfig().getString("temp-seconds");
    String TempHours = plugin.getConfig().getString("temp-hours");

    Sound SoundHigh = Sound.valueOf(plugin.getConfig().getString("temp-sound-high"));
    Sound SoundMedium = Sound.valueOf(plugin.getConfig().getString("temp-sound-medium"));
    Sound SoundLow = Sound.valueOf(plugin.getConfig().getString("temp-sound-low"));
    Sound SoundLast = Sound.valueOf(plugin.getConfig().getString("temp-sound-last"));
    Sound SoundNo = Sound.valueOf(plugin.getConfig().getString("temp-sound-no"));


    HashMap<Player, Boolean> NotifyChat = FlyAlert.NotifyChat;
    HashMap<Player, Boolean> NotifyTitle = FlyAlert.NotifyTitle;
    HashMap<Player, Boolean> NotifyBossBar = FlyAlert.NotifyBossBar;

    int timem = 86400;

    @Override
    public void run() {
        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < list.size(); i++) {
            if (timer.containsKey(list.get(i).getName())) {
                Player player = list.get(i);
                int secondes = time.get(list.get(i).getName());
                long timeleft = ((timer.get(list.get(i).getName()) / 1000) + secondes) - (System.currentTimeMillis() / 1000);
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
                    PlayLowSound(player);
                    player.sendMessage(Prefix + ChatColor.DARK_RED + ChatColor.BOLD + " 1 " + ChatColor.RESET + ChatColor.RED + TempSecond + " " + TempLeft);
                    player.sendTitle(ChatColor.RED +"Time left:", ChatColor.DARK_RED + String.valueOf(timeleft), 20,30,20);
                    timem = 1;
                }
                if (timeleft <= 0) {
                    player.sendTitle(ChatColor.DARK_RED +"NO TIME LEFT!", "", 20,30,20);
                    player.sendMessage(Prefix + ChatColor.RED +ChatColor.BOLD + " No time left!");
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


        PlayHighSound(player);
        int heures = (int) (timeleft / 3600);
        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
        if (NotifyChat.get(player)) {
            player.sendMessage(Prefix + " " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) * 3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (timeleft - (heures * 3600 + minutes * 60)) + " " + ChatColor.GREEN + TempSecond + " " + TempLeft);
        }

    }

    public void Medium(Player player, long timeleft) {


        PlayMediumSound(player);
        int heures = (int) (timeleft / 3600);
        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
        if (NotifyChat.get(player)) {
            player.sendMessage(Prefix + " " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GOLD + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) * 3600) / 60) + " " + ChatColor.GOLD + TempMinute + " and " + ChatColor.AQUA + (timeleft - (heures * 3600 + minutes * 60)) + " " + ChatColor.GOLD + TempSecond + " " + TempLeft);
        }
        if (NotifyTitle.get(player)) {
            player.sendTitle(ChatColor.RED +"Time left:", ChatColor.GOLD + String.valueOf(timeleft), 20,30,20); //TODO CONFIG
        }

    }


    public void Low(Player player, long timeleft) {


        PlayLowSound(player);
        int heures = (int) (timeleft / 3600);
        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
        if (NotifyChat.get(player)) {
            player.sendMessage(Prefix + " " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.RED + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) * 3600) / 60) + " " + ChatColor.RED + TempMinute + " and " + ChatColor.AQUA + (timeleft - (heures * 3600 + minutes * 60)) + " " + ChatColor.RED + TempSecond + " " + TempLeft);
        }
        if (NotifyTitle.get(player)) {
            player.sendTitle(ChatColor.DARK_RED +"Time left:", ChatColor.RED + String.valueOf(timeleft), 20,30,20);
        }

    }


}
