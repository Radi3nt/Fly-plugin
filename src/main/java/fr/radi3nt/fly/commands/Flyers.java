package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import fr.radi3nt.fly.commands.Fly;
import java.util.ArrayList;

public class Flyers implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);
    ArrayList<Player> flyers = Fly.flyers;

    String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;
    String NoPermission = plugin.getConfig().getString("no-permission");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("fly.getflyers")) {
            Player player = Bukkit.getPlayerExact(String.valueOf(flyers.get(0)));
         sender.sendMessage(Prefix + " " + player);
            sender.sendMessage(Prefix + " " + flyers);
        } else {
            sender.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
        }
        return true;
    }
}
