package fr.radi3nt.fly.events.crafts;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftInventoryCrafting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import static fr.radi3nt.fly.events.crafts.PrepareCraftItemEvent.CreateFlyPotion;
import static fr.radi3nt.fly.events.crafts.PrepareCraftItemEvent.PotionCraft;

public class OnCraftEvent implements Listener {

    @EventHandler
    public void OnCraftEvent(InventoryClickEvent e) {
        if (MainFly.getPlugin(MainFly.class).getConfig().getBoolean("fly-potions")) {
            if (e.getWhoClicked().hasPermission("fly.potion")) {
                if (e.getSlotType().equals(InventoryType.SlotType.RESULT)) {
                    if (e.getView().getTopInventory() instanceof CraftInventoryCrafting) {

                        CraftingInventory inventory = (CraftingInventory) e.getClickedInventory();
                        if (e.getCurrentItem().getType() != Material.AIR) {
                            if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Fly Potion ")) {
                                ItemStack[] matrix = inventory.getMatrix();
                                Material Base = matrix[0].getType();
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
                                int temps = 0;
                                int i = (PotionCraft.get(inventory) - 1);
                                int emerald = 15;
                                int diamond = 5;
                                int gold = 1;
                                switch (Base) {
                                    case EMERALD:
                                        temps = i * emerald + emerald;
                                        break;
                                    case EMERALD_BLOCK:
                                        temps = i * emerald * 9 + i * emerald * 9;
                                        break;
                                    case DIAMOND:
                                        temps = i * diamond + diamond;
                                        break;
                                    case DIAMOND_BLOCK:
                                        temps = i * diamond * 9 + diamond * 9;
                                        break;
                                    case GOLD_INGOT:
                                        temps = i * gold + gold;
                                        break;
                                    case GOLD_BLOCK:
                                        temps = i * gold * 9 + gold * 9;
                                        break;
                                }
                                inventory.setMatrix(matrix);
                                int heures = (temps / 3600);
                                int minutes = ((temps - (temps / 3600) * 3600) / 60);
                                int seconds = temps - (heures * 3600 + minutes * 60);

                                ItemStack item = CreateFlyPotion(heures, minutes, seconds);
                                e.getWhoClicked().setItemOnCursor(item);
                                PotionCraft.remove(inventory);

                            }
                        }
                    }
                }
            }
        }
    }

}
