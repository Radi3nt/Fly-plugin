package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.FlyAlert;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static fr.radi3nt.fly.commands.Fly.FlyMethod;

public class OnPlayerJoin implements Listener {

    ArrayList<String> flyers = Fly.flyers;
    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;


    HashMap<Player, Boolean> NotifyChat = FlyAlert.NotifyChat;
    HashMap<Player, Boolean> NotifyTitle = FlyAlert.NotifyTitle;
    HashMap<Player, Boolean> NotifyBossBar = FlyAlert.NotifyBossBar;
    HashMap<Player, Boolean> NotifySounds = FlyAlert.NotifySounds;

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
    String version = plugin.getConfig().getString("version");
    Boolean Message = plugin.getConfig().getBoolean("credits-message");


    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        NotifyChat.remove(player);
        NotifyTitle.remove(player);
        NotifyBossBar.remove(player);
        NotifySounds.remove(player);
        NotifyChat.put(player, true);
        NotifyTitle.put(player, true);
        NotifyBossBar.put(player, true);
        NotifySounds.put(player, true);
        if (player.getAllowFlight()) {
            if (!player.hasPermission("fly.join")) {
                if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    player.setInvulnerable(false);
                    flyers.remove(player.getName());
                    timer.remove(player.getName());
                    time.remove(player.getName());
                }
            }
        }
        flyers.remove(player.getName());
        player.setInvulnerable(false);
        if (player.hasPermission("fly.admin")) {
            if (Message) {
                player.sendMessage(ChatColor.BLUE + " " + ChatColor.STRIKETHROUGH + "------------------------");
                player.sendMessage(ChatColor.AQUA + " |   Fly plugin by " + ChatColor.GREEN + ChatColor.BOLD + "Radi3nt" + ChatColor.AQUA + "    |");
                player.sendMessage(ChatColor.AQUA + " |        Version: " + ChatColor.GREEN + ChatColor.BOLD + "1.2.3c" + ChatColor.AQUA + "      |");
                player.sendMessage(ChatColor.BLUE + " " + ChatColor.STRIKETHROUGH + "------------------------");
            }
        }

        if (player.hasPermission("fly.join")) {
            File locations = new File("plugins/FlyPlugin", "flyers.yml");
            if (!locations.exists()) {
                try {
                    locations.createNewFile();
                } catch (IOException event) {
                    event.printStackTrace();
                }
            }
            FileConfiguration loc = YamlConfiguration.loadConfiguration(locations);
            if (loc.get("flyers." + player.getName()) != null) {
                Integer timeleft = (Integer) loc.get("flyers." + player.getName());
                FlyMethod(player, true);
                timer.put(player.getName(), System.currentTimeMillis());
                time.put(player.getName(), timeleft);
                Location ploc = player.getLocation();
                int y = ploc.getBlockY() - 1;
                ploc.add(ploc.getBlockX(), y, ploc.getBlockZ());
                if (ploc.getBlock().getType().isAir()) {
                    player.setFlying(true);
                }
            }
        }
    }
}
