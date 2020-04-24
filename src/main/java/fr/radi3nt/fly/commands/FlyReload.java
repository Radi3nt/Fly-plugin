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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;
        String NoPermission = plugin.getConfig().getString("no-permission");
        String ReloadMessage = plugin.getConfig().getString("reload-message");

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("fly.reload")) {

                plugin.reloadConfig();
                List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                for (int i = 0; i < list.size(); i++){
                    if (list.get(i).hasPermission("fly.admin")) {
                        list.get(i).sendMessage(Prefix + " " + ReloadMessage);
                        Player op = list.get(i);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            op.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 1);
                        }, 5L);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            op.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 1.5);
                        }, 8L);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            op.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 2);
                        }, 11L);
                    }

                }
                return true;
            } else {
                player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
            }
        } else {
            plugin.reloadConfig();
            sender.sendMessage(Prefix + " " + ReloadMessage);
        }
        return true;
    }
}
