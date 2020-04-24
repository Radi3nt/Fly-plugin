package fr.radi3nt.fly.events;

import fr.radi3nt.fly.commands.Fly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;

public class PlayerFlying implements Listener {

    ArrayList<String> flyers = Fly.flyers;

    @EventHandler
    public void OnPlayerFlying(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if(!player.isFlying()) {
            if (!flyers.contains(player.getName())) {
                flyers.add(player.getName());
            }
            player.setInvulnerable(player.hasPermission("fly.invincible"));
        } else {
            player.setInvulnerable(false);
            if (!player.hasPermission("fly.fly") && !flyers.contains(player.getName())) {
                player.setFlying(false);
                player.setAllowFlight(false);
                flyers.remove(player.getName());
                player.setInvulnerable(false);
            }
        }
    }
}
