package fr.radi3nt.fly.timer;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public static HashMap<Player, Integer> MaxHeight = new HashMap<>();
    public static HashMap<Player, Boolean> ZoneFlyers = new HashMap<>();
    private static int ZonesParticlesInterval = 0;
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();


    @Override
    public void run() {

        // SHIELD

        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        ArrayList<String> flyers = Fly.flyers;
        Map<String, Long> timer = Tempfly.timer;
        ArrayList<Boolean> InZones = new ArrayList<>();

        Boolean Particles = plugin.getConfig().getBoolean("particles");
        Boolean ShieldContact = plugin.getConfig().getBoolean("shield-contact-reaction");
        Integer Reelradius = plugin.getConfig().getInt("height-floor-radius");
        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);


        for (int i = 0; i < list.size(); i++) {
            Player player = list.get(i);


            //SIELD
            if (player.isFlying() && !player.getGameMode().equals(GameMode.SPECTATOR)) {
                if (player.hasPermission("fly.shield")) {

                    for (Entity entity : player.getNearbyEntities(1.25, 1.25, 1.25)) {
                        if (entity instanceof Player) {
                            Player target = (Player) entity;
                            if (!target.getGameMode().equals(GameMode.SPECTATOR)) {
                                if (target.isFlying() && target.hasPermission("fly.shield")) {
                                    if (ShieldContact) {
                                        Location exploadloc = new Location(player.getWorld(), (target.getLocation().getX() + player.getLocation().getX()) / 2, (target.getLocation().getY() + player.getLocation().getY()) / 2, (target.getLocation().getZ() + player.getLocation().getZ()) / 2);
                                        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, exploadloc, 4, 0, 0, 0, 0);
                                        Vector fromPlayerToTarget = entity.getLocation().toVector().subtract(player.getLocation().toVector());
                                        entity.setVelocity(fromPlayerToTarget.multiply(0.4));
                                    } else {
                                        Vector fromPlayerToTarget = entity.getLocation().toVector().subtract(player.getLocation().toVector());
                                        entity.setVelocity(fromPlayerToTarget.multiply(0.25));
                                    }
                                } else {
                                    Vector fromPlayerToTarget = entity.getLocation().toVector().subtract(player.getLocation().toVector());
                                    entity.setVelocity(fromPlayerToTarget.multiply(0.25));
                                }
                            }
                        }
                    }
                    if (Particles) {
                        if (number >= 4) {
                            for (int p = 0; p < list.size(); p++) {
                                Player target = list.get(p);
                                if (player != target) {
                                    for (double phi = 0; phi < 2 * Math.PI; phi += Math.PI / 10) {
                                        for (double tehta = 0; tehta <= 2 * Math.PI; tehta += Math.PI / 10) {
                                            Location location = player.getLocation();
                                            double r = 1.5;
                                            double x = r * cos(tehta) * sin(phi);
                                            double y = r * cos(phi);
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

            //HEIGHT LIMIT
            if (player.isFlying()) {
                if (player.hasPermission("fly.height")) {
                    int perm = 0;
                    if (!MaxHeight.containsKey(player)) {

                        for (int height = 0; height < 256; height++) {

                            if (player.hasPermission("fly.height." + height)) {
                                perm = height;
                            }
                        }
                        MaxHeight.put(player, perm);
                    }
                    perm = MaxHeight.get(player);
                    if (!(perm == 0)) {
                        int reelradius = Reelradius;
                        Location floorLocation = new Location(player.getLocation().getWorld(), player.getLocation().getBlockX(), perm + 2, player.getLocation().getBlockZ());
                        if (player.getLocation().getBlockY() >= perm) {
                            if (!pushed.contains(player)) {
                                pushed.add(player);
                                new BukkitRunnable() {

                                    double t = 0;
                                    final Location playerLocation = player.getLocation();

                                    public void run() {

                                        t = t + 0.1 * Math.PI;
                                        for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 16) {
                                            double x = t * cos(theta);
                                            double y = 2 * Math.exp(-0.1 * t) * sin(t) + 0.2;
                                            double z = t * sin(theta);
                                            playerLocation.add(x, y, z);
                                            if (t > 0.9) {
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
                            Vector vector = new Vector(0, player.getLocation().toVector().getY(), 0);
                            player.setVelocity(vector.multiply(-0.2));


                        }
                        if (Particles) {
                            if (perm - reelradius < player.getLocation().getBlockY()) {
                                //Display
                                int radius = reelradius - (perm - player.getLocation().getBlockY());

                                int ox = 0;
                                int oy = 0; // origin

                                for (int x = -radius; x < radius; x++) {
                                    int height = (int) Math.sqrt(radius * radius - x * x);

                                    for (int y = -height; y < height; y++) {
                                        if (radius > reelradius) {
                                            radius = reelradius;
                                        }
                                        floorLocation.add(x + ox, 0, y + oy);
                                        player.spawnParticle(Particle.END_ROD, floorLocation, 1, 0, 0, 0, 0);
                                        floorLocation.subtract(x + ox, 0, y + oy);
                                    }
                                }


                            }
                        }
                    }
                }
            }

            //CREATIVE REDO
            if (player.hasPermission("fly.gamemode")) {
                if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
                    player.setAllowFlight(true);
                    if (!flyers.contains(player.getName())) {
                        flyers.add(player.getName());
                    }
                }
            }

            //FLYERS REMOVER
            if (!player.getAllowFlight()) {
                flyers.remove(player.getName());
            }

            //DUST
            if (player.hasPermission("fly.admin")) {
                if (NotifyDust.get(player)) {
                    for (Player target : list) {
                        if (flyers.contains(target.getName()) && !target.getGameMode().equals(GameMode.SPECTATOR)) {
                            if (target.isFlying()) {
                                if (timer.containsKey(target.getName())) {
                                    Location tloc = target.getLocation();
                                    Double x = tloc.getX();
                                    Double y = tloc.getY() + 2.1;
                                    Double z = tloc.getZ();
                                    tloc.add(0, 2, 0);
                                    player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0, 0, 0, 0, new Particle.DustOptions(Color.YELLOW, 1));
                                } else {
                                    Location tloc = target.getLocation();
                                    Double x = tloc.getX();
                                    Double y = tloc.getY() + 2.1;
                                    Double z = tloc.getZ();
                                    tloc.add(0, 2, 0);
                                    player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0, 0, 0, 0, new Particle.DustOptions(Color.GREEN, 1));
                                }
                            } else {
                                Location tloc = target.getLocation();
                                Double x = tloc.getX();
                                Double y = tloc.getY() + 2.1;
                                Double z = tloc.getZ();
                                tloc.add(0, 2, 0);
                                player.spawnParticle(Particle.REDSTONE, x, y, z, 0, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                            }
                        }
                    }
                }
            }

            //AUTO GROUND REMOVE

            if (player.isOnGround()) {
                if (GroundHitters.contains(player)) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        GroundHitters.remove(player);
                    }, 40L);
                }
            }


            //Flying Area
            File FlyingZone = new File("plugins/FlyPlugin", "zones.yml");
            if (!FlyingZone.exists()) {
                try {
                    FlyingZone.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileConfiguration FlyingZoneConfig = YamlConfiguration.loadConfiguration(FlyingZone);
            try {
                for (String zones : FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false)) {
                    String world = FlyingZoneConfig.getString("Zones." + zones + ".World");
                    int Ox = FlyingZoneConfig.getInt("Zones." + zones + ".Ox");
                    int Oy = FlyingZoneConfig.getInt("Zones." + zones + ".Oy");
                    int Oz = FlyingZoneConfig.getInt("Zones." + zones + ".Oz");

                    int Ix = FlyingZoneConfig.getInt("Zones." + zones + ".Ix");
                    int Iy = FlyingZoneConfig.getInt("Zones." + zones + ".Iy");
                    int Iz = FlyingZoneConfig.getInt("Zones." + zones + ".Iz");

                    String particlesType = plugin.getConfig().getString("flyzones-particles");
                    Integer viewdist = plugin.getConfig().getInt("flyzones-viewdistance");

                    String perm = FlyingZoneConfig.getString("Zones." + zones + ".Perm");

                    if (perm != null) {
                        if (!perm.isEmpty()) {
                            if (player.hasPermission(perm)) {
                                InZones.add(CheckInFlyingZones(Ox, Oy, Oz, Ix, Iy, Iz, world, player));
                                if (Ix < Ox) {
                                    int NOx = Ox;
                                    Ox = Ix;
                                    Ix = NOx;
                                }
                                if (Iy < Oy) {
                                    int NOy = Oy;
                                    Oy = Iy;
                                    Iy = NOy;
                                }
                                if (Iz < Oz) {
                                    int NOz = Oz;
                                    Oz = Iz;
                                    Iz = NOz;
                                }
                                ZonesParticlesInterval++;
                                if (ZonesParticlesInterval >= 12) {
                                    for (int x = Ox; x <= Ix + 1; x++) {
                                        for (int y = Oy; y <= Iy + 1; y++) {
                                            for (int z = Oz; z <= Iz + 1; z++) {
                                                if (Particles) {
                                                    if (x == Ox || y == Oy || z == Oz || x == Ix + 1 || y == Iy + 1 || z == Iz + 1) {
                                                        try {
                                                            Location checkLoc = new Location(Bukkit.getWorld(world), x, y, z);
                                                            int distance = (int) player.getLocation().distance(checkLoc);
                                                            if (distance < viewdist) {
                                                                try {
                                                                    player.spawnParticle(Particle.valueOf(particlesType), checkLoc, 1, 0, 0, 0, 0);
                                                                    //Default Particle.CRIT_MAGIC
                                                                } catch (IllegalArgumentException e) {
                                                                    console.sendMessage(Prefix + ChatColor.RED + " Error in config: " + particlesType + " is invalid");
                                                                }
                                                            }
                                                        } catch (Exception e) {
                                                            //EXCEPTION
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        InZones.add(CheckInFlyingZones(Ox, Oy, Oz, Ix, Iy, Iz, world, player));
                        if (Ix < Ox) {
                            int NOx = Ox;
                            Ox = Ix;
                            Ix = NOx;
                        }
                        if (Iy < Oy) {
                            int NOy = Oy;
                            Oy = Iy;
                            Iy = NOy;
                        }
                        if (Iz < Oz) {
                            int NOz = Oz;
                            Oz = Iz;
                            Iz = NOz;
                        }
                        ZonesParticlesInterval++;
                        if (ZonesParticlesInterval >= 12) {
                            for (int x = Ox; x <= Ix + 1; x++) {
                                for (int y = Oy; y <= Iy + 1; y++) {
                                    for (int z = Oz; z <= Iz + 1; z++) {
                                        if (Particles) {
                                            if (x == Ox || y == Oy || z == Oz || x == Ix + 1 || y == Iy + 1 || z == Iz + 1) {
                                                try {
                                                    Location checkLoc = new Location(Bukkit.getWorld(world), x, y, z);
                                                    int distance = (int) player.getLocation().distance(checkLoc);
                                                    if (distance < viewdist) {
                                                        try {
                                                            player.spawnParticle(Particle.valueOf(particlesType), checkLoc, 1, 0, 0, 0, 0);
                                                            //Default Particle.CRIT_MAGIC
                                                        } catch (IllegalArgumentException e) {
                                                            console.sendMessage(Prefix + ChatColor.RED + " Error in config: " + particlesType + " is invalid");
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    //EXCEPTION
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }


                }
                if (InZones.contains(true)) {
                    if (!ZoneFlyers.containsKey(player)) {
                        ZoneFlyers.put(player, player.getAllowFlight() && !Tempfly.time.containsKey(player.getName()));
                        Fly.FlyMethod(player, true);
                        player.setFlying(true);
                    }
                    if (!player.getAllowFlight()) {
                        Fly.FlyMethod(player, true);
                    }
                } else {
                    if (ZoneFlyers.containsKey(player)) {
                        if (!Tempfly.time.containsKey(player.getName()) && !ZoneFlyers.get(player)) {
                            Fly.FlyMethod(player, false);
                            GroundHitters.add(player);
                        }
                        ZoneFlyers.remove(player);
                    }
                }
                InZones.clear();
            } catch (NullPointerException e) {
                //Exeption
            }

        }
    }

    private boolean CheckInFlyingZones(Integer Ox, Integer Oy, Integer Oz, Integer Ix, Integer Iy, Integer Iz, String world, Player player) {
        if (Ox < Ix) {
            return xO(Ox, Oy, Oz, Ix, Iy, Iz, world, player);
        } else {
            return xI(Ox, Oy, Oz, Ix, Iy, Iz, world, player);
        }

    }

    public boolean xO(Integer Ox, Integer Oy, Integer Oz, Integer Ix, Integer Iy, Integer Iz, String world, Player player) {
        for (int x = Ox; x <= Ix; x++) {
            if (Oy < Iy) {
                if (yO(Ox, Oy, Oz, Ix, Iy, Iz, world, x, player)) {
                    return true;
                }
            } else {
                if (yI(Ox, Oy, Oz, Ix, Iy, Iz, world, x, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean yO(Integer Ox, Integer Oy, Integer Oz, Integer Ix, Integer Iy, Integer Iz, String world, Integer x, Player player) {
        for (int y = Oy; y <= Iy; y++) {
            if (Oz < Iz) {
                if (zO(Ox, Oy, Oz, Ix, Iy, Iz, world, x, y, player)) {
                    return true;
                }
            } else {
                if (zI(Ox, Oy, Oz, Ix, Iy, Iz, world, x, y, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean zO(Integer Ox, Integer Oy, Integer Oz, Integer Ix, Integer Iy, Integer Iz, String world, Integer x, Integer y, Player player) {
        for (int z = Oz; z <= Iz; z++) {
            try {
                Location checkLoc = new Location(Bukkit.getWorld(world), x, y, z);
                if (player.getLocation().getBlock().equals(checkLoc.getBlock())) {
                    return true;
                }
            } catch (Exception e) {
                //Something
            }
        }
        return false;
    }

    public Boolean xI(Integer Ox, Integer Oy, Integer Oz, Integer Ix, Integer Iy, Integer Iz, String world, Player player) {
        for (int x = Ix; x <= Ox; x++) {
            if (Oy < Iy) {
                if (yO(Ox, Oy, Oz, Ix, Iy, Iz, world, x, player)) {
                    return true;
                }
            } else {
                if (yI(Ox, Oy, Oz, Ix, Iy, Iz, world, x, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean yI(Integer Ox, Integer Oy, Integer Oz, Integer Ix, Integer Iy, Integer Iz, String world, Integer x, Player player) {
        for (int y = Iy; y <= Oy; y++) {
            if (Oz < Iz) {
                if (zO(Ox, Oy, Oz, Ix, Iy, Iz, world, x, y, player)) {
                    return true;
                }
            } else {
                if (zI(Ox, Oy, Oz, Ix, Iy, Iz, world, x, y, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean zI(Integer Ox, Integer Oy, Integer Oz, Integer Ix, Integer Iy, Integer Iz, String world, Integer x, Integer y, Player player) {
        for (int z = Iz; z <= Oz; z++) {
            try {
                Location checkLoc = new Location(Bukkit.getWorld(world), x, y, z);
                if (player.getLocation().getBlock().equals(checkLoc.getBlock())) {
                    return true;
                }
            } catch (Exception e) {
                //Something
            }
        }
        return false;
    }


}
