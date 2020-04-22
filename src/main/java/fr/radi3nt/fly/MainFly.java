package fr.radi3nt.fly;

import fr.radi3nt.fly.commands.*;
import fr.radi3nt.fly.events.*;
import fr.radi3nt.fly.timer.checker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class MainFly extends JavaPlugin {

    public ArrayList<String> flyers = Fly.flyers;
    String Prefix = ChatColor.GOLD + this.getConfig().getString("prefix") + ChatColor.RESET;

    @Override
    public void onEnable() {

        System.out.println("[Fly] Starting up !");

        getServer().getPluginManager().registerEvents(new GetEntityDamaged(), this);
        getServer().getPluginManager().registerEvents(new OnWorldChange(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerFlying(), this);
        getServer().getPluginManager().registerEvents(new OnGamemodeChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDisconnect(), this);

        getCommand("fly").setExecutor(new Fly());
        getCommand("fly").setTabCompleter(new fr.radi3nt.fly.tab.Fly());
        getCommand("flyspeed").setExecutor(new FlySpeed());
        getCommand("flyspeed").setTabCompleter(new fr.radi3nt.fly.tab.FlySpeed());
        getCommand("tempfly").setExecutor(new Tempfly());
        getCommand("tempfly").setTabCompleter(new fr.radi3nt.fly.tab.Tempfly());
        getCommand("flyers").setExecutor(new Flyers());
        getCommand("flyreload").setExecutor(new FlyReload());

        checker task = new checker();
        task.runTaskTimer(this, 0, 1);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("Disabling ...");
        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).hasPermission("fly.admin")) {
                list.get(i).sendMessage(Prefix + ChatColor.RED + " Reloading is not very compatible with the plugin ...");
                list.get(i).sendMessage(Prefix + ChatColor.RED + " We highly recommend to restart your server,");
                list.get(i).sendMessage(Prefix + ChatColor.RED + " If you don't want to have some hackers flying around");
            }
        }
        flyers.clear();
    }
}