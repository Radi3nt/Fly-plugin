package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static java.lang.Math.*;

public class OnGroundHit implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);


    public static ArrayList<Player> GroundHitters = new ArrayList<>();


    @EventHandler
    public void OnFallDamage(EntityDamageEvent e) {

        Boolean ActiveFallwave = plugin.getConfig().getBoolean("active-fallwave");
        Boolean ActiveFallwaveAlways = plugin.getConfig().getBoolean("active-fallwave-always");

        if (e.getEntity() instanceof Player){
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                Player player = (Player) e.getEntity();
                if (ActiveFallwave) {
                    if (ActiveFallwaveAlways) {
                        float fDist = player.getFallDistance();
                        HitMethod(player, fDist);
                        GroundHitters.remove(player);
                    } else {
                        if (GroundHitters.contains(player)) {
                            float fDist = player.getFallDistance();
                            HitMethod(player, fDist);
                            GroundHitters.remove(player);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }


    public static void HitMethod(Player player, double hight){

        double radius = hight/2.25 + random()*1.25;


        Location playerLocation = player.getLocation();
        new BukkitRunnable() {
            double t = 0;
            public void run() {

                t = t + 0.1*Math.PI;
                for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32) {
                    double x = t*cos(theta);
                    double y = 2*Math.exp(-0.0001*t) * sin(t) - 0.5;
                    double z = t*sin(theta);
                    playerLocation.add(x, y, z);
                    Location blocklocation = player.getLocation();
                    double blocky = blocklocation.getBlockY() - 1;
                    blocklocation.setY(blocky);
                    Block block = player.getWorld().getBlockAt(blocklocation);
                    if (block.isEmpty() || block.getType().isAir()) {
                        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, playerLocation, 1, 0, 0, 0, 0, Material.DIRT.createBlockData() , true);
                    } else {
                        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, playerLocation, 1, 0, 0, 0, 0, block.getType().createBlockData(), true);
                    }

                    for (Entity e : player.getNearbyEntities(20, 20, 20)) {
                         if (e.getLocation().distance(playerLocation) < 1.25) {
                             Vector vector = new Vector(0, (radius*2 - t)/hight, 0);
                             Vector fromPlayerToTarget = e.getLocation().toVector().subtract(player.getLocation().toVector());
                             fromPlayerToTarget.multiply(hight/15);
                             fromPlayerToTarget.setY(vector.getY());
                             e.setVelocity(fromPlayerToTarget);
                         }
                    }

                    playerLocation.subtract(x, y, z);


                }
                if (t > radius) {
                    this.cancel();
                }

            }

        }.runTaskTimer(MainFly.getPlugin(MainFly.class), 1L, 1L);

        GroundHitters.remove(player);
        GroundHitters.remove(player);
        GroundHitters.remove(player);
        GroundHitters.remove(player);
    }
}
