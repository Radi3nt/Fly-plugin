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

public class Fly implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);
    public static ArrayList<Player> flyers = new ArrayList<>();

    String FlyYoureself = plugin.getConfig().getString("fly-youreself");
    String InvalidPlayer = plugin.getConfig().getString("invalid-player");
    String NoPermission = plugin.getConfig().getString("no-permission");
    String FlySomeonePlayer = plugin.getConfig().getString("fly-someone-player");
    String FlySomeoneTarget = plugin.getConfig().getString("fly-someone-target");
    Boolean PlayerNameReval = plugin.getConfig().getBoolean("fly-player-name-reval");
    Boolean TargetSendMessage = plugin.getConfig().getBoolean("fly-target-message");

    String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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
                        player.sendMessage(Prefix + ChatColor.RED + InvalidPlayer);
                        player.sendMessage(ChatColor.RED + "/fly [joueur]");
                    }
                } else {
                    player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                }

            } else {
                player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
            }

        } else {
            sender.sendMessage("This command must be run by a player !");
        }
        return true;
    }

    public void PlayerFly(Player player) {
        if (!player.getAllowFlight()) {
            FlyMethod(player, true);
            player.sendMessage(Prefix + " " + FlyYoureself + " on");
        } else {
            FlyMethod(player, false);
            player.sendMessage(Prefix + " " + FlyYoureself + " off");
        }
    }


    public void TargetFly(Player target, Player player) {
        if (!target.getAllowFlight()) {
            FlyMethod(target, true);
            player.sendMessage(Prefix + " " + FlySomeonePlayer + " on for " + target.getName());
            if (TargetSendMessage) {
                if (!PlayerNameReval) {
                    target.sendMessage(Prefix + " " + FlySomeoneTarget + " on");
                } else {
                    target.sendMessage(Prefix + " " + FlySomeoneTarget + " on by " + " " + player.getName());
                }
            }
        } else {
            FlyMethod(target, false);
            player.sendMessage(Prefix + " " + FlySomeonePlayer + " off for " + target.getName());
            if (TargetSendMessage) {
                if (!PlayerNameReval) {
                    target.sendMessage(Prefix + " " + FlySomeoneTarget + " off");
                } else {
                    target.sendMessage(Prefix + " " + FlySomeoneTarget + " off by " + player.getName());
                }
            }
        }
    }


    public void FlyMethod(Player player, boolean state) {
        player.setAllowFlight(state);
        if (state == true) {
            flyers.add(player); // this will be useless for the next update !
        } else {
            flyers.remove(player);
            player.setInvulnerable(false);
        }
    }
}