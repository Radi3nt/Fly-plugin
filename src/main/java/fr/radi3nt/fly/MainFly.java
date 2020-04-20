package fr.radi3nt.fly;

import fr.radi3nt.fly.commands.*;
import fr.radi3nt.fly.events.*;
import fr.radi3nt.fly.timer.checker;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class MainFly extends JavaPlugin {

    public ArrayList<String> flyers = Fly.flyers;

    @Override
    public void onEnable() {

        System.out.println("[Fly] Starting up !");

        getServer().getPluginManager().registerEvents(new GetEntityDamaged(), this);
        getServer().getPluginManager().registerEvents(new OnWorldChange(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerFlying(), this);
        getServer().getPluginManager().registerEvents(new OnGamemodeChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDisconnect(), this);

        getCommand("fly").setExecutor(new Fly());
        getCommand("flyspeed").setTabCompleter(new fr.radi3nt.fly.tab.FlySpeed());
        getCommand("flyspeed").setExecutor(new FlySpeed());
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
        flyers.clear();
    }
}