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

        if (sender instanceof Player){

            String NoPermission = plugin.getConfig().getString("no-permission");
            String NoArgs = plugin.getConfig().getString("no-args");
            String WrongArgs = plugin.getConfig().getString("wrong-args");
            String PlayerMessage = plugin.getConfig().getString("speed-player-message");
            String PlayerSomeoneMessage = plugin.getConfig().getString("speed-someone-player");
            String TargetMessageReval = plugin.getConfig().getString("speed-target-namereval");
            String TargetMessage = plugin.getConfig().getString("speed-target");
            Boolean SpeedTargetMessage = plugin.getConfig().getBoolean("speed-target-message");
            Boolean SpeedPlayerNameReval = plugin.getConfig().getBoolean("speed-player-name-reval");

            String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;

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
                        player.sendMessage(ChatColor.RED + WrongArgs);
                        return false;
                    }
                    float FlySpeedf = Float.parseFloat(sb.toString());

                    if (FlySpeedf > 0 && FlySpeedf < 10) {

                        if (args.length > 1) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            target.setFlySpeed(FlySpeedf / 10);
                            int FlySpeedI = (int) FlySpeedf;
                            if (player.hasPermission("fly.speed." + FlySpeedI)) {
                                player.sendMessage(Prefix + " " + PlayerSomeoneMessage + " " + target.getName() + " to " + ChatColor.DARK_AQUA + FlySpeedI);
                                if (SpeedTargetMessage) {
                                    if (SpeedPlayerNameReval) {
                                        target.sendMessage(Prefix + " " + player.getName() + " " + TargetMessageReval + " " + ChatColor.DARK_AQUA + FlySpeedI);
                                    } else {
                                        target.sendMessage(Prefix + " " + TargetMessage + " " + ChatColor.DARK_AQUA + FlySpeedI);
                                    }
                                }
                            } else {
                                player.sendMessage( Prefix + " " + ChatColor.RED + NoPermission);
                            }

                        } else {
                            player.setFlySpeed(FlySpeedf / 10);
                            int FlySpeedI = (int) FlySpeedf;
                            if (player.hasPermission("fly.speed." + FlySpeedI)) {
                                player.sendMessage(Prefix + " " + PlayerMessage + " " + ChatColor.DARK_AQUA + FlySpeedI);
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
            sender.sendMessage("This command must be run by a player !");
        }
        return true;
    }
}
