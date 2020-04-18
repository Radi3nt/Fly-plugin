package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class GetEntityDamaged implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    String Prefix = ChatColor.GOLD + plugin.getConfig().getString("prefix") + ChatColor.RESET;
    String NoPermission = plugin.getConfig().getString("no-permission");

    @EventHandler
    public void GetEntityDamaged(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (damager instanceof Player) {
            if (((Player) damager).isFlying()) {
                if (!damager.hasPermission("fly.damage")) {
                    damager.sendMessage(Prefix + " " + ChatColor.RED +NoPermission);
                    e.setCancelled(true);
                }
            }
        }
    }

}
