package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static fr.radi3nt.fly.commands.FlyAlert.*;
import static fr.radi3nt.fly.commands.Tempfly.time;
import static fr.radi3nt.fly.commands.Tempfly.timer;

public class UnTempFly implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String InvalidPlayer = plugin.getConfig().getString("invalid-player");
        String NoPermission = plugin.getConfig().getString("no-permission");
        String NoFlyYou = plugin.getConfig().getString("timefly-nofly-you");
        String NoFlyHe = plugin.getConfig().getString("timefly-nofly-target");


        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("fly.unfly")) {
                    if (timer.containsKey(player.getName())) {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.setInvulnerable(false);
                        time.put(player.getName(), 1);
                        timer.put(player.getName(), System.currentTimeMillis());

                        Boolean Chat = NotifyChat.get(player);
                        Boolean BossBar = NotifyBossBar.get(player);
                        Boolean Title = NotifyTitle.get(player);
                        Boolean Sounds = NotifySounds.get(player);

                        NotifyChat.put(player, false);
                        NotifyBossBar.put(player, false);
                        NotifyTitle.put(player, false);
                        NotifySounds.put(player, false);

                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            NotifyChat.put(player, Chat);
                            NotifyBossBar.put(player, BossBar);
                            NotifyTitle.put(player, Title);
                            NotifySounds.put(player, Sounds);
                        }, 50L);


                        String Off = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("off-state")) + ChatColor.RESET;
                        String FlyYoureself = plugin.getConfig().getString("fly-yourself");
                        String FYr = FlyYoureself.replace("%state%", Off);
                        player.sendMessage(Prefix + " " + FYr);
                    } else {
                        player.sendMessage(Prefix + " " + ChatColor.RED + NoFlyYou);
                    }
                } else {
                    player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                }
            } else {
                if (player.hasPermission("fly.unflyothers")) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target instanceof Player) {
                        if (timer.containsKey(target.getName())) {
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            target.setInvulnerable(false);
                            time.put(target.getName(), 1);
                            timer.put(target.getName(), System.currentTimeMillis());

                            Boolean Chat = NotifyChat.get(target);
                            Boolean BossBar = NotifyBossBar.get(target);
                            Boolean Title = NotifyTitle.get(target);
                            Boolean Sounds = NotifySounds.get(target);

                            NotifyChat.put(target, false);
                            NotifyBossBar.put(target, false);
                            NotifyTitle.put(target, false);
                            NotifySounds.put(target, false);

                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                NotifyChat.put(target, Chat);
                                NotifyBossBar.put(target, BossBar);
                                NotifyTitle.put(target, Title);
                                NotifySounds.put(target, Sounds);
                            }, 50L);

                            String Off = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("off-state")) + ChatColor.RESET;
                            Boolean TargetSendMessage = plugin.getConfig().getBoolean("fly-target-message");
                            String FlySomeoneTarget = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-target")) + ChatColor.RESET;
                            String FlySomeonePlayer = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-player")) + ChatColor.RESET;
                            String FSPr = FlySomeonePlayer.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", player.getName());
                            player.sendMessage(Prefix + " " + FSPr);
                            if (TargetSendMessage) {
                                String FSTr = FlySomeoneTarget.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", player.getName());
                                target.sendMessage(Prefix + " " + FSTr);
                            }
                        } else {
                            sender.sendMessage(Prefix + " " + ChatColor.RED + NoFlyHe);
                        }
                    } else {
                        player.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                    }
                } else {
                    player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                }
            }
        } else {
            if (args.length == 0) {
                String NoArgs = plugin.getConfig().getString("no-args");
                sender.sendMessage(Prefix + " " + ChatColor.RED + NoArgs);
            } else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target instanceof Player) {
                    if (timer.containsKey(target.getName())) {
                        target.setAllowFlight(false);
                        target.setFlying(false);
                        target.setInvulnerable(false);
                        time.put(target.getName(), 1);
                        timer.put(target.getName(), System.currentTimeMillis());
                        Boolean Chat = NotifyChat.get(target);
                        Boolean BossBar = NotifyBossBar.get(target);
                        Boolean Title = NotifyTitle.get(target);
                        Boolean Sounds = NotifySounds.get(target);

                        NotifyChat.put(target, false);
                        NotifyBossBar.put(target, false);
                        NotifyTitle.put(target, false);
                        NotifySounds.put(target, false);

                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            NotifyChat.put(target, Chat);
                            NotifyBossBar.put(target, BossBar);
                            NotifyTitle.put(target, Title);
                            NotifySounds.put(target, Sounds);
                            }, 50L);





                        String Off = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("off-state")) + ChatColor.RESET;
                        Boolean TargetSendMessage = plugin.getConfig().getBoolean("fly-target-message");
                        String FlySomeoneTarget = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-target")) + ChatColor.RESET;
                        String FlySomeonePlayer = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-player")) + ChatColor.RESET;
                        String FSPr = FlySomeonePlayer.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", "console");
                        sender.sendMessage(Prefix + " " + FSPr);
                        if (TargetSendMessage) {
                            String FSTr = FlySomeoneTarget.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", "console");
                            target.sendMessage(Prefix + " " + FSTr);
                        }
                    } else {
                        sender.sendMessage(Prefix + " " + ChatColor.RED + NoFlyHe);
                    }
                } else {
                    sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                }
            }
        }
        return true;
    }
}
