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
import java.util.HashMap;
import java.util.Map;

public class Tempfly implements CommandExecutor {

    public static Map<String, Long> timer = new HashMap<>();
    public static Map<String, Integer> time = new HashMap<>();
    public ArrayList<String> flyers = Fly.flyers;


    Plugin plugin = MainFly.getPlugin(MainFly.class);

    String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;
    String WrongArgs = plugin.getConfig().getString("wrong-args");
    String InvalidPlayer = plugin.getConfig().getString("invalid-player");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("fly.tempfly")) {
            if (args.length < 2) {
                player.sendMessage(Prefix + " " + WrongArgs);
            } else {
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

                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target instanceof Player) {
                        time.put(target.getName(), Integer.parseInt((sb.toString())));
                            if (time.get(target.getName()) > 0 && time.get(target.getName()) < 86400) {
                                FlyMethod(target, true);
                                player.sendMessage(Prefix + " " + target.getName() + " can fly for " + ChatColor.AQUA + time.get(target.getName()) + " seconds");
                                timer.put(target.getName(), System.currentTimeMillis());
                            } else {
                                player.sendMessage(Prefix + ChatColor.RED + WrongArgs);
                            }

                    } else {
                        player.sendMessage(Prefix + ChatColor.RED + InvalidPlayer);
                    }
            }
        }
        return true;
    }


    public void FlyMethod(Player player, boolean state) {
        player.setAllowFlight(state);
        if (state == true) {
            flyers.add(player.getName()); // this will be useless for the next update !
        } else {
            flyers.remove(player.getName());
            player.setInvulnerable(false);
        }
    }

}


