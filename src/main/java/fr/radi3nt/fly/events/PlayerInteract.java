package fr.radi3nt.fly.events;

import fr.radi3nt.fly.commands.Fly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class PlayerInteract implements Listener {


    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent e) {

        ArrayList<String> flyers = Fly.flyers;

        Player player = e.getPlayer();
        if (player.isFlying()) {
            if (!player.hasPermission("fly.fly")) {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setInvulnerable(false);
                flyers.remove(player.getName());
            }
        }
    }
}
