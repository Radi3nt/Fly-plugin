package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class GetFlyTime implements CommandExecutor {

    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;

    Plugin plugin = MainFly.getPlugin(MainFly.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String InvalidPlayer = plugin.getConfig().getString("invalid-player");

        String NoFlyYou = plugin.getConfig().getString("timefly-nofly-you");
        String NoFlyHe = plugin.getConfig().getString("timefly-nofly-target");

        String NoPermission = plugin.getConfig().getString("no-permission");

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);


        String Timeleft = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("timefly-structure") + ChatColor.RESET);



        if (args.length == 1) {
            if (sender.hasPermission("fly.timeflyothers")) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target instanceof Player) {
                    if (timer.containsKey(target.getName())) {
                        int secondes = time.get(target.getName());
                        long timeleft = ((timer.get(target.getName()) / 1000) + secondes) - (System.currentTimeMillis() / 1000);
                        int heures = (int) (timeleft / 3600);
                        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
                        int seconds = (int) (timeleft - (heures*3600 + minutes*60));
                        String TimeleftR = Timeleft.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                        sender.sendMessage(Prefix + " " + TimeleftR);
                    } else {
                        sender.sendMessage(Prefix + " " + ChatColor.RED + NoFlyHe);
                    }

                } else {
                    sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                    sender.sendMessage(ChatColor.RED + "/timefly [joueur]");
                }
            } else {
                sender.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(player.hasPermission("fly.timefly")) {
                    if (timer.containsKey(sender.getName())) {
                        int secondes = time.get(player.getName());
                        long timeleft = ((timer.get(player.getName()) / 1000) + secondes) - (System.currentTimeMillis() / 1000);
                        int heures = (int) (timeleft / 3600);
                        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
                        int seconds = (int) (timeleft - (heures*3600 + minutes*60));
                        String TimeleftR = Timeleft.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                        player.sendMessage(Prefix + " " + TimeleftR);
                    } else {
                        player.sendMessage(Prefix + " " + ChatColor.RED + NoFlyYou);
                    }
                } else {
                    player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                }
            } else {
                sender.sendMessage(Prefix + " " + ChatColor.RED + InvalidPlayer);
            }
        }
        return true;
    }
}
