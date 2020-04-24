package fr.radi3nt.fly.timer;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class checker extends BukkitRunnable {

    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;
    ArrayList<String> flyers = Fly.flyers;
    Plugin plugin = MainFly.getPlugin(MainFly.class);
    String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;


    String TempLeft = plugin.getConfig().getString("temp-left");
    String TempMinute = plugin.getConfig().getString("temp-minutes");
    String TempSecond = plugin.getConfig().getString("temp-seconds");
    String TempHours = plugin.getConfig().getString("temp-hours");

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
                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.sendMessage(Prefix + ChatColor.AQUA + " 1 " + ChatColor.GREEN + TempHours + " " + TempLeft);
                    timem = 3600;
                }
                if (timeleft == 1800 && timem > 1800) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.sendMessage(Prefix + ChatColor.AQUA + " 30 " + ChatColor.GREEN + TempMinute + " " + TempLeft);
                    timem = 1800;
                }
                if (timeleft == 900 && timem > 900) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.sendMessage(Prefix + ChatColor.AQUA + " 15 " + ChatColor.GREEN + TempMinute + " " + TempLeft);
                    timem = 900;
                }
                if (timeleft == 300 && timem > 300) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.sendMessage(Prefix + ChatColor.AQUA + " 5 " + ChatColor.GREEN + TempMinute + " " + TempLeft);
                    timem = 300;
                }
                if (timeleft == 60 && timem > 60) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.sendMessage(Prefix + ChatColor.AQUA + " 1 " + ChatColor.GOLD + TempMinute + " " + TempLeft);
                    player.sendTitle(ChatColor.RED +"Time left:", ChatColor.GOLD + String.valueOf(timeleft/60) + " minute", 20,30,20);
                    timem = 60;
                }
                if (timeleft == 30 && timem > 30) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.sendMessage(Prefix + ChatColor.AQUA + " 30 " + ChatColor.GOLD + TempSecond + " " + TempLeft);
                    player.sendTitle(ChatColor.RED +"Time left:", ChatColor.GOLD + String.valueOf(timeleft), 20,30,20);
                    timem = 30;
                }
                if (timeleft == 15 && timem > 15) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.sendMessage(Prefix + ChatColor.AQUA + " 15 " + ChatColor.GOLD + TempSecond + " " + TempLeft);
                    player.sendTitle(ChatColor.RED +"Time left:", ChatColor.GOLD + String.valueOf(timeleft), 20,30,20);
                    timem = 15;
                }
                if (timeleft == 5 && timem > 5) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.5);
                    player.sendMessage(Prefix + ChatColor.AQUA + " 5 " + ChatColor.GOLD + TempSecond + " " + TempLeft);
                    player.sendTitle(ChatColor.RED +"Time left:", ChatColor.GOLD + String.valueOf(timeleft), 20,30,20);
                    timem = 5;
                }
                if (timeleft == 3 && timem > 3) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.hat", SoundCategory.AMBIENT, 100, (float) 1);
                    player.sendMessage(Prefix + ChatColor.DARK_RED + ChatColor.BOLD + " 3 " + ChatColor.RESET + ChatColor.RED + TempSecond + " " + TempLeft);
                    player.sendTitle(ChatColor.RED +"Time left:", ChatColor.RED + String.valueOf(timeleft), 20,30,20);
                    timem = 3;
                }
                if (timeleft == 2 && timem > 2) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.hat", SoundCategory.AMBIENT, 100, (float) 1);
                    player.sendMessage(Prefix + ChatColor.DARK_RED + ChatColor.BOLD + " 2 " + ChatColor.RESET + ChatColor.RED + TempSecond + " " + TempLeft);
                    player.sendTitle(ChatColor.RED +"Time left:",  ChatColor.RED + String.valueOf(timeleft), 20,30,20);
                    timem = 2;
                }
                if (timeleft == 1 && timem > 1) {
                    player.playSound(player.getLocation(), "minecraft:block.note_block.hat", SoundCategory.AMBIENT, 100, 1);
                    player.sendMessage(Prefix + ChatColor.DARK_RED + ChatColor.BOLD + " 1 " + ChatColor.RESET + ChatColor.RED + TempSecond + " " + TempLeft);
                    player.sendTitle(ChatColor.RED +"Time left:", ChatColor.DARK_RED + String.valueOf(timeleft), 20,30,20);
                    timem = 1;
                }
                if (timeleft <= 0) {
                    player.sendTitle(ChatColor.DARK_RED +"NO TIME LEFT!", "", 20,30,20);
                    player.sendMessage(Prefix + ChatColor.RED +ChatColor.BOLD + " No time left!");
                    player.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 0.1);
                    player.playSound(player.getLocation(), "minecraft:block.bell.use", 100, 1);
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
}
