package fr.radi3nt.fly.events.crafts;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

import static fr.radi3nt.fly.events.crafts.PrepareCraftItemEvent.CreateFlyPotion;

public class OnBrewFlyPotion implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);

    @EventHandler
    public void OnCraftEvent(BrewEvent e) {
        if (MainFly.getPlugin(MainFly.class).getConfig().getBoolean("fly-potions")) {
            try {
                if (e.getContents().getItem(0).getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Fly Potion ")) {
                    e.getContents().setItem(0, transformPotion(e.getContents().getItem(0)));
                }
                if (e.getContents().getItem(1).getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Fly Potion ")) {
                    e.getContents().setItem(0, transformPotion(e.getContents().getItem(0)));
                }
                if (e.getContents().getItem(2).getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Fly Potion ")) {
                    e.getContents().setItem(0, transformPotion(e.getContents().getItem(0)));
                }
            } catch (NullPointerException event) {

            }
        }
    }

    private ItemStack transformPotion(ItemStack item) {
        String[] split = item.getItemMeta().getDisplayName().split(" ");
        ArrayList<String> NameSplitted = new ArrayList<>();
        NameSplitted.addAll(Arrays.asList(split));
        int Tsec = 0;
        for (int i = 0; i < NameSplitted.size(); i++) {
            String name = NameSplitted.get(i);
            if (name.equals("sec")) {
                Tsec = Integer.parseInt(NameSplitted.get(i - 1).trim());
            }
        }
        int Tmin = 0;
        if (NameSplitted.contains("min")) {
            for (int i = 0; i < NameSplitted.size(); i++) {
                String name = NameSplitted.get(i);
                if (name.equals("min")) {
                    Tmin = Integer.parseInt(NameSplitted.get(i - 1).trim());
                }
            }
        }
        int Th = 0;
        if (NameSplitted.contains("h")) {
            for (int i = 0; i < NameSplitted.size(); i++) {
                String name = NameSplitted.get(i);
                if (name.equals("h")) {
                    Th = Integer.parseInt(NameSplitted.get(i - 1).trim());
                }
            }
        }
        ItemStack potion = CreateFlyPotion(Th, Tmin, Tsec);
        potion.setType(Material.SPLASH_POTION);
        return potion;
    }
}
