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

    String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;

    public ArrayList<String> flyers = Fly.flyers;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(Prefix + " "+ String.valueOf(flyers));
        return true;
    }
}
