package fr.radi3nt.fly.events;

import fr.radi3nt.fly.commands.Fly;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.ArrayList;

public class OnGamemodeChange implements Listener {

    ArrayList<String> flyers = Fly.flyers;

    @EventHandler
    public void OnGamemodeChange(PlayerGameModeChangeEvent e) {
        Player player = e.getPlayer();
        player.setInvulnerable(false);
        if (!player.hasPermission("fly.gamemode")) {
            if (!(player.getGameMode( ) == GameMode.CREATIVE)) {
                player.setAllowFlight(false);
                player.setFlying(false);
                flyers.remove(player);
            }
        } else {
            player.setAllowFlight(false);
            player.setFlying(false);
            flyers.remove(player);
        }
    }

}
