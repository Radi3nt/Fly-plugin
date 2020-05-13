package fr.radi3nt.fly;

import fr.radi3nt.fly.commands.*;
import fr.radi3nt.fly.events.*;
import fr.radi3nt.fly.timer.FlyVerify;
import fr.radi3nt.fly.timer.checker;
import fr.radi3nt.fly.utilis.UpdateCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.radi3nt.fly.commands.Fly.FlyMethod;
import static fr.radi3nt.fly.commands.FlyAlert.NotifyDust;
import static fr.radi3nt.fly.timer.checker.timem;

public final class MainFly extends JavaPlugin {

    HashMap<Player, Boolean> NotifyChat = FlyAlert.NotifyChat;
    HashMap<Player, Boolean> NotifyTitle = FlyAlert.NotifyTitle;
    HashMap<Player, Boolean> NotifyBossBar = FlyAlert.NotifyBossBar;
    HashMap<Player, Boolean> NotifySounds = FlyAlert.NotifySounds;

    public ArrayList<String> flyers = Fly.flyers;
    String Prefix = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("prefix") + ChatColor.RESET);
    ConsoleCommandSender console = Bukkit.getConsoleSender();


    //--------------------------------------------------//
    public static final String VERSION = "1.2.3e";
    //--------------------------------------------------//


    @Override
    public void onEnable() {


        console.sendMessage(ChatColor.GOLD + "[Fly] " + ChatColor.YELLOW + "Starting up !");
        console.sendMessage(ChatColor.GOLD + "[Fly] " + ChatColor.YELLOW + "Fly Plugin by " + ChatColor.AQUA + ChatColor.BOLD + "Radi3nt");
        console.sendMessage(ChatColor.GOLD + "[Fly] " + ChatColor.YELLOW + "If you have any issues, please report it");

        RegisterEvents();
        console.sendMessage(ChatColor.GOLD + "[Fly] " + ChatColor.YELLOW + "Registered Events");
        RegisterCommands();
        console.sendMessage(ChatColor.GOLD + "[Fly] " + ChatColor.YELLOW + "Registered Commands");

        checker task = new checker();
        task.runTaskTimer(this, 2, 1);

        UpdateCheck updater = new UpdateCheck();
        updater.run();

        FlyVerify flyverif = new FlyVerify();
        flyverif.runTaskTimer(this, 0, 1);


        this.getConfig().set("version", VERSION);

        File locations = new File("plugins/FlyPlugin", "flyers.yml");
        if (!locations.exists()) {
            try {
                locations.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration loc = YamlConfiguration.loadConfiguration(locations);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < list.size(); i++){
            Player player = list.get(i);
            NotifyChat.remove(player);
            NotifyTitle.remove(player);
            NotifyBossBar.remove(player);
            NotifySounds.remove(player);
            NotifyDust.remove(player);
            NotifyChat.put(player, true);
            NotifyTitle.put(player, true);
            NotifyBossBar.put(player, true);
            NotifySounds.put(player, true);
            NotifyDust.put(player, true);


            if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
                player.setAllowFlight(true);
                player.setFlying(true);
            }

            Map<String, Long> timer = Tempfly.timer;
            Map<String, Integer> time = Tempfly.time;
            if (loc.get("flyers." + player.getName()) != null) {
                Integer timeleft = (Integer) loc.get("flyers." + player.getName());
                FlyMethod(player, true);
                timer.put(player.getName(), System.currentTimeMillis());
                time.put(player.getName(), timeleft);
                timem.put(player, 100000);
                HashMap<Player,BossBar> bossbar = checker.bossbar;
                bossbar.put(player, Bukkit.createBossBar("Charging ...", BarColor.WHITE, BarStyle.SOLID));
                bossbar.get(player).addPlayer(player);
            }
        }
    }

    @Override
    public void onDisable() {
        console.sendMessage( ChatColor.GOLD + "[Fly] " + ChatColor.DARK_RED + "Disabling ...");
        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        NotifyBossBar.clear();
        NotifyTitle.clear();
        NotifyChat.clear();
        NotifySounds.clear();
        NotifyDust.clear();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).hasPermission("fly.admin")) {
                list.get(i).sendMessage(Prefix + ChatColor.RED + " Reloading is not very compatible with the plugin ... \n We highly recommend to restart your server, \n if you don't want to have some hackers flying around");

                list.get(i).playSound(list.get(i).getLocation(), "minecraft:block.note_block.bit", SoundCategory.AMBIENT, 100, 1);
            }
            if (list.get(i).getGameMode().equals(GameMode.CREATIVE)) {
                list.get(i).setAllowFlight(true);
                HashMap<Player,BossBar> bossbar = checker.bossbar;
                if (bossbar.containsKey(list.get(i))) {
                    bossbar.get(list.get(i)).removePlayer(list.get(i));
                    bossbar.remove(list.get(i));
                }
            } else {
                HashMap<Player,BossBar> bossbar = checker.bossbar;
                if (bossbar.containsKey(list.get(i))) {
                    bossbar.get(list.get(i)).removePlayer(list.get(i));
                    bossbar.remove(list.get(i));
                }
                Player player = list.get(i);
                NotifyChat.remove(player);
                NotifyTitle.remove(player);
                NotifyBossBar.remove(player);
                NotifySounds.remove(player);
                NotifyDust.remove(player);
                NotifyChat.put(player, true);
                NotifyTitle.put(player, true);
                NotifyBossBar.put(player, true);
                NotifySounds.put(player, true);
                NotifyDust.put(player, true);
            }
        }
        flyers.clear();
    }

    public void RegisterEvents() {
        getServer().getPluginManager().registerEvents(new GetEntityDamaged(), this);
        getServer().getPluginManager().registerEvents(new OnWorldChange(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerFlying(), this);
        getServer().getPluginManager().registerEvents(new OnGamemodeChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDisconnect(), this);
        getServer().getPluginManager().registerEvents(new OnFlyGuiClick(), this);
    }

    public void RegisterCommands() {
        getCommand("fly").setExecutor(new Fly());
        getCommand("fly").setTabCompleter(new fr.radi3nt.fly.tab.Fly());

        getCommand("flyspeed").setExecutor(new FlySpeed());
        getCommand("flyspeed").setTabCompleter(new fr.radi3nt.fly.tab.FlySpeed());

        getCommand("tempfly").setExecutor(new Tempfly());
        getCommand("tempfly").setTabCompleter(new fr.radi3nt.fly.tab.Tempfly());

        getCommand("timefly").setExecutor(new GetFlyTime());
        getCommand("timefly").setTabCompleter(new fr.radi3nt.fly.tab.GetFlyTime());

        getCommand("flyalert").setExecutor(new FlyAlert());
        getCommand("flyalert").setTabCompleter(new fr.radi3nt.fly.tab.FlyAlert());


        getCommand("flygui").setExecutor(new FlyGui());

        getCommand("flyers").setExecutor(new Flyers());

        getCommand("flyreload").setExecutor(new FlyReload());
    }
}