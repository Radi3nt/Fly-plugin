package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class FlyAlert implements CommandExecutor {

    public static HashMap<Player, Boolean> NotifyChat = new HashMap<>();
    public static HashMap<Player, Boolean> NotifyTitle = new HashMap<>();
    public static HashMap<Player, Boolean> NotifyBossBar = new HashMap<>();
    public static HashMap<Player, Boolean> NotifySounds = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Plugin plugin = MainFly.getPlugin(MainFly.class);

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String NoArgs = plugin.getConfig().getString("no-args");
        String WrongArgs = plugin.getConfig().getString("wrong-args");



        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 1) {
                switch (args[0]) {
                    case "chat":
                        if (args[1].equals("on")) {
                            NotifyChat.remove(player);
                            NotifyChat.put(player, true);
                            sender.sendMessage(Prefix + " " + "Chat enabled"); //TODO CONFIG
                        } else if (args[1].equals("off")) {
                            NotifyChat.remove(player);
                            NotifyChat.put(player, false);
                            sender.sendMessage(Prefix + " " + "Chat disabled"); //TODO CONFIG
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;

                    case "title":
                        if (args[1].equals("on")) {
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, true);
                            sender.sendMessage(Prefix + " " + "Title enabled"); //TODO CONFIG
                        } else if (args[1].equals("off")) {
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, false);
                            sender.sendMessage(Prefix + " " + "Title disabled"); //TODO CONFIG
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;

                    case "sounds":
                        if (args[1].equals("on")) {
                            NotifySounds.remove(player);
                            NotifySounds.put(player, true);
                            sender.sendMessage(Prefix + " " + "Sounds enabled"); //TODO CONFIG
                        } else if (args[1].equals("off")) {
                            NotifySounds.remove(player);
                            NotifySounds.put(player, false);
                            sender.sendMessage(Prefix + " " + "Sounds disabled"); //TODO CONFIG
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;

                    /*/case "bossbar":
                        if (args[1].equals("on")) {
                            NotifyBossBar.remove(player);
                            NotifyBossBar.put(player, true);
                            sender.sendMessage(Prefix + " " + "Bar enabled"); //TODO CONFIG
                        } else if (args[1].equals("off")) {
                            NotifyBossBar.remove(player);
                            NotifyBossBar.put(player, false);
                            sender.sendMessage(Prefix + " " + "Bar disabled"); //TODO CONFIG
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;
                        /*/

                    case "all":
                        if (args[1].equals("on")) {
                            NotifyBossBar.remove(player);
                            NotifyBossBar.put(player, true);
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, true);
                            NotifyChat.remove(player);
                            NotifyChat.put(player, true);
                            NotifySounds.remove(player);
                            NotifySounds.put(player, true);
                            sender.sendMessage(Prefix + " " + "All enabled"); //TODO CONFIG
                        } else if (args[1].equals("off")) {
                            NotifyBossBar.remove(player);
                            NotifyBossBar.put(player, false);
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, false);
                            NotifyChat.remove(player);
                            NotifyChat.put(player, false);
                            NotifySounds.remove(player);
                            NotifySounds.put(player, false);
                            sender.sendMessage(Prefix + " " + "All disabled"); //TODO CONFIG
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;
                    default:
                        player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        break;
                }
            } else {
                player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
            }
        } else {
            sender.sendMessage(Prefix + ChatColor.RED +" This command MUST be run by a player");
        }
        return true;
    }
}
