package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Map;

public class OnPlayerJoin implements Listener {

    ArrayList<String> flyers = Fly.flyers;
    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
    String version = plugin.getConfig().getString("version");
    Boolean Message = plugin.getConfig().getBoolean("credits-message");


    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (player.getAllowFlight()) {
            if (!player.hasPermission("fly.join")) {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setInvulnerable(false);
                flyers.remove(player.getName());
                timer.remove(player.getName());
                time.remove(player.getName());
            }
        }
        flyers.remove(player.getName());
        player.setInvulnerable(false);
            if (player.hasPermission("fly.admin")) {
                if (Message) {
                    player.sendMessage(  ChatColor.BLUE + " " + ChatColor.STRIKETHROUGH + "------------------------");
                    player.sendMessage(  ChatColor.AQUA + " |   Fly plugin by " + ChatColor.GREEN + ChatColor.BOLD + "Radi3nt" + ChatColor.AQUA + "    |");
                    player.sendMessage(  ChatColor.AQUA + " |         Version: " + ChatColor.GREEN + ChatColor.BOLD + "1.2.0" + ChatColor.AQUA + "        |");
                    player.sendMessage(  ChatColor.BLUE + " " + ChatColor.STRIKETHROUGH + "------------------------");
                }
            }
        }

    }
