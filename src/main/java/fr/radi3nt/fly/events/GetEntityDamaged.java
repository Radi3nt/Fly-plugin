package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class GetEntityDamaged implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
    String NoDamage = plugin.getConfig().getString("no-damage");


    @EventHandler
    public void GetEntityDamaged(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (damager instanceof Player) {
            if (((Player) damager).isFlying()) {
                if (!damager.hasPermission("fly.damage")) {
                    Player player = (Player) damager;
                    damager.sendMessage(Prefix + " " + ChatColor.RED + NoDamage);
                    player.playSound(player.getLocation(), "minecraft:block.note_block.pling", SoundCategory.AMBIENT, 100, (float) 0.1);
                    e.setCancelled(true);
                }
            }
        }
    }

}
