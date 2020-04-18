package fr.radi3nt.fly.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerFlying implements Listener {

    @EventHandler
    public void OnPlayerFlying(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if(!player.isFlying()) {
            if (player.hasPermission("fly.invincible")) {
                player.setInvulnerable(true);
            } else {
                player.setInvulnerable(false);
            }
        } else {
            player.setInvulnerable(false);
            if (!player.hasPermission("fly.fly")) {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setInvulnerable(false);
            }
        }
    }
}
