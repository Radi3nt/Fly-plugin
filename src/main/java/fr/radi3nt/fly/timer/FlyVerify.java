package fr.radi3nt.fly.timer;

import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fr.radi3nt.fly.commands.FlyAlert.NotifyDust;

public class FlyVerify extends BukkitRunnable {


    @Override
    public void run() {

        ArrayList<String> flyers = Fly.flyers;
        Map<String, Long> timer = Tempfly.timer;


        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < list.size(); i++) {
            Player player = list.get(i);
            if (player.hasPermission("fly.admin")) {
                if (NotifyDust.get(player)) {
                    for (int p = 0; p < list.size(); p++) {
                        Player target = list.get(i);
                        if (flyers.contains(target.getName())) {
                            if (target.isFlying()) {
                                if (timer.containsKey(target.getName())) {
                                    Location tloc = target.getLocation();
                                    Double x = tloc.getX();
                                    Double y = tloc.getY() + 2.3;
                                    Double z = tloc.getZ();
                                    tloc.add(0, 2, 0);
                                    player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0.001, 1, 0, 1, new Particle.DustOptions(Color.YELLOW, 1));
                                } else {
                                    Location tloc = target.getLocation();
                                    Double x = tloc.getX();
                                    Double y = tloc.getY() + 2.3;
                                    Double z = tloc.getZ();
                                    tloc.add(0, 2, 0);
                                    player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0.001, 1, 0, 1, new Particle.DustOptions(Color.GREEN, 1));
                                }
                            } else {
                                Location tloc = target.getLocation();
                                Double x = tloc.getX();
                                Double y = tloc.getY() + 2;
                                Double z = tloc.getZ();
                                tloc.add(0, 2, 0);
                                player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0.001, 1, 0, 1, new Particle.DustOptions(Color.RED, 1));
                            }
                        }
                    }
                }
            }
        }


    }
}
