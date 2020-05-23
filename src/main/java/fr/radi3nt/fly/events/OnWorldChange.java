package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.Map;

import static fr.radi3nt.fly.commands.FlyAlert.*;
import static fr.radi3nt.fly.events.OnGroundHit.GroundHitters;

public class OnWorldChange implements Listener {

    ArrayList<String> flyers = Fly.flyers;
    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;

    @EventHandler
    public void OnWorldChange(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (player.getAllowFlight()) {
            if (!player.hasPermission("fly.changeworld")) {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setInvulnerable(false);
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
            }
        }
    }
}
