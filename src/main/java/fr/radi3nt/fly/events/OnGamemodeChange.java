package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Map;

import static fr.radi3nt.fly.commands.FlyAlert.*;
import static fr.radi3nt.fly.events.OnGroundHit.GroundHitters;

public class OnGamemodeChange implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    ArrayList<String> flyers = Fly.flyers;
    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;

    @EventHandler
    public void OnGamemodeChange(PlayerGameModeChangeEvent e) {
        GameMode gamemode = e.getNewGameMode();
        Player player = e.getPlayer();
        player.setInvulnerable(false);
        if (gamemode.equals(GameMode.CREATIVE)) {
                player.setAllowFlight(true);
                player.setFlying(true);
        } else if (player.getGameMode().equals(GameMode.CREATIVE)) {
            if (!player.hasPermission("fly.gamemode")) {
                player.setAllowFlight(false);
                player.setFlying(false);
                if (timer.containsKey(player.getName())) {
                    time.put(player.getName(), 1);
                    timer.put(player.getName(), System.currentTimeMillis());
                    Boolean Chat = NotifyChat.get(player);
                    Boolean BossBar = NotifyBossBar.get(player);
                    Boolean Title = NotifyTitle.get(player);
                    Boolean Sounds = NotifySounds.get(player);

                    GroundHitters.add(player);


                    NotifyChat.put(player, false);
                    NotifyBossBar.put(player, false);
                    NotifyTitle.put(player, false);
                    NotifySounds.put(player, false);

                    Bukkit.getScheduler().runTaskLater(MainFly.getPlugin(MainFly.class), () -> {
                        NotifyChat.put(player, Chat);
                        NotifyBossBar.put(player, BossBar);
                        NotifyTitle.put(player, Title);
                        NotifySounds.put(player, Sounds);
                    }, 50L);
                }
                flyers.remove(player.getName());
                GroundHitters.add(player);
            }
        }
    }
}
