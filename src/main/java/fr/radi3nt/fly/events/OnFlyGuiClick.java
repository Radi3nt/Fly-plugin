package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.FlyGui;
import fr.radi3nt.fly.commands.Tempfly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static fr.radi3nt.fly.commands.Fly.FlyMethod;

public class OnFlyGuiClick implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);
    public static ArrayList<String> flyers = new ArrayList<>();
    public static Map<String, Long> timer = new HashMap<>();
    public Map<String, Integer> time = Tempfly.time;

    String FlySomeonePlayer = plugin.getConfig().getString("fly-someone-player");
    String FlySomeoneTarget = plugin.getConfig().getString("fly-someone-target");
    Boolean PlayerNameReval = plugin.getConfig().getBoolean("fly-player-name-reveal");
    Boolean TargetSendMessage = plugin.getConfig().getBoolean("fly-target-message");

    String NameReveal = plugin.getConfig().getString("temp-target-namereveal");
    String TargetMe = plugin.getConfig().getString("temp-target");

    Boolean TargetMessage = plugin.getConfig().getBoolean("temp-target-message");
    Boolean PlayerNameReveal = plugin.getConfig().getBoolean("temp-player-name-reveal");

    String TempLeft = plugin.getConfig().getString("temp-left");
    String TempMinute = plugin.getConfig().getString("temp-minutes");
    String TempSecond = plugin.getConfig().getString("temp-seconds");
    String TempHours = plugin.getConfig().getString("temp-hours");


    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);

    public static HashMap<Player, Integer> Secondes = new HashMap<>();
    public static HashMap<Player, Integer> Minutes = new HashMap<>();
    public static HashMap<Player, Integer> Hours = new HashMap<>();



    HashMap<Player, Player> players = FlyGui.players;

    @EventHandler
    public void OnFlyGuiClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "           === Fly GUI ===")) {
            Player target = players.get(player);
            switch (e.getCurrentItem().getType()) {
                case RED_WOOL:
                    player.closeInventory();
                    TargetFly(target, player, false);
                    players.remove(player);
                    break;

                case LIME_WOOL:
                    player.closeInventory();
                    TargetFly(target, player, true);
                    players.remove(player);
                    break;

                case GOLD_BLOCK:
                    player.closeInventory();
                    players.remove(player);
                    TempflyMethod(player, target);
                    break;
            }

            e.setCancelled(true);
        } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "        === Tempfly GUI ===")) {
            Player target = players.get(player);
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase( ChatColor.GOLD + TempHours)) {
                int HoursI;
                if (e.getClick().isLeftClick()) {
                    if (Hours.get(player) >= 23) {
                        HoursI = 23;
                        Hours.remove(player);
                        Hours.put(player, HoursI);
                    }
                    HoursI = Hours.get(player);
                    HoursI++;
                    e.getCurrentItem().setAmount(HoursI);
                    Hours.remove(player);
                    Hours.put(player, HoursI);
                } else if (e.getClick().isRightClick()) {
                    if (!(Hours.get(player) <= 1)) {
                        HoursI = Hours.get(player);
                        HoursI--;
                        e.getCurrentItem().setAmount(HoursI);
                        Hours.remove(player);
                        Hours.put(player, HoursI);
                    } else {
                        if (Hours.get(player) <= 0) {
                            HoursI = 0;
                            Hours.remove(player);
                            Hours.put(player, HoursI);
                        } else {
                            HoursI = Hours.get(player);
                            HoursI--;
                            Hours.remove(player);
                            Hours.put(player, HoursI);
                        }
                    }
                }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + TempMinute)) {
                    int MinuteI;
                    if (e.getClick().isLeftClick()) {
                        if (Minutes.get(player) >= 59) {
                            MinuteI = 59;
                            Minutes.remove(player);
                            Minutes.put(player, MinuteI);
                        }
                        MinuteI = Minutes.get(player);
                        MinuteI++;
                        e.getCurrentItem().setAmount(MinuteI);
                        Minutes.remove(player);
                        Minutes.put(player, MinuteI);
                    } else if (e.getClick().isRightClick()) {
                        if (!(Minutes.get(player) <= 1)) {
                            MinuteI = Minutes.get(player);
                            MinuteI--;
                            e.getCurrentItem().setAmount(MinuteI);
                            Minutes.remove(player);
                            Minutes.put(player, MinuteI);
                        } else {
                            if (Minutes.get(player) <= 0) {
                                MinuteI = 0;
                                Minutes.remove(player);
                                Minutes.put(player, MinuteI);
                            } else {
                                MinuteI = Minutes.get(player);
                                MinuteI--;
                                Minutes.remove(player);
                                Minutes.put(player, MinuteI);
                            }
                        }
                    }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + TempSecond)) {
                        int SecondesI;
                        if (e.getClick().isLeftClick()) {
                            if (Secondes.get(player) >= 59) {
                                SecondesI = 59;
                                Secondes.remove(player);
                                Secondes.put(player, SecondesI);
                            }
                            SecondesI = Secondes.get(player);
                            SecondesI++;
                            e.getCurrentItem().setAmount(SecondesI);
                            Secondes.remove(player);
                            Secondes.put(player, SecondesI);
                        } else if (e.getClick().isRightClick()) {
                            if (!(Secondes.get(player) <= 1)) {
                                SecondesI = Secondes.get(player);
                                SecondesI--;
                                e.getCurrentItem().setAmount(SecondesI);
                                Secondes.remove(player);
                                Secondes.put(player, SecondesI);
                            } else {
                                if (Secondes.get(player) <= 0) {
                                    SecondesI = 0;
                                    Secondes.remove(player);
                                    Secondes.put(player, SecondesI);
                                } else {
                                    SecondesI = Secondes.get(player);
                                    SecondesI--;
                                    Secondes.remove(player);
                                    Secondes.put(player, SecondesI);
                                }
                            }
                    }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Continue")) {

                int TimeLeft = ((Hours.get(player)*3600) + (Minutes.get(player)*60) + (Secondes.get(player)));
                flyers.remove(target.getName());
                flyers.remove(target.getName());
                FlyMethod(target, true);
                Tempfly.time.put(target.getName(), TimeLeft);
                Tempfly.timer.put(target.getName(), System.currentTimeMillis());
                player.sendMessage(Prefix + " " + target.getName() + " can fly for " + ChatColor.AQUA + (TimeLeft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((TimeLeft - (TimeLeft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (TimeLeft - (((TimeLeft / 3600) *3600) + (TimeLeft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                    if (TargetMessage) {
                        if (PlayerNameReveal) {
                            target.sendMessage(Prefix + " " + player.getName() + " " + NameReveal + ChatColor.AQUA + (time.get(target.getName()) / 3600) + " "+ TempHours);
                        } else {
                            target.sendMessage(Prefix + " " + TargetMe + " " + (TimeLeft / 3600) + " " + ChatColor.GREEN + TempHours + ", " + ChatColor.AQUA + ((TimeLeft - (TimeLeft / 3600) *3600) / 60) + " " + ChatColor.GREEN + TempMinute + " and " + ChatColor.AQUA + (TimeLeft - (((TimeLeft / 3600) *3600) + (TimeLeft/ 60) * 60)) + " " + ChatColor.GREEN + TempSecond);
                        }
                    }

                players.remove(player);
                player.closeInventory();

            }
            }

            e.setCancelled(true);
        }

    public void TargetFly(Player target, Player player, Boolean state) {
        FlyMethod(target, state);
        if (state) {
            player.sendMessage(Prefix + " " + FlySomeonePlayer + " on for " + target.getName());
            if (TargetSendMessage) {
                if (!PlayerNameReval) {
                    target.sendMessage(Prefix + " " + FlySomeoneTarget + " on");
                } else {
                    target.sendMessage(Prefix + " " + FlySomeoneTarget + " on by " + player.getName());
                }
            }
        } else {
            player.sendMessage(Prefix + " " + FlySomeonePlayer + " off for " + target.getName());
            if (TargetSendMessage) {
                if (!PlayerNameReval) {
                    target.sendMessage(Prefix + " " + FlySomeoneTarget + " off");
                } else {
                    target.sendMessage(Prefix + " " + FlySomeoneTarget + " off by " + player.getName());
                }
            }
        }
    }

    public void TempflyMethod(Player player, Player target) {
        if (player.hasPermission("fly.gui.*")) {
            Inventory tempflygui = Bukkit.createInventory(player, 36, ChatColor.GOLD + "        === Tempfly GUI ===");

            ItemStack secondes = new ItemStack(Material.ANVIL);
            ItemStack minute = new ItemStack(Material.CHIPPED_ANVIL);
            ItemStack heures = new ItemStack(Material.DAMAGED_ANVIL);


            ItemStack valider = new ItemStack(Material.ARROW);


            ArrayList<String> loreT = new ArrayList<>();
            loreT.add(ChatColor.DARK_RED + "left click to add");
            loreT.add(ChatColor.DARK_RED + "right click to remove");

            ItemMeta validerItemMeta = valider.getItemMeta();

            validerItemMeta.setDisplayName(ChatColor.GREEN + "Continue");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "---->");
            validerItemMeta.setLore(lore);
            valider.setItemMeta(validerItemMeta);


            ItemMeta smeta = secondes.getItemMeta();
            smeta.setDisplayName(ChatColor.GOLD + TempSecond);
            smeta.setLore(loreT);
            secondes.setItemMeta(smeta);

            ItemMeta mmeta = minute.getItemMeta();
            mmeta.setDisplayName(ChatColor.GOLD + TempMinute);
            mmeta.setLore(loreT);
            minute.setItemMeta(mmeta);

            ItemMeta hmeta = heures.getItemMeta();
            hmeta.setDisplayName(ChatColor.GOLD + TempHours);
            hmeta.setLore(loreT);
            heures.setItemMeta(hmeta);


            ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
            SkullMeta sk = (SkullMeta) playerhead.getItemMeta();
            ArrayList<String> loreh = new ArrayList<>();
            if (target.getAllowFlight()) {
                sk.setDisplayName(ChatColor.GREEN + target.getName());
                if (target.isFlying()) {
                    loreh.add(ChatColor.GREEN + target.getName() + " is flying");
                } else {
                    loreh.add(ChatColor.DARK_RED + target.getName() + " isn't flying");
                }
                sk.setLore(loreh);
            } else {
                sk.setDisplayName(ChatColor.DARK_RED + target.getName());
                loreh.add(ChatColor.DARK_RED + target.getName() + " isn't flying");
                sk.setLore(loreh);
            }
            sk.setOwner(target.getName());
            playerhead.setItemMeta(sk);


            tempflygui.setItem(4, playerhead);

            tempflygui.setItem(20, heures);
            tempflygui.setItem(22, minute);
            tempflygui.setItem(24, secondes);
            tempflygui.setItem(35, valider);


            Secondes.put(player, 0);
            Minutes.put(player, 0);
            Hours.put(player, 0);

            players.put(player, target);
            player.openInventory(tempflygui);
        }
    }

}
