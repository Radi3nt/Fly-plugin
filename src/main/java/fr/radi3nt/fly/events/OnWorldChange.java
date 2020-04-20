package fr.radi3nt.fly.events;

import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.Map;

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
                flyers.remove(player.getName());
                timer.remove(player.getName());
                time.remove(player.getName());
            }
        }
    }
}
