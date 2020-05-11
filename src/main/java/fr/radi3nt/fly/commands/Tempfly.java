package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tempfly implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    public static Map<String, Long> timer = new HashMap<>();
    public static Map<String, Integer> time = new HashMap<>();
    public ArrayList<String> flyers = Fly.flyers;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String WrongArgs = plugin.getConfig().getString("wrong-args");
        String NoArgs = plugin.getConfig().getString("no-args");
        String InvalidPlayer = plugin.getConfig().getString("invalid-player");
        String NoPermission = plugin.getConfig().getString("no-permission");


        String MessagePT = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("tempfly-message"));
        String MessageTP = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("tempfly-target"));
        String MessageP = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("tempfly-player"));

        Boolean TargetMessage = plugin.getConfig().getBoolean("tempfly-target-message");


        File locations = new File("plugins/FlyPlugin", "flyers.yml");
        if (!locations.exists()) {
            try {
                locations.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            FileConfiguration loc = YamlConfiguration.loadConfiguration(locations);


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
                            loc.set("flyers." + target.getName() , time.get(target.getName()));
                            FlyMethod(target, true);
                            timer.put(target.getName(), System.currentTimeMillis());
                                int timeleft = time.get(target.getName());
                                int heures = (timeleft / 3600);
                                int minutes = ((timeleft - (timeleft / 3600) *3600) / 60);
                                int seconds = timeleft - (heures*3600 + minutes*60);
                                String TimeleftPT = MessagePT.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%target%", target.getName());
                                sender.sendMessage(Prefix + " " + TimeleftPT);
                                if (TargetMessage) {
                                    String TimeleftTP = MessageTP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%player%", "console");
                                        target.sendMessage(Prefix + " " + TimeleftTP);
                                }
                        } else {
                            sender.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                        }

                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                    }
                } else {
                    sender.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
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
                                loc.set("flyers." + target.getName() , time.get(target.getName()));                                FlyMethod(target, true);
                                timer.put(target.getName(), System.currentTimeMillis());
                                int timeleft = time.get(target.getName());
                                int heures = (timeleft / 3600);
                                int minutes = ((timeleft - (timeleft / 3600) *3600) / 60);
                                int seconds = timeleft - (heures*3600 + minutes*60);
                                String TimeleftPT = MessagePT.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%target%", target.getName());
                                sender.sendMessage(Prefix + " " + TimeleftPT);
                                if (TargetMessage) {
                                    String TimeleftTP = MessageTP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%player%", player.getName());
                                    target.sendMessage(Prefix + " " + TimeleftTP);
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
                            loc.set("flyers." + player.getName() , time.get(player.getName()));
                            FlyMethod(player, true);
                            timer.put(player.getName(), System.currentTimeMillis());
                            int timeleft = time.get(player.getName());
                            int heures = (timeleft / 3600);
                            int minutes = ((timeleft - (timeleft / 3600) *3600) / 60);
                            int seconds = timeleft - (heures*3600 + minutes*60);
                            String TimeleftP = MessageP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                            sender.sendMessage(Prefix + " " + TimeleftP);
                        } else {
                            player.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
                        }
                    } else {
                        player.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                    }

                }
            }
            } else {
            player.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
        }
        }
        try {
            loc.save(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void FlyMethod(Player player, boolean state) {
        player.setAllowFlight(state);
        if (state) {
            flyers.add(player.getName());
        } else {
            flyers.remove(player.getName());
            player.setInvulnerable(false);
        }
    }

}


