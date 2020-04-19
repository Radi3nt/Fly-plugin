package fr.radi3nt.fly.events;

import fr.radi3nt.fly.commands.Fly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class OnPlayerJoin implements Listener {

    ArrayList<String> flyers = Fly.flyers;

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (player.getAllowFlight()) {
            if (!player.hasPermission("fly.join")) {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setInvulnerable(false);
                flyers.remove(player);
            }
        }
        flyers.remove(player);
        player.setInvulnerable(false);
    }

}
