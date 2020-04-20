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
    String NoPermission = plugin.getConfig().getString("no-permission");

    String NameReveal = plugin.getConfig().getString("temp-target-namereveal");
    String TargetMe = plugin.getConfig().getString("temp-target");

    Boolean TargetMessage = plugin.getConfig().getBoolean("temp-target-message");
    Boolean PlayerNameReveal = plugin.getConfig().getBoolean("temp-player-name-reveal");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Not availaible yet");
            return false;
        }
        Player player = (Player) sender;
        if (player.hasPermission("fly.tempfly")) {
            if (args.length < 1) {
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
                    if (!(args.length == 1)) {
                        if (player.hasPermission("fly.tempflyothers")) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (target instanceof Player) {
                                time.put(target.getName(), Integer.parseInt((sb.toString())));
                                if (time.get(target.getName()) > 0 && time.get(target.getName()) < 86400) {
                                    flyers.remove(target);
                                    flyers.remove(target);
                                    FlyMethod(target, true);
                                    timer.put(target.getName(), System.currentTimeMillis());
                                    if (time.get(target.getName()) >= 3600) {
                                        player.sendMessage(Prefix + " " + target.getName() + " can fly for " + ChatColor.AQUA + (time.get(target.getName()) / 3600) + " hours");
                                        if (TargetMessage) {
                                            if (PlayerNameReveal) {
                                                target.sendMessage(Prefix + " " + player.getName() + " " + NameReveal + " " + ChatColor.AQUA + (time.get(target.getName()) / 3600) + " hours");
                                            } else {
                                                target.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + (time.get(target.getName()) / 3600) + " hours");
                                            }
                                        }
                                    } else if (time.get(target.getName()) >= 60) {
                                        player.sendMessage(Prefix + " " + target.getName() + " can fly for " + ChatColor.AQUA + (time.get(target.getName()) / 60) + " minutes");
                                        if (TargetMessage) {
                                            if (PlayerNameReveal) {
                                                target.sendMessage(Prefix + " " + player.getName() + " " + NameReveal + " " + ChatColor.AQUA + (time.get(target.getName()) / 60) + " minutes");
                                            } else {
                                                target.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + (time.get(target.getName()) / 60) + " minutes");
                                            }
                                        }
                                    } else {
                                        player.sendMessage(Prefix + " " + target.getName() + " can fly for " + ChatColor.AQUA + time.get(target.getName()) + " seconds");
                                        if (TargetMessage) {
                                            if (PlayerNameReveal) {
                                                target.sendMessage(Prefix + " " + player.getName() + " " + NameReveal + " " + ChatColor.AQUA + time.get(target.getName()) + " sceonds");
                                            } else {
                                                target.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + time.get(target.getName()) + " seconds");
                                            }
                                        }
                                    }
                                } else {
                                    player.sendMessage(Prefix + ChatColor.RED + " " +  WrongArgs);
                                }

                            } else {
                                player.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                            }
                        } else {
                            player.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
                        }
                    } else {
                        if (player instanceof Player) {
                            time.put(player.getName(), Integer.parseInt((sb.toString())));
                            if (time.get(player.getName()) > 0 && time.get(player.getName()) < 86400) {
                                flyers.remove(player);
                                flyers.remove(player);
                                FlyMethod(player, true);
                                timer.put(player.getName(), System.currentTimeMillis());
                                if (time.get(player.getName()) >= 3600) {
                                    player.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + (time.get(player.getName()) / 3600) + " hours");
                                } else if (time.get(player.getName()) >= 60) {
                                    player.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + (time.get(player.getName()) / 60) + " minutes");
                                } else {
                                    player.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + time.get(player.getName()) + " seconds");
                                }
                            } else {
                                player.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                            }

                        } else {
                            player.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                        }
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


