package fr.radi3nt.fly.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static fr.radi3nt.fly.timer.Cosmetics.MaxHeight;

public class OnPlayerMove implements Listener {

    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (MaxHeight.containsKey(player)) {
            int perm = 0;
            if (player.getLocation().getBlockY() >= MaxHeight.get(player) - 5 && player.getLocation().getBlockY() <= MaxHeight.get(player) - 3) {
                if (player.hasPermission("fly.height")) {
                    for (int height = 0; height < 256; height++) {

                        if (player.hasPermission("fly.height." + height)) {
                            perm = height;
                        }
                    }
                }
                if (player.isOp()) {
                    MaxHeight.put(player, 0);
                } else {
                    MaxHeight.put(player, perm);
                }
            }
        }

    }

}
