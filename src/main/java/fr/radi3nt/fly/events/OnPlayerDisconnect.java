package fr.radi3nt.fly.events;

import fr.radi3nt.fly.commands.Fly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class OnPlayerDisconnect implements Listener {

    ArrayList<String> flyers = Fly.flyers;

    @EventHandler
    public void OnPlayerDisconnect(PlayerQuitEvent e) {

        Player player = e.getPlayer();
        player.setInvulnerable(false);
        flyers.remove(player);

    }

}
