package fr.radi3nt.fly.events.crafts;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.HashMap;

public class PrepareCraftItemEvent implements Listener {

    public static HashMap<CraftingInventory, Integer> PotionCraft = new HashMap<>();

    public static boolean CheckForEmerauldMateral(ItemStack[] craftingitems, int number, int g) {
        Material E = Material.BEDROCK;
        switch (g) {
            case 0:
                E = Material.EMERALD;
                break;
            case 1:
                E = Material.EMERALD_BLOCK;
                break;
            case 2:
                E = Material.DIAMOND;
                break;
            case 3:
                E = Material.DIAMOND_BLOCK;
                break;
            case 4:
                E = Material.GOLD_INGOT;
                break;
            case 5:
                E = Material.GOLD_BLOCK;
                break;
        }
        Material B = Material.GLASS_BOTTLE;

        for (int l = 0; l < craftingitems.length; l++) {
            if (craftingitems[l] == null) {
                return false;
            }
            if (l != 4) {
                if (!craftingitems[l].getType().equals(E) || !(craftingitems[l].getAmount() >= number)) {
                    return false;
                }
            } else {
                if (!craftingitems[l].getType().equals(B) || !(craftingitems[l].getAmount() >= 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ItemStack CreateFlyPotion(int heures, int minutes, int seconds) {
        ItemStack item = new ItemStack(Material.POTION, 1);
        ItemMeta meta = item.getItemMeta();
        if (heures < 1) {
            if (minutes < 1) {
                meta.setDisplayName(ChatColor.GOLD + "Fly Potion " + seconds + " sec");
            } else {
                meta.setDisplayName(ChatColor.GOLD + "Fly Potion " + minutes + " min " + seconds + " sec");
            }
        } else {
            meta.setDisplayName(ChatColor.GOLD + "Fly Potion " + heures + " h " + minutes + " min " + seconds + " sec");
        }
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.setColor(Color.FUCHSIA);
        item.setItemMeta(potionMeta);
        return item;
    }

    @EventHandler
    public void PrepareCraftItemEvent(PrepareItemCraftEvent e) {
        if (MainFly.getPlugin(MainFly.class).getConfig().getBoolean("fly-potions")) {
            for (int l = 0; l < 6; l++) {
                ItemStack[] craftingitems = e.getInventory().getMatrix();
                ItemStack finalitem = new ItemStack(Material.AIR);
                int z = 0;
                int emerald = 15;
                int diamond = 5;
                int gold = 1;
                for (int i = 0; i <= 64; i++) {
                    int temps = i;
                    switch (l) {
                        case 0:
                            temps = i * emerald + emerald;
                            break;
                        case 1:
                            temps = i * emerald * 9 + i * emerald * 9;
                            break;
                        case 2:
                            temps = i * diamond + diamond;
                            break;
                        case 3:
                            temps = i * diamond * 9 + diamond * 9;
                            break;
                        case 4:
                            temps = i * gold + gold;
                            break;
                        case 5:
                            temps = i * gold * 9 + gold * 9;
                            break;
                    }
                    int heures = (temps / 3600);
                    int minutes = ((temps - (temps / 3600) * 3600) / 60);
                    int seconds = temps - (heures * 3600 + minutes * 60);

                    ItemStack item = CreateFlyPotion(heures, minutes, seconds);

                    if (CheckForEmerauldMateral(craftingitems, i + 1, l)) {
                        finalitem.setType(item.getType());
                        finalitem.setItemMeta(item.getItemMeta());
                        e.getInventory().setResult(null);
                        PotionCraft.remove(e.getInventory());
                        z = i;
                    }

                }
                if (!finalitem.getType().equals(Material.AIR)) {
                    e.getInventory().setResult(finalitem);
                    PotionCraft.put(e.getInventory(), z + 1);
                }
            }
        }
    }

}
