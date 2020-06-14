package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.FlyAlert;
import fr.radi3nt.fly.commands.Tempfly;
import fr.radi3nt.fly.utilis.UpdateCheck;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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
import static fr.radi3nt.fly.commands.FlyAlert.NotifyDust;
import static fr.radi3nt.fly.events.OnGroundHit.GroundHitters;
import static fr.radi3nt.fly.timer.Cosmetics.ZoneFlyers;
import static fr.radi3nt.fly.timer.TempCheck.timem;

public class OnPlayerJoin implements Listener {

    ArrayList<String> flyers = Fly.flyers;
    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;


    HashMap<Player, Boolean> NotifyChat = FlyAlert.NotifyChat;
    HashMap<Player, Boolean> NotifyTitle = FlyAlert.NotifyTitle;
    HashMap<Player, Boolean> NotifyBossBar = FlyAlert.NotifyBossBar;
    HashMap<Player, Boolean> NotifySounds = FlyAlert.NotifySounds;

    Plugin plugin = MainFly.getPlugin(MainFly.class);


    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String version = plugin.getConfig().getString("version");
        Boolean Message = plugin.getConfig().getBoolean("credits-message");
        Boolean UpdateChecking = plugin.getConfig().getBoolean("update-check");


        Player player = e.getPlayer();

        if (player.hasPermission("fly.air")) {
            if (!player.isOnGround()) {
                player.setAllowFlight(true);
                player.setFlying(true);
            }
        } else {
            ZoneFlyers.put(player, false);
        }

        NotifyChat.remove(player);
        NotifyTitle.remove(player);
        NotifyBossBar.remove(player);
        NotifySounds.remove(player);
        NotifyDust.remove(player);
        NotifyChat.put(player, true);
        NotifyTitle.put(player, true);
        NotifyBossBar.put(player, true);
        NotifySounds.put(player, true);
        NotifyDust.put(player, true);
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
                player.sendMessage(ChatColor.AQUA + " |        Version: " + ChatColor.GREEN + ChatColor.BOLD + MainFly.VERSION + ChatColor.AQUA + "       |");
                player.sendMessage(ChatColor.BLUE + " " + ChatColor.STRIKETHROUGH + "------------------------");
            }
            if (UpdateChecking) {
                if (!UpdateCheck.upToDate) {
                    TextComponent tc = new TextComponent();
                    tc.setText(ChatColor.BLUE + " → spigot link");
                    tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/fly-plugin-tempfly-gui.77618"));
                    tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("➤ Click here to get the link").create()));


                    if (UpdateCheck.PreRelease.equals("true")) {
                        if (UpdateCheck.MajorVersion) {
                            player.sendMessage(Prefix + " Update found: " + UpdateCheck.latest + ", currently on version " + MainFly.VERSION + ".\n This is a MAJOR pre-release version! The update can be found here :");
                        } else {
                            player.sendMessage(Prefix + " Update found: " + UpdateCheck.latest + ", currently on version " + MainFly.VERSION + ".\n This is a minor pre-release version! The update can be found here :");
                        }
                    } else {
                        if (UpdateCheck.MajorVersion) {
                            player.sendMessage(Prefix + " Update found: " + UpdateCheck.latest + ", currently on version " + MainFly.VERSION + ".\n This is a MAJOR version! The update can be found here :");
                        } else {
                            player.sendMessage(Prefix + " Update found: " + UpdateCheck.latest + ", currently on version " + MainFly.VERSION + ".\n This is a minor version! The update can be found here :");
                        }
                    }

                    player.spigot().sendMessage(tc);
                }
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
                timem.put(player, 100000);
                Location ploc = player.getLocation();
                int y = ploc.getBlockY() - 2;
                ploc.add(0, y, 0);
            }
        }

        Location ploc = player.getLocation().getBlock().getLocation();
        ploc.add(0, -2, 0);
        if (ploc.getBlock().getType().equals(Material.AIR)) {
            GroundHitters.add(player);
        }

    }
}
