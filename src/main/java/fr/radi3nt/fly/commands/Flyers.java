package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Flyers implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;

    public ArrayList<String> flyers = Fly.flyers;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int shift;
        if (sender instanceof Player) {
            if (flyers.size() == 0) {
                sender.sendMessage(Prefix + " No one has the permission to fly !");
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
            shift = 5;
            if (flyers.size() == 1 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift));
            }
            if (flyers.size() == 2 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift));
            }
            if (flyers.size() == 3 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift));
            }
            if (flyers.size() == 4 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift));
            }
            if (flyers.size() > 5 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift) + ", " + flyers.get(4 + shift));
            }
            shift = 10;
            if (flyers.size() == 1 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift));
            }
            if (flyers.size() == 2 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift));
            }
            if (flyers.size() == 3 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift));
            }
            if (flyers.size() == 4 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift));
            }
            if (flyers.size() > 5 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift) + ", " + flyers.get(4 + shift));
            }
            shift = 15;
            if (flyers.size() == 1 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift));
            }
            if (flyers.size() == 2 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift));
            }
            if (flyers.size() == 3 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift));
            }
            if (flyers.size() == 4 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift));
            }
            if (flyers.size() > 5 + shift) {
                sender.sendMessage(Prefix + " " + flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift) + ", " + flyers.get(4 + shift));
            }
        } else {
            if (flyers.size() == 0) {
                System.out.println("No one has the permission to fly !");
            }
            if (flyers.size() == 1) {
                System.out.println(flyers.get(0));
            }
            if (flyers.size() == 2) {
                System.out.println(flyers.get(0) + ", " + flyers.get(1));
            }
            if (flyers.size() == 3) {
                System.out.println(flyers.get(0) + ", " + flyers.get(1) + ", " + flyers.get(2));
            }
            if (flyers.size() == 4) {
                System.out.println(flyers.get(0) + ", " + flyers.get(1) + ", " + flyers.get(2) + ", " + flyers.get(3));
            }
            if (flyers.size() > 5) {
                System.out.println(flyers.get(0) + ", " + flyers.get(1) + ", " + flyers.get(2) + ", " + flyers.get(3) + ", " + flyers.get(4));
            }
            shift = 5;
            if (flyers.size() == 1 + shift) {
                System.out.println(flyers.get(0 + shift));
            }
            if (flyers.size() == 2 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift));
            }
            if (flyers.size() == 3 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift));
            }
            if (flyers.size() == 4 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift));
            }
            if (flyers.size() > 5 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift) + ", " + flyers.get(4 + shift));
            }
            shift = 10;
            if (flyers.size() == 1 + shift) {
                System.out.println(flyers.get(0 + shift));
            }
            if (flyers.size() == 2 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift));
            }
            if (flyers.size() == 3 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift));
            }
            if (flyers.size() == 4 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift));
            }
            if (flyers.size() > 5 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift) + ", " + flyers.get(4 + shift));
            }
            shift = 15;
            if (flyers.size() == 1 + shift) {
                System.out.println(flyers.get(0 + shift));
            }
            if (flyers.size() == 2 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift));
            }
            if (flyers.size() == 3 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift));
            }
            if (flyers.size() == 4 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift));
            }
            if (flyers.size() > 5 + shift) {
                System.out.println(flyers.get(0 + shift) + ", " + flyers.get(1 + shift) + ", " + flyers.get(2 + shift) + ", " + flyers.get(3 + shift) + ", " + flyers.get(4 + shift));
            }
        }
        return true;
    }
}
