package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Flyers implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    public ArrayList<String> flyers = Fly.flyers;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String NoPermission = plugin.getConfig().getString("no-permission");
        String NoOnePerm = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyers-noone")) + ChatColor.RESET;

        int shift;
            if (sender.hasPermission("fly.flyers")) {
                if (flyers.size() == 0) {
                    sender.sendMessage(Prefix + " " + NoOnePerm);
                }
                if (flyers.size() == 1) {
                    sender.sendMessage(Prefix + " " + flyers.get(0));
                }
                if (flyers.size() == 2) {
                    sender.sendMessage(Prefix + " " + flyers.get(0) + ", " + flyers.get(1));
                }
                if (flyers.size() == 3) {
                    sender.sendMessage(Prefix + " " + flyers.get(0) + ", " + flyers.get(1) + ", " + flyers.get(2));
                }
                if (flyers.size() == 4) {
                    sender.sendMessage(Prefix + " " + flyers.get(0) + ", " + flyers.get(1) + ", " + flyers.get(2) + ", " + flyers.get(3));
                }
                if (flyers.size() > 5) {
                    sender.sendMessage(Prefix + " " + flyers.get(0) + ", " + flyers.get(1) + ", " + flyers.get(2) + ", " + flyers.get(3) + ", " + flyers.get(4));
                }
                for (shift = 5; shift <= flyers.size(); shift++) {
                    if (flyers.size() == 1 + shift) {
                        sender.sendMessage(Prefix + " " + flyers.get(shift));
                    }
                    if (flyers.size() == 2 + shift) {
                        sender.sendMessage(Prefix + " " + flyers.get(shift) + ", " + flyers.get(1 + shift));
                    }
                    if (flyers.size() == 3 + shift) {
                        sender.sendMessage(Prefix + " " + flyers.get(shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift));
                    }
                    if (flyers.size() == 4 + shift) {
                        sender.sendMessage(Prefix + " " + flyers.get(shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift));
                    }
                    if (flyers.size() > 5 + shift) {
                        sender.sendMessage(Prefix + " " + flyers.get(shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift) + ", " + flyers.get(4 + shift));
                    }
                }
            } else {
                sender.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
            }
        return true;
    }
}
