package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FlySpeed implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String NoPermission = plugin.getConfig().getString("no-permission");
        String NoArgs = plugin.getConfig().getString("no-args");
        String WrongArgs = plugin.getConfig().getString("wrong-args");
        String PlayerMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("speed-player-message")) + ChatColor.RESET;
        String PlayerSomeoneMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("speed-someone-player")) + ChatColor.RESET;
        String TargetMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("speed-target")) + ChatColor.RESET;
        String InvalidPlayer = plugin.getConfig().getString("invalid-player");
        Boolean SpeedTargetMessage = plugin.getConfig().getBoolean("speed-target-message");


        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);




        if (sender instanceof Player){

            Player player = (Player) sender;
            if (player.hasPermission("fly.speed")) {
                if (args.length >= 1) {
                    StringBuilder sb = new StringBuilder();
                    boolean found = false;
                    for(char c : args[0].toCharArray()){
                        if(Character.isDigit(c)){
                            sb.append(c);
                            found = true;
                        } else if(found){
                            // If we already found a digit before and this char is not a digit, stop looping
                            break;
                        }
                    }

                    if(sb.toString().isEmpty()) {
                        player.sendMessage(ChatColor.RED + " " + WrongArgs);
                        return false;
                    }
                    float FlySpeedf = Float.parseFloat(sb.toString());

                    if (FlySpeedf > 0 && FlySpeedf < 10) {
                        if (args.length > 1) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (target instanceof Player) {
                                int FlySpeedI = (int) FlySpeedf;
                                if (player.hasPermission("fly.others")) {
                                    if (player.hasPermission("fly.speed." + FlySpeedI)) {
                                        target.setFlySpeed(FlySpeedf / 10);
                                        String PlayerSomeoneMessageR = PlayerSomeoneMessage.replace("%target%", target.getName()).replace("%speed%", String.valueOf(FlySpeedI));
                                        player.sendMessage(Prefix + " " + PlayerSomeoneMessageR);
                                        if (SpeedTargetMessage) {
                                            String TargetMessageR = TargetMessage.replace("%player%", player.getName()).replace("%speed%", String.valueOf(FlySpeedI));
                                            target.sendMessage(Prefix + " " + TargetMessageR);
                                        }
                                    } else {
                                        player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                                    }
                                } else {
                                    player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                                }
                            } else {
                                player.sendMessage(Prefix + " " + InvalidPlayer);
                            }

                        } else {
                            int FlySpeedI = (int) FlySpeedf;
                            if (player.hasPermission("fly.speed." + FlySpeedI)) {
                                String PlayerMessageR = PlayerMessage.replace("%speed%", String.valueOf(FlySpeedI));
                                player.setFlySpeed(FlySpeedf / 10);
                                player.sendMessage(Prefix + " " + PlayerMessageR);
                            } else {
                                player.sendMessage( Prefix + " " + ChatColor.RED + NoPermission);
                            }
                        }
                    } else {
                        player.sendMessage( Prefix + " " + ChatColor.RED + WrongArgs);
                        return false;
                    }
                } else {
                    player.sendMessage(Prefix + " " + ChatColor.RED + NoArgs);
                    return false;
                }
            } else {
                player.sendMessage( Prefix + " " + ChatColor.RED + NoPermission);
            }

        } else {
            if (args.length >= 1) {
                StringBuilder sb = new StringBuilder();
                boolean found = false;
                for(char c : args[0].toCharArray()){
                    if(Character.isDigit(c)){
                        sb.append(c);
                        found = true;
                    } else if(found){
                        // If we already found a digit before and this char is not a digit, stop looping
                        break;
                    }
                }

                if(sb.toString().isEmpty()) {
                    sender.sendMessage(ChatColor.RED + " " + WrongArgs);
                    return false;
                }
                float FlySpeedf = Float.parseFloat(sb.toString());

                if (FlySpeedf > 0 && FlySpeedf < 10) {

                    if (args.length > 1) {
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target instanceof Player) {
                            target.setFlySpeed(FlySpeedf / 10);
                            int FlySpeedI = (int) FlySpeedf;
                            String PlayerSomeoneMessageR = PlayerSomeoneMessage.replace("%target%", target.getName()).replace("%speed%", String.valueOf(FlySpeedI));
                            sender.sendMessage(Prefix + " " + PlayerSomeoneMessageR);
                            if (SpeedTargetMessage) {
                                String TargetMessageR = TargetMessage.replace("%player%", "console").replace("%speed%", String.valueOf(FlySpeedI));
                                target.sendMessage(Prefix + " " + TargetMessageR);
                            }
                                } else {
                                    sender.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                                }
                            } else {
                                sender.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                            }
                        } else {
                            sender.sendMessage(Prefix + " " + ChatColor.RED + InvalidPlayer);
                        }

                    } else {
                sender.sendMessage(Prefix + " " + ChatColor.RED + NoArgs);
            }
        }
        return true;
    }
}
