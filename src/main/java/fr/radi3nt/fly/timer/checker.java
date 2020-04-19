package fr.radi3nt.fly.timer;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    int timem = 100;

    @Override
    public void run() {
        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < list.size(); i++){
            if (timer.containsKey(list.get(i).getName())) {
                Player player = list.get(i);
                int secondes = time.get(list.get(i).getName());
                long timeleft = ((timer.get(list.get(i).getName()) / 1000) + secondes) - (System.currentTimeMillis() / 1000);

                if (timeleft == 30 && timem > 30) {
                    player.sendMessage(Prefix + ChatColor.AQUA + " 30 " + ChatColor.GREEN + "seconds of flight left!");
                    timem = 30;
                }
                if (timeleft == 15 && timem > 15) {
                    player.sendMessage(Prefix + ChatColor.AQUA + " 15 " + ChatColor.GREEN + "seconds of flight left!");
                    timem = 15;
                }
                if (timeleft == 5 && timem > 5) {
                    player.sendMessage(Prefix + ChatColor.AQUA + " 5 " + ChatColor.GOLD + "seconds of flight left!");
                    timem = 5;
                }
                if (timeleft == 3 && timem > 3) {
                    player.sendMessage(Prefix + ChatColor.DARK_RED + ChatColor.BOLD + " 3 " + ChatColor.RESET + ChatColor.RED + "seconds of flight left!");
                    timem = 3;
                }
                if (timeleft == 2 && timem > 2) {
                    player.sendMessage(Prefix + ChatColor.DARK_RED + ChatColor.BOLD + " 2 " + ChatColor.RESET + ChatColor.RED + "seconds of flight left!");
                    timem = 2;
                }
                if (timeleft == 1 && timem > 1) {
                    player.sendMessage(Prefix + ChatColor.DARK_RED + ChatColor.BOLD + " 1 " + ChatColor.RESET + ChatColor.RED + "seconds of flight left!");
                    timem = 1;
                }
                if (timeleft < 0) {
                    player.sendMessage(Prefix + ChatColor.RED + ChatColor.BOLD + " No time left!");
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setInvulnerable(false);
                    Tempfly.timer.remove(player.getName());
                    timer.remove(player.getName());
                    Tempfly.time.remove(player.getName());
                    time.remove(player.getName());
                    timem = 100;
                    flyers.remove(player);
                    break;
                }
            }
        }
    }
}
