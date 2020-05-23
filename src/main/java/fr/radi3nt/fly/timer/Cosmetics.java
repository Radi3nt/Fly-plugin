package fr.radi3nt.fly.timer;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fr.radi3nt.fly.commands.FlyAlert.NotifyDust;
import static fr.radi3nt.fly.events.OnGroundHit.GroundHitters;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Cosmetics extends BukkitRunnable {

    Plugin plugin = MainFly.getPlugin(MainFly.class);
    int number = 0;

    public static ArrayList<Player> pushed = new ArrayList<>();

    @Override
    public void run() {

        // SHIELD

        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        Boolean Particles = plugin.getConfig().getBoolean("particles");
        if (Particles) {
            for (int i = 0; i < list.size(); i++) {
                Player player = list.get(i);
                if (player.isFlying()) {
                    if (player.hasPermission("fly.shield")) {

                        for (Entity entity : player.getNearbyEntities(1.25, 1.25, 1.25)) {
                            if (entity instanceof Player) {
                                Player target = (Player) entity;
                                if (target.isFlying() && target.hasPermission("fly.shield")) {
                                    Location exploadloc = new Location(player.getWorld(), (target.getLocation().getX() + player.getLocation().getX())/2, (target.getLocation().getY() + player.getLocation().getY())/2, (target.getLocation().getZ() + player.getLocation().getZ())/2);
                                    player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, exploadloc, 4, 0, 0, 0, 0);
                                    Vector fromPlayerToTarget = entity.getLocation().toVector().subtract(player.getLocation().toVector());
                                    entity.setVelocity(fromPlayerToTarget.multiply(1));
                                } else {
                                    Vector fromPlayerToTarget = entity.getLocation().toVector().subtract(player.getLocation().toVector());
                                    entity.setVelocity(fromPlayerToTarget.multiply(0.25));
                                }
                            }
                        }

                        if (number >= 4) {
                            for (int p = 0; p < list.size(); p++) {
                                Player target = list.get(p);
                                if (player != target) {
                                    for (double phi = 0; phi < 2 * Math.PI; phi += Math.PI / 10) {
                                        for (double tehta = 0; tehta <= 2 * Math.PI; tehta += Math.PI / 10) {
                                            Location location = player.getLocation();
                                            double r = 1.5;
                                            double x = r * cos(tehta) * sin(phi);
                                            double y = r * cos(phi) + 1;
                                            double z = r * sin(tehta) * sin(phi);
                                            location.add(x, y, z);

                                            target.spawnParticle(Particle.CRIT, location, 1, 0, 0, 0, 0);
                                            location.subtract(x, y, z);
                                        }
                                    }
                                }
                            }
                            number = 0;
                        } else {
                            number++;
                        }

                    }
                }
            }
        }

        // DUST + FLYERS AUTO REMOVE

        ArrayList<String> flyers = Fly.flyers;
        Map<String, Long> timer = Tempfly.timer;


        for (int i = 0; i < list.size(); i++) {
            Player player = list.get(i);


            //HEIGHT LIMIT
            if (player.isFlying()) {
                int perm = 0;
                for (int height = 0; height < 256 ; height++) {

                    if (player.hasPermission("fly.height." + height)) {
                        perm = height;
                    }

                }
                if (!(perm == 0)) {
                    int reelradius = 20;
                    Location floorLocation = new Location(player.getLocation().getWorld(), player.getLocation().getBlockX(), perm + 2, player.getLocation().getBlockZ());
                    if (player.getLocation().getBlockY() >= perm) {
                        if (!pushed.contains(player)) {
                            pushed.add(player);
                            new BukkitRunnable() {

                                double t = 0;
                                Location playerLocation = player.getLocation();

                                public void run() {

                                    t = t + 0.1 * Math.PI;
                                    for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 16) {
                                        double x = t * cos(theta);
                                        double y = 2 * Math.exp(-0.1 * t) * sin(t) + 0.2;
                                        double z = t * sin(theta);
                                        playerLocation.add(x, y, z);
                                        if (t>0.9) {
                                            player.spawnParticle(Particle.END_ROD, playerLocation, 1, 0, 0, 0, 0);
                                        }
                                        playerLocation.subtract(x, y, z);


                                    }
                                    if (t > 20) {
                                        pushed.remove(player);
                                        this.cancel();
                                    }
                                    if (t > 15) {
                                        pushed.remove(player);
                                    }

                                }

                            }.runTaskTimer(MainFly.getPlugin(MainFly.class), 1L, 1L);
                        }
                            Vector vector = new Vector(player.getLocation().toVector().getX() / 2, player.getLocation().toVector().getY(), player.getLocation().toVector().getZ() / 2);
                            player.setVelocity(vector.multiply(-0.02));

                    }
                    if (Particles) {
                        if (perm - reelradius < player.getLocation().getBlockY()) {
                            //Display
                            int radius = reelradius - (perm - player.getLocation().getBlockY());

                            int ox = 0;
                            int oy = 0; // origin

                            for (int x = -radius; x < radius; x++)
                            {
                                int height = (int) Math.sqrt(radius * radius - x * x);

                                for (int y = -height; y < height; y++) {
                                    floorLocation.add(x + ox, 0,y + oy);
                                    player.spawnParticle(Particle.END_ROD, floorLocation, 1, 0, 0, 0, 0);
                                    floorLocation.subtract(x + ox, 0,y + oy);
                                }
                            }


                        }
                    }
                }
            }


            if (player.hasPermission("fly.gamemode")) {
                if (player.getGameMode().equals(GameMode.CREATIVE)) {
                    player.setAllowFlight(true);
                    if (!flyers.contains(player.getName())) {
                        flyers.add(player.getName());
                    }
                }
            }

            if (!player.getAllowFlight()) {
                flyers.remove(player.getName());
            }

            if (player.hasPermission("fly.admin")) {
                if (NotifyDust.get(player)) {
                    for (int p = 0; p < list.size(); p++) {
                        Player target = list.get(p);
                        if (flyers.contains(target.getName())) {
                            if (target.isFlying()) {
                                if (timer.containsKey(target.getName())) {
                                    Location tloc = target.getLocation();
                                    Double x = tloc.getX();
                                    Double y = tloc.getY() + 2.1;
                                    Double z = tloc.getZ();
                                    tloc.add(0, 2, 0);
                                    player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0.001, 1, 0, 1, new Particle.DustOptions(Color.YELLOW, 1));
                                } else {
                                    Location tloc = target.getLocation();
                                    Double x = tloc.getX();
                                    Double y = tloc.getY() + 2.1;
                                    Double z = tloc.getZ();
                                    tloc.add(0, 2, 0);
                                    player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0.001, 1, 0, 1, new Particle.DustOptions(Color.GREEN, 1));
                                }
                            } else {
                                Location tloc = target.getLocation();
                                Double x = tloc.getX();
                                Double y = tloc.getY() + 2.1;
                                Double z = tloc.getZ();
                                tloc.add(0, 2, 0);
                                player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0.001, 1, 0, 1, new Particle.DustOptions(Color.RED, 1));
                            }
                        }
                    }
                }
            }
            //Others

            if (player.isOnGround()) {
                if (GroundHitters.contains(player)) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        GroundHitters.remove(player);
                    }, 40L);
                }
            }

        }




    }
}