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
                    if (player.getLocation().getBlockY() >= perm) {
                        Vector vector = new Vector(player.getLocation().toVector().getX()/2, player.getLocation().toVector().getY(), player.getLocation().toVector().getZ()/2);
                        player.setVelocity(vector.multiply(-0.01));
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
