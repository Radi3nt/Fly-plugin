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
    public static HashMap<Player, Boolean> NotifyDust = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Plugin plugin = MainFly.getPlugin(MainFly.class);

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String NoArgs = plugin.getConfig().getString("no-args");
        String WrongArgs = plugin.getConfig().getString("wrong-args");
        String Estate = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("e-state")) + ChatColor.RESET;
        String Dstate = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("d-state")) + ChatColor.RESET;
        String Chat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyalert-chat")) + ChatColor.RESET;
        String Title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyalert-title")) + ChatColor.RESET;
        String Sounds = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyalert-sounds")) + ChatColor.RESET;
        String Bossbar = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyalert-bossbar")) + ChatColor.RESET;
        String Dust = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyalert-dust")) + ChatColor.RESET;
        String All = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyalert-all")) + ChatColor.RESET;



        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 1) {
                switch (args[0]) {
                    case "chat":
                        if (args[1].equals("on")) {
                            NotifyChat.remove(player);
                            NotifyChat.put(player, true);
                            String ChatR = Chat.replace("%state%", Estate);
                            sender.sendMessage(Prefix + " " + ChatR);
                        } else if (args[1].equals("off")) {
                            NotifyChat.remove(player);
                            NotifyChat.put(player, false);
                            String ChatR = Chat.replace("%state%", Dstate);
                            sender.sendMessage(Prefix + " " + ChatR);
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;

                    case "title":
                        if (args[1].equals("on")) {
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, true);
                            String TitleR = Title.replace("%state%", Estate);
                            sender.sendMessage(Prefix + " " + TitleR);
                        } else if (args[1].equals("off")) {
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, false);
                            String TitleR = Title.replace("%state%", Dstate);
                            sender.sendMessage(Prefix + " " + TitleR);
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;

                    case "sounds":
                        if (args[1].equals("on")) {
                            NotifySounds.remove(player);
                            NotifySounds.put(player, true);
                            String SoundsR = Sounds.replace("%state%", Estate);
                            sender.sendMessage(Prefix + " " + SoundsR);
                        } else if (args[1].equals("off")) {
                            NotifySounds.remove(player);
                            NotifySounds.put(player, false);
                            String SoundsR = Sounds.replace("%state%", Dstate);
                            sender.sendMessage(Prefix + " " + SoundsR);
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;

                    case "bossbar":
                        if (args[1].equals("on")) {
                            NotifyBossBar.remove(player);
                            NotifyBossBar.put(player, true);
                            String BossbarR = Bossbar.replace("%state%", Estate);
                            sender.sendMessage(Prefix + " " + BossbarR);
                        } else if (args[1].equals("off")) {
                            NotifyBossBar.remove(player);
                            NotifyBossBar.put(player, false);
                            String BossbarR = Bossbar.replace("%state%", Dstate);
                            sender.sendMessage(Prefix + " " + BossbarR);
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;


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
                            String AllR = All.replace("%state%", Estate);
                            sender.sendMessage(Prefix + " " + AllR);
                        } else if (args[1].equals("off")) {
                            NotifyBossBar.remove(player);
                            NotifyBossBar.put(player, false);
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, false);
                            NotifyChat.remove(player);
                            NotifyChat.put(player, false);
                            NotifySounds.remove(player);
                            NotifySounds.put(player, false);
                            String AllR = All.replace("%state%", Dstate);
                            sender.sendMessage(Prefix + " " + AllR);
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                        }
                        break;

                    case "dust":
                        if (player.hasPermission("fly.admin")) {
                            if (args[1].equals("on")) {
                                NotifyDust.remove(player);
                                NotifyDust.put(player, true);
                                String DustR = Dust.replace("%state%", Estate);
                                sender.sendMessage(Prefix + " " + DustR);
                            } else if (args[1].equals("off")) {
                                NotifyDust.remove(player);
                                NotifyDust.put(player, false);
                                String DustR = Dust.replace("%state%", Dstate);
                                sender.sendMessage(Prefix + " " + DustR);
                            } else {
                                player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                            }
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
