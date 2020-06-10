package fr.radi3nt.fly.events.wand;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import static fr.radi3nt.fly.commands.FlyZone.IMark;
import static fr.radi3nt.fly.commands.FlyZone.OMark;

public class OnClickWithWand implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);


    @EventHandler
    public void OnLeftClickWithWand(PlayerInteractEvent e) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String NoPermission = plugin.getConfig().getString("no-permission");


        Player player = e.getPlayer();
        if (player.getItemInHand().getType().equals(Material.ARROW) && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Fly Wand") && player.getItemInHand().getItemMeta().hasLore()) {
            if (player.hasPermission("fly.usewand")) {
                if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    String Lclick = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyzones-selectL") + ChatColor.RESET).replace("%pos%", e.getClickedBlock().getX() + "X, " + e.getClickedBlock().getY() + "Y, " + e.getClickedBlock().getZ() + "Z");
                    if (OMark.containsKey(player)) {
                        if (!OMark.get(player).equals(e.getClickedBlock().getLocation())) {
                            OMark.put(player, e.getClickedBlock().getLocation());
                            player.sendMessage(Prefix + " " + Lclick);
                        }
                    } else {
                        OMark.put(player, e.getClickedBlock().getLocation());
                        player.sendMessage(Prefix + " " + Lclick);
                    }
                } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    String Rclick = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyzones-selectR") + ChatColor.RESET).replace("%pos%", e.getClickedBlock().getX() + "X, " + e.getClickedBlock().getY() + "Y, " + e.getClickedBlock().getZ() + "Z");
                    if (IMark.containsKey(player)) {
                        if (!IMark.get(player).equals(e.getClickedBlock().getLocation())) {
                            IMark.put(player, e.getClickedBlock().getLocation());
                            player.sendMessage(Prefix + " " + Rclick);
                        }
                    } else {
                        IMark.put(player, e.getClickedBlock().getLocation());
                        player.sendMessage(Prefix + " " + Rclick);
                    }
                }
            } else {
                player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
            }
            e.setCancelled(true);
        }


    }


}
