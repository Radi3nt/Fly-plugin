package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.SoundCategory;
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

    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
    String WrongArgs = plugin.getConfig().getString("wrong-args");
    String NoArgs = plugin.getConfig().getString("no-args");
    String InvalidPlayer = plugin.getConfig().getString("invalid-player");
    String NoPermission = plugin.getConfig().getString("no-permission");

    String NameReveal = plugin.getConfig().getString("temp-target-namereveal");
    String TargetMe = plugin.getConfig().getString("temp-target");

    Boolean TargetMessage = plugin.getConfig().getBoolean("temp-target-message");
    Boolean PlayerNameReveal = plugin.getConfig().getBoolean("temp-player-name-reveal");
    String TempMinute = plugin.getConfig().getString("temp-minutes");
    String TempSecond = plugin.getConfig().getString("temp-seconds");
    String TempHours = plugin.getConfig().getString("temp-hours");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length < 1) {
                sender.sendMessage(Prefix + " " + NoArgs);
            } else {
                StringBuilder sb = new StringBuilder();
                boolean found = false;
                for (char c : args[0].toCharArray()) {
                    if (Character.isDigit(c)) {
                        sb.append(c);
                        found = true;
                    } else if (found) {
                        // If we already found a digit before and this char is not a digit, stop looping
                        break;
                    }
                }

                if (sb.toString().isEmpty()) {
                    sender.sendMessage(ChatColor.RED + WrongArgs);
                    return false;
                }
                if (!(args.length == 1)) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target instanceof Player) {
                        time.put(target.getName(), Integer.parseInt((sb.toString())));
                        if (time.get(target.getName()) > 0 && time.get(target.getName()) < 86400) {
                            flyers.remove(target.getName());
                            flyers.remove(target.getName());
                            FlyMethod(target, true);
                            timer.put(target.getName(), System.currentTimeMillis());
                                int timeleft = time.get(target.getName());
                                sender.sendMessage(Prefix + " " + target.getName() + " can fly for " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (timeleft - (((timeleft / 3600) *3600) + (timeleft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                                if (TargetMessage) {
                                    if (PlayerNameReveal) {
                                        target.sendMessage(Prefix + " The console " + NameReveal + " " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (timeleft - (((timeleft / 3600) *3600) + (timeleft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                                    } else {
                                        target.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (timeleft - (((timeleft / 3600) *3600) + (timeleft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                                    }
                                }
                        } else {
                            sender.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                        }

                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                    }
                }
            }
        } else {
        Player player = (Player) sender;
        if (player.hasPermission("fly.tempfly")) {
            if (args.length < 1) {
                player.sendMessage(Prefix + " " + WrongArgs);
            } else {
                StringBuilder sb = new StringBuilder();
                boolean found = false;
                for (char c : args[0].toCharArray()) {
                    if (Character.isDigit(c)) {
                        sb.append(c);
                        found = true;
                    } else if (found) {
                        // If we already found a digit before and this char is not a digit, stop looping
                        break;
                    }
                }

                if (sb.toString().isEmpty()) {
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
                                player.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, 2);
                                FlyMethod(target, true);
                                timer.put(target.getName(), System.currentTimeMillis());
                                int timeleft = time.get(target.getName());
                                sender.sendMessage(Prefix + " " + target.getName() + " can fly for " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (timeleft - (((timeleft / 3600) *3600) + (timeleft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                                if (TargetMessage) {
                                        if (PlayerNameReveal) {
                                            target.sendMessage(Prefix + " " + player.getName() + " " + NameReveal + " " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (timeleft - (((timeleft / 3600) *3600) + (timeleft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                                        } else {
                                            target.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (timeleft - (((timeleft / 3600) *3600) + (timeleft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                                        }
                                    }
                            } else {
                                player.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                            }

                        } else {
                            player.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                        }
                    } else {
                        player.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
                    }
                } else {
                    time.put(player.getName(), Integer.parseInt((sb.toString())));
                    if (time.get(player.getName()) > 0 && time.get(player.getName()) < 86400) {
                        if (player.hasPermission("fly.tempfly." + time.get(player.getName()))) {
                            flyers.remove(player.getName());
                            FlyMethod(player, true);
                            timer.put(player.getName(), System.currentTimeMillis());
                            int timeleft = time.get(player.getName());
                            sender.sendMessage(Prefix + " " + TargetMe + " " + ChatColor.AQUA + (timeleft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((timeleft - (timeleft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (timeleft - (((timeleft / 3600) *3600) + (timeleft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                        } else {
                            player.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
                        }
                    } else {
                        player.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                    }

                }
            }
            }
        }
        return true;
    }


    public void FlyMethod(Player player, boolean state) {
        player.setAllowFlight(state);
        if (state == true) {
            flyers.add(player.getName());
        } else {
            flyers.remove(player.getName());
            player.setInvulnerable(false);
        }
    }

}


