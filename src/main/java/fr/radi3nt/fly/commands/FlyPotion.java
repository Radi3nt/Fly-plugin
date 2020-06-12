package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

import static fr.radi3nt.fly.events.crafts.PrepareCraftItemEvent.CreateFlyPotion;

public class FlyPotion implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String NoPermission = plugin.getConfig().getString("no-permission");
        String NoArgs = plugin.getConfig().getString("no-args");


        if (args.length > 1) {
            ArrayList<String> NameSplitted = new ArrayList<>();
            NameSplitted.addAll(Arrays.asList(args));
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


            ItemStack potion = CreateFlyPotion(Th, Tmin, Tsec);
            if (sender instanceof Player) {
                if (sender.hasPermission("fly.give.flypotion")) {
                    ((Player) sender).getInventory().addItem(potion);
                } else {
                    sender.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                }
            } else {
                sender.sendMessage(Prefix + " " + ChatColor.RED + "Command must be run by a player");
            }
        } else {
            sender.sendMessage(Prefix + " " + ChatColor.RED + NoArgs);
        }
        return true;
    }
}
