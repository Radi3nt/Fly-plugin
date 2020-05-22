package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Map;

import static fr.radi3nt.fly.events.OnGroundHit.GroundHitters;
import static fr.radi3nt.fly.timer.TempCheck.timem;

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
                time.remove(player.getName());
                timer.remove(player.getName());
                timem.remove(player);
                flyers.remove(player.getName());
                GroundHitters.add(player);
            }
        }
    }
}
