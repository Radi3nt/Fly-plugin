package fr.radi3nt.fly.events.crafts;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import static fr.radi3nt.fly.events.crafts.PrepareCraftItemEvent.PotionCraft;

public class OnCraftEvent implements Listener {

    @EventHandler
    public void OnCraftEvent(InventoryClickEvent e) {
        if (MainFly.getPlugin(MainFly.class).getConfig().getBoolean("fly-potions")) {
            //Just want to test this :) e.getWhoClicked().setWindowProperty(InventoryView.Property.BREW_TIME, 1);
            if (e.getWhoClicked().hasPermission("fly.potion")) {
                if (e.getSlotType().equals(InventoryType.SlotType.RESULT)) {
                    CraftingInventory inventory = (CraftingInventory) e.getClickedInventory();
                    if (e.getCurrentItem().getType() != Material.AIR) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Fly Potion ")) {
                            ItemStack[] matrix = inventory.getMatrix();
                            for (ItemStack itemStack : matrix) {
                                if (itemStack.getType().equals(matrix[4].getType())) {
                                    itemStack.setAmount(itemStack.getAmount() - 1);
                                } else {
                                    if (itemStack.getAmount() - PotionCraft.get(inventory) >= 0) {
                                        itemStack.setAmount(itemStack.getAmount() - PotionCraft.get(inventory));
                                    } else {
                                        itemStack.setType(Material.AIR);
                                    }
                                }
                            }
                            inventory.setMatrix(matrix);
                            int temps = (PotionCraft.get(inventory) - 1) * 15 + 15;
                            int heures = (temps / 3600);
                            int minutes = ((temps - (temps / 3600) * 3600) / 60);
                            int seconds = temps - (heures * 3600 + minutes * 60);

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
                            e.getWhoClicked().setItemOnCursor(item);
                            PotionCraft.remove(inventory);

                        }
                    }
                }
            }
        } else {
            e.setCancelled(true);
        }
    }

}
