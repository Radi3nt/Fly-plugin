package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

import static fr.radi3nt.fly.events.OnGroundHit.GroundHitters;

public class Fly implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);
    public static ArrayList<String> flyers = new ArrayList<>();



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String InvalidPlayer = plugin.getConfig().getString("invalid-player");
        String NoPermission = plugin.getConfig().getString("no-permission");

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("fly.fly")) {
                if (args.length == 0) {

                    PlayerFly(player);

                } else if (player.hasPermission("fly.others")) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target instanceof Player) {

                        TargetFly(target, player);

                    } else {
                        player.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                        player.sendMessage(ChatColor.RED + "/fly [joueur]");
                    }
                } else {
                    player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                }

            } else {
                player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
            }

        } else {
            if (!(args.length == 0)) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target instanceof Player) {
                    String On = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("on-state")) + ChatColor.RESET;
                    String Off = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("off-state")) + ChatColor.RESET;
                    Boolean TargetSendMessage = plugin.getConfig().getBoolean("fly-target-message");
                    String FlySomeoneTarget = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-target")) + ChatColor.RESET;
                    String FlySomeonePlayer = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-player")) + ChatColor.RESET;
                    if (!target.getAllowFlight()) {
                        FlyMethod(target, true);
                        String FSPr = FlySomeonePlayer.replace("%state%", On).replace("%target%", target.getName()).replace("%player%", "console");
                        sender.sendMessage(Prefix + " " + FSPr);
                        if (TargetSendMessage) {
                            String FSTr = FlySomeoneTarget.replace("%state%", On).replace("%target%", target.getName()).replace("%player%","console");
                            target.sendMessage(Prefix + " " + FSTr);
                        }
                    } else {
                        FlyMethod(target, false);
                        String FSPr = FlySomeonePlayer.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", "console");
                        sender.sendMessage(Prefix + " " + FSPr);
                        if (TargetSendMessage) {
                            String FSTr = FlySomeoneTarget.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", "console");
                            target.sendMessage(Prefix + " " + FSTr);
                        }
                    }
                } else {
                    sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                    sender.sendMessage(ChatColor.RED + "/fly <joueur>");
                }
            } else {
                sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                sender.sendMessage(ChatColor.RED + "/fly <joueur>");
            }
        }
        return true;
    }

    public void PlayerFly(Player player) {
        String On = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("on-state")) + ChatColor.RESET;
        String Off = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("off-state")) + ChatColor.RESET;
        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String FlyYoureself = plugin.getConfig().getString("fly-yourself");
        if (!player.getAllowFlight()) {
            FlyMethod(player, true);
            String FYr = FlyYoureself.replace("%state%", On);
            player.sendMessage(Prefix + " " + FYr);
        } else {
            FlyMethod(player, false);
            String FYr = FlyYoureself.replace("%state%", Off);
            player.sendMessage(Prefix + " " + FYr);
        }
    }


    public void TargetFly(Player target, Player player) {
        String On = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("on-state")) + ChatColor.RESET;
        String Off = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("off-state")) + ChatColor.RESET;
        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        Boolean TargetSendMessage = plugin.getConfig().getBoolean("fly-target-message");
        String FlySomeoneTarget = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-target")) + ChatColor.RESET;
        String FlySomeonePlayer = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-player")) + ChatColor.RESET;
        if (!target.getAllowFlight()) {
            FlyMethod(target, true);
            String FSPr = FlySomeonePlayer.replace("%state%", On).replace("%target%", target.getName()).replace("%player%", player.getName());
            player.sendMessage(Prefix + " " + FSPr);
            if (TargetSendMessage) {
                String FSTr = FlySomeoneTarget.replace("%state%", On).replace("%target%", target.getName()).replace("%player%", player.getName());
                target.sendMessage(Prefix + " " + FSTr);
            }
        } else {
            FlyMethod(target, false);
            GroundHitters.add(player);
            String FSPr = FlySomeonePlayer.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", player.getName());
            player.sendMessage(Prefix + " " + FSPr);
            if (TargetSendMessage) {
                String FSTr = FlySomeoneTarget.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", player.getName());
                target.sendMessage(Prefix + " " + FSTr);
            }
        }
    }


    public static void FlyMethod(Player player, boolean state) {
        player.setAllowFlight(state);
        if (state) {
            flyers.remove(player.getName());
            flyers.add(player.getName());
        } else {
            flyers.remove(player.getName());
            player.setInvulnerable(false);
            GroundHitters.add(player);
        }
    }
}