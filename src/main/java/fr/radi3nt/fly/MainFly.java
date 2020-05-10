package fr.radi3nt.fly;

import fr.radi3nt.fly.commands.*;
import fr.radi3nt.fly.events.*;
import fr.radi3nt.fly.timer.checker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.SoundCategory;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class MainFly extends JavaPlugin {

    HashMap<Player, Boolean> NotifyChat = FlyAlert.NotifyChat;
    HashMap<Player, Boolean> NotifyTitle = FlyAlert.NotifyTitle;
    HashMap<Player, Boolean> NotifyBossBar = FlyAlert.NotifyBossBar;
    HashMap<Player, Boolean> NotifySounds = FlyAlert.NotifySounds;

    public ArrayList<String> flyers = Fly.flyers;
    String Prefix = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("prefix") + ChatColor.RESET);
    ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {


        console.sendMessage(ChatColor.GOLD + "[Fly] " + ChatColor.YELLOW + "Starting up !");
        console.sendMessage(ChatColor.GOLD + "[Fly] " + ChatColor.YELLOW + "Fly Plugin by " + ChatColor.AQUA + ChatColor.BOLD + "Radi3nt");
        console.sendMessage(ChatColor.GOLD + "[Fly] " + ChatColor.YELLOW + "If you have any issues, please report it");

        getServer().getPluginManager().registerEvents(new GetEntityDamaged(), this);
        getServer().getPluginManager().registerEvents(new OnWorldChange(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerFlying(), this);
        getServer().getPluginManager().registerEvents(new OnGamemodeChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDisconnect(), this);
        getServer().getPluginManager().registerEvents(new OnFlyGuiClick(), this);

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

        checker task = new checker();
        task.runTaskTimer(this, 0, 1);


        getConfig().options().copyDefaults();
        saveDefaultConfig();

        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < list.size(); i++){
            Player player = list.get(i);
            NotifyChat.remove(player);
            NotifyTitle.remove(player);
            NotifyBossBar.remove(player);
            NotifySounds.remove(player);
            NotifyChat.put(player, true);
            NotifyTitle.put(player, true);
            NotifyBossBar.put(player, true);
            NotifySounds.put(player, true);
            if (player.getGameMode().equals(GameMode.CREATIVE)) {
                player.setAllowFlight(true);
                player.setFlying(true);
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
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).hasPermission("fly.admin")) {
                list.get(i).sendMessage(Prefix + ChatColor.RED + " Reloading is not very compatible with the plugin ... \n We highly recommend to restart your server, \n if you don't want to have some hackers flying around");

                list.get(i).playSound(list.get(i).getLocation(), "minecraft:block.note_block.bit", SoundCategory.AMBIENT, 100, 1);
            }
            if (list.get(i).getGameMode().equals(GameMode.CREATIVE)) {
                list.get(i).setAllowFlight(true);
            } else {
                Fly.FlyMethod(list.get(i), false);
                Player player = list.get(i);
                NotifyChat.remove(player);
                NotifyTitle.remove(player);
                NotifyBossBar.remove(player);
                NotifySounds.remove(player);
                NotifyChat.put(player, true);
                NotifyTitle.put(player, true);
                NotifyBossBar.put(player, true);
                NotifySounds.put(player, true);
            }
        }
        flyers.clear();
    }

    public static int ReturnHours(int timeleft) {
        int heures = (int) (timeleft / 3600);
        return heures;
    }

    public static int ReturnMinutes(int timeleft) {
        int heures = (int) (timeleft / 3600);
        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
        return minutes;
    }

    public static int ReturnSeconds(int timeleft) {
        int heures = (int) (timeleft / 3600);
        int minutes = (int) ((timeleft - (timeleft / 3600) *3600) / 60);
        int seconds = (int) (timeleft - (heures*3600 + minutes*60));
        return seconds;
    }



}