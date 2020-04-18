package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FlyReload implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player =(Player) sender;

        String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;
        String NoPermission = plugin.getConfig().getString("no-permission");
        String ReloadMessage = plugin.getConfig().getString("reload-message");


        if (player.hasPermission("fly.reload")) {
            plugin.reloadConfig();
            player.sendMessage(Prefix + " " + ReloadMessage);
            System.out.println(Prefix + " " + ReloadMessage);
            return true;
        } else {
            player.sendMessage( Prefix + " " + ChatColor.RED + NoPermission);
            return false;
        }
    }
}
