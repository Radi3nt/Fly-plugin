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
import java.util.List;

public class FlyReload implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);
    public static Long timer = 0L;



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String NoPermission = plugin.getConfig().getString("no-permission");
        String ReloadMessage = plugin.getConfig().getString("reload-message");
        String CooldownMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("cooldown-message")) + ChatColor.RESET;
        Boolean ReloadMelody = plugin.getConfig().getBoolean("reload-melody");
        Integer CooldownTime = plugin.getConfig().getInt("cooldown-time");


        if (sender instanceof Player) {

            Player player_c = (Player) sender;
            if (player_c.hasPermission("fly.reload")) {
                plugin.reloadConfig();
                plugin.getConfig().options().copyDefaults();
                plugin.saveConfig();
                int secondes = CooldownTime;
                long timeleft = ((timer / 1000) + secondes) - (System.currentTimeMillis() / 1000);
                String cooldown = CooldownMessage.replace("%timeleft%", String.valueOf(timeleft));
                if (timeleft <= 0) {
                    List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).hasPermission("fly.admin")) {
                            list.get(i).sendMessage(Prefix + " " + ChatColor.RED + ChatColor.BOLD + ReloadMessage);
                            timer = System.currentTimeMillis();
                        /*/Player op = list.get(i);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            op.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 1);
                        }, 5L);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            op.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 1.5);
                        }, 8L);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            op.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 2);
                        }, 11L);/*/
                            if (ReloadMelody) {
                                Player player = list.get(i);
                                player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 0.8);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 0.8);
                                }, 4L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 0.67);
                                }, 8L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 0.67);
                                }, 12L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 0.8);
                                }, 16L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 0.9);
                                }, 20L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1);
                                }, 24L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.2);
                                }, 28L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.2);
                                }, 32L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.2);
                                }, 36L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1);
                                }, 40L);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    player.playSound(player.getLocation(), "minecraft:block.note_block.harp", SoundCategory.AMBIENT, 100, (float) 1.6);
                                }, 44L);
                            }
                        }
                    }
                } else {
                    player_c.sendMessage(Prefix + " " + cooldown);
                }
                return true;
            } else {
                player_c.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
            }
        } else {
            plugin.reloadConfig();
            sender.sendMessage(Prefix + " " + ChatColor.RED + ReloadMessage);
            plugin.getConfig().options().copyDefaults();
            plugin.saveDefaultConfig();
        }
        return true;
    }
}
