package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.timer.TempCheck;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static fr.radi3nt.fly.events.OnGroundHit.GroundHitters;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Tempfly implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    public static Map<Player, Long> cooldown = new HashMap<Player, Long>();

    public static Map<String, Long> timer = new HashMap<>();
    public static Map<String, Integer> time = new HashMap<>();
    public ArrayList<String> flyers = Fly.flyers;


    HashMap<Player, Integer> timem = TempCheck.timem;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String WrongArgs = plugin.getConfig().getString("wrong-args");
        String NoArgs = plugin.getConfig().getString("no-args");
        String InvalidPlayer = plugin.getConfig().getString("invalid-player");
        String NoPermission = plugin.getConfig().getString("no-permission");


        Boolean IsCooldown = plugin.getConfig().getBoolean("cooldown-per-player");
        Boolean Stackable = plugin.getConfig().getBoolean("stack-tempfly");


        String MessagePT = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tempfly-message"));
        String MessageTP = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tempfly-target"));
        String MessageP = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tempfly-player"));

        Boolean TargetMessage = plugin.getConfig().getBoolean("tempfly-target-message");

        File locations = new File("plugins/FlyPlugin", "flyers.yml");
        if (!locations.exists()) {
            try {
                locations.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration loc = YamlConfiguration.loadConfiguration(locations);


        if (!(sender instanceof Player)) {
            if (args.length < 1) {
                sender.sendMessage(Prefix + " " + NoArgs);
            } else {
                StringBuilder sb = new StringBuilder();
                boolean found = false;
                for (char c : args[0].toCharArray()) {
                    if (Character.isDigit(c)) {
                        sb.append(c);
                        found = true;
                    } else if (found) {
                        // If we already found a digit before and this char is not a digit, stop looping
                        break;
                    }
                }

                if (sb.toString().isEmpty()) {
                    sender.sendMessage(ChatColor.RED + WrongArgs);
                    return false;
                }
                if (!(args.length == 1)) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target instanceof Player) {
                        if (Stackable) {
                            if (time.containsKey(target.getName())) {
                                time.put(target.getName(), (int) (Integer.parseInt((sb.toString())) + ((timer.get(target.getName()) / 1000) + time.get(target.getName())) - (System.currentTimeMillis() / 1000)));
                            } else {
                                time.put(target.getName(), Integer.parseInt((sb.toString())));
                            }
                        } else {
                            time.put(target.getName(), Integer.parseInt((sb.toString())));
                        }
                        if (time.get(target.getName()) > 0 && time.get(target.getName()) < 86400) {
                            flyers.remove(target.getName());
                            flyers.remove(target.getName());
                            loc.set("flyers." + target.getName(), time.get(target.getName()));
                            FlyMethod(target, true);
                            timem.put(target, 100000);
                            timer.put(target.getName(), System.currentTimeMillis());
                            int timeleft = time.get(target.getName());
                            int heures = (timeleft / 3600);
                            int minutes = ((timeleft - (timeleft / 3600) * 3600) / 60);
                            int seconds = timeleft - (heures * 3600 + minutes * 60);
                            String TimeleftPT = MessagePT.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%target%", target.getName());
                            sender.sendMessage(Prefix + " " + TimeleftPT);
                            if (TargetMessage) {
                                String TimeleftTP = MessageTP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%player%", "console");
                                target.sendMessage(Prefix + " " + TimeleftTP);
                            }
                        } else {
                            sender.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                        }

                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                    }
                } else {
                    sender.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                }
            }
        } else {
            Player player = (Player) sender;
            if (player.hasPermission("fly.tempfly")) {
                if (args.length < 1) {
                    player.sendMessage(Prefix + " " + WrongArgs);
                } else {
                    if (!IsCooldown || !cooldown.containsKey(player) || player.hasPermission("fly.tempflybypass")) {
                        StringBuilder sb = new StringBuilder();
                        boolean found = false;
                        for (char c : args[0].toCharArray()) {
                            if (Character.isDigit(c)) {
                                sb.append(c);
                                found = true;
                            } else if (found) {
                                // If we already found a digit before and this char is not a digit, stop looping
                                break;
                            }
                        }

                        if (sb.toString().isEmpty()) {
                            player.sendMessage(ChatColor.RED + WrongArgs);
                            return false;
                        }
                        if (!(args.length == 1)) {
                            if (player.hasPermission("fly.tempflyothers")) {
                                Player target = Bukkit.getPlayerExact(args[1]);
                                if (target instanceof Player) {
                                    if (Stackable) {
                                        if (time.containsKey(target.getName())) {
                                            time.put(target.getName(), (int) (Integer.parseInt((sb.toString())) + ((timer.get(target.getName()) / 1000) + time.get(target.getName())) - (System.currentTimeMillis() / 1000)));
                                        } else {
                                            time.put(target.getName(), Integer.parseInt((sb.toString())));
                                        }
                                    } else {
                                        time.put(target.getName(), Integer.parseInt((sb.toString())));
                                    }
                                    if (time.get(target.getName()) > 0 && time.get(target.getName()) < 86400) {
                                        flyers.remove(target.getName());
                                        flyers.remove(target.getName());
                                        player.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, 2);
                                        loc.set("flyers." + target.getName(), time.get(target.getName()));
                                        FlyMethod(target, true);
                                        timer.put(target.getName(), System.currentTimeMillis());
                                        timem.put(target, 100000);
                                        int timeleft = time.get(target.getName());
                                        int heures = (timeleft / 3600);
                                        int minutes = ((timeleft - (timeleft / 3600) * 3600) / 60);
                                        int seconds = timeleft - (heures * 3600 + minutes * 60);
                                        String TimeleftPT = MessagePT.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%target%", target.getName());
                                        sender.sendMessage(Prefix + " " + TimeleftPT);
                                        cooldown.put(player, System.currentTimeMillis());
                                        if (TargetMessage) {
                                            String TimeleftTP = MessageTP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%player%", player.getName());
                                            target.sendMessage(Prefix + " " + TimeleftTP);
                                        }
                                    } else {
                                        player.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                                    }

                                } else {
                                    player.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                                }
                            } else {
                                player.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
                            }
                        } else {

                            if (Integer.parseInt((sb.toString())) > 0 && Integer.parseInt((sb.toString())) < 86400) {
                                int maxcount = 0;
                                for (int i = 0; i < 86400; i++) {

                                    if (player.hasPermission("fly.tempfly." + i)) {
                                        maxcount = i;
                                    }

                                }

                                if (maxcount >= Integer.parseInt((sb.toString()))) {
                                    if (Stackable) {
                                        if (time.containsKey(player.getName())) {
                                            time.put(player.getName(), (int) (Integer.parseInt((sb.toString())) + ((timer.get(player.getName()) / 1000) + time.get(player.getName())) - (System.currentTimeMillis() / 1000)));
                                        } else {
                                            time.put(player.getName(), Integer.parseInt((sb.toString())));
                                        }
                                    } else {
                                        time.put(player.getName(), Integer.parseInt((sb.toString())));
                                    }
                                    flyers.remove(player.getName());
                                    loc.set("flyers." + player.getName(), time.get(player.getName()));
                                    FlyMethod(player, true);
                                    timem.put(player, 100000);
                                    timer.put(player.getName(), System.currentTimeMillis());
                                    int timeleft = time.get(player.getName());
                                    int heures = (timeleft / 3600);
                                    int minutes = ((timeleft - (timeleft / 3600) * 3600) / 60);
                                    int seconds = timeleft - (heures * 3600 + minutes * 60);
                                    String TimeleftP = MessageP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                                    sender.sendMessage(Prefix + " " + TimeleftP);
                                    cooldown.put(player, System.currentTimeMillis());
                                } else {
                                    player.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
                                }
                            } else {
                                player.sendMessage(Prefix + ChatColor.RED + " " + WrongArgs);
                            }

                        }
                    } else {
                        int maxcount = 0;
                        for (int i = 0; i < 86400; i++) {

                            if (player.hasPermission("fly.cooldown." + i)) {
                                maxcount = i;
                            }

                        }
                        int secondes = maxcount;
                        long timeleft = ((cooldown.get(player) / 1000) + secondes) - (System.currentTimeMillis() / 1000);
                        String CooldownMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("cooldown-message")) + ChatColor.RESET;
                        String CooldownMessageM = CooldownMessage.replace("%timeleft%", String.valueOf(timeleft));
                        player.sendMessage(Prefix + " " + CooldownMessageM);
                    }
                }
            } else {
                player.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
            }
        }
        try {
            loc.save(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void FlyMethod(Player player, boolean state) {
        player.setAllowFlight(state);
        if (state) {
            flyers.add(player.getName());
            Boolean Particles = plugin.getConfig().getBoolean("particles");
            if (Particles) {
                new BukkitRunnable() {
                    final Location loc = player.getLocation();
                    final double r = 1;
                    double t = 0;

                    public void run() {
                        t = t + Math.PI / 16;
                        double x = r * cos(t);
                        double y = 0.225 * t;
                        double z = r * sin(t);
                        loc.add(x, y, z);
                        player.getWorld().spawnParticle(Particle.FLAME, loc.getX(), loc.getY(), loc.getZ(), 1, 0, 0, 0, 0, null, true);
                        loc.subtract(x, y, z);
                        if (t > Math.PI * 3) {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0, 1);
            }
        } else {
            flyers.remove(player.getName());
            player.setInvulnerable(false);
            GroundHitters.add(player);
        }
    }
}