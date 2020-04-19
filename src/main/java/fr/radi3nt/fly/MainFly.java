package fr.radi3nt.fly;

import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.FlyReload;
import fr.radi3nt.fly.commands.FlySpeed;
import fr.radi3nt.fly.commands.Tempfly;
import fr.radi3nt.fly.events.*;
import fr.radi3nt.fly.timer.checker;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainFly extends JavaPlugin {

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
        getCommand("flyreload").setExecutor(new FlyReload());

        checker task = new checker();
        task.runTaskTimer(this, 0, 1);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
}