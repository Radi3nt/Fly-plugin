package fr.radi3nt.fly.events;

import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.ArrayList;
import java.util.Map;

public class OnGamemodeChange implements Listener {

    ArrayList<String> flyers = Fly.flyers;
    public Map<String, Long> timer = Tempfly.timer;
    public Map<String, Integer> time = Tempfly.time;

    @EventHandler
    public void OnGamemodeChange(PlayerGameModeChangeEvent e) {
        Player player = e.getPlayer();
        player.setInvulnerable(false);
        if (!player.hasPermission("fly.gamemode")) {
            if (!(player.getGameMode( ) == GameMode.CREATIVE)) {
                player.setAllowFlight(false);
                player.setFlying(false);
                flyers.remove(player.getName());
                timer.remove(player.getName());
                time.remove(player.getName());
            }
        } else {
            player.setAllowFlight(false);
            player.setFlying(false);
            flyers.remove(player.getName());
            timer.remove(player.getName());
            time.remove(player.getName());
        }
    }

}
