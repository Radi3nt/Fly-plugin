package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
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
import static fr.radi3nt.fly.commands.FlyAlert.*;
import static fr.radi3nt.fly.events.OnGroundHit.GroundHitters;
import static fr.radi3nt.fly.timer.TempCheck.timem;

public class OnFlyGuiClick implements Listener {

    Plugin plugin = MainFly.getPlugin(MainFly.class);
    public static ArrayList<String> flyers = Fly.flyers;
    public static Map<String, Long> timer = new HashMap<>();
    public Map<String, Integer> time = Tempfly.time;


    public static HashMap<Player, Integer> Secondes = new HashMap<>();
    public static HashMap<Player, Integer> Minutes = new HashMap<>();
    public static HashMap<Player, Integer> Hours = new HashMap<>();




    @EventHandler
    public void OnFlyGuiClick(InventoryClickEvent e) {

        Boolean TargetMessage = plugin.getConfig().getBoolean("temp-target-message");

        String NoPermission = plugin.getConfig().getString("no-permission");


        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);


        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "           === Fly GUI ===")) {
            String name = e.getView().getItem(4).getItemMeta().getDisplayName();
            name = ChatColor.stripColor(name);
            Player target = player.getServer().getPlayer(name);
            switch (e.getCurrentItem().getType()) {
                case RED_WOOL:
                    if (target == player) {
                        if (player.hasPermission("fly.fly")) {
                            if (timer.containsKey(player.getName())) {
                                player.setAllowFlight(false);
                                player.setFlying(false);
                                player.setInvulnerable(false);
                                time.put(player.getName(), 1);
                                timer.put(player.getName(), System.currentTimeMillis());
                                Boolean Chat = NotifyChat.get(player);
                                Boolean BossBar = NotifyBossBar.get(player);
                                Boolean Title = NotifyTitle.get(player);
                                Boolean Sounds = NotifySounds.get(player);

                                GroundHitters.add(player);


                                NotifyChat.put(player, false);
                                NotifyBossBar.put(player, false);
                                NotifyTitle.put(player, false);
                                NotifySounds.put(player, false);

                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    NotifyChat.put(player, Chat);
                                    NotifyBossBar.put(player, BossBar);
                                    NotifyTitle.put(player, Title);
                                    NotifySounds.put(player, Sounds);
                                }, 50L);
                            }
                            player.closeInventory();
                            TargetFly(target, player, false);
                            break;
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                            player.closeInventory();
                        }
                    } else {
                        if (player.hasPermission("fly.others")) {
                            if (timer.containsKey(player.getName())) {
                                target.setAllowFlight(false);
                                target.setFlying(false);
                                target.setInvulnerable(false);
                                time.put(target.getName(), 1);
                                timer.put(target.getName(), System.currentTimeMillis());
                                Boolean Chat = NotifyChat.get(target);
                                Boolean BossBar = NotifyBossBar.get(target);
                                Boolean Title = NotifyTitle.get(target);
                                Boolean Sounds = NotifySounds.get(target);

                                GroundHitters.add(target);


                                NotifyChat.put(target, false);
                                NotifyBossBar.put(target, false);
                                NotifyTitle.put(target, false);
                                NotifySounds.put(target, false);

                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    NotifyChat.put(target, Chat);
                                    NotifyBossBar.put(target, BossBar);
                                    NotifyTitle.put(target, Title);
                                    NotifySounds.put(target, Sounds);
                                }, 50L);
                            }
                            player.closeInventory();
                            TargetFly(target, player, false);
                            break;
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);

                            player.closeInventory();
                        }
                    }
                    break;

                case LIME_WOOL:
                    if (target == player) {
                        if (player.hasPermission("fly.fly")) {
                            player.closeInventory();
                            TargetFly(target, player, true);
                            break;
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                            player.closeInventory();
                        }
                    } else {
                        if (player.hasPermission("fly.others")) {
                            player.closeInventory();
                            TargetFly(target, player, true);
                            break;
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                            player.closeInventory();
                        }
                    }
                    break;

                case CLOCK:
                    if (target.getName().equalsIgnoreCase(player.getName())) {
                        if (player.hasPermission("fly.tempfly")) {
                            player.closeInventory();
                            TempflyMethod(player, target);
                            break;
                        } else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                            player.closeInventory();
                            break;
                        }
                    } else {
                        if (player.hasPermission("fly.tempflyothers")) {
                            player.closeInventory();
                            TempflyMethod(player, target);
                            break;
                        }  else {
                            player.sendMessage(Prefix + " " + ChatColor.RED + NoPermission);
                            player.closeInventory();
                            break;
                        }
                    }

            }

            e.setCancelled(true);
        } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "        === Tempfly GUI ===")) {
            String name = e.getView().getItem(4).getItemMeta().getDisplayName();
            name = ChatColor.stripColor(name);
            Player targettf = player.getServer().getPlayer(name);
            if (e.getCurrentItem().getType().equals(Material.DAMAGED_ANVIL)) {
                int HoursI;
                if (e.getClick().isLeftClick()) {
                    if (e.getClick().isShiftClick()) {
                        HoursI = 23;
                        e.getCurrentItem().setAmount(HoursI);
                        Hours.remove(player);
                        Hours.put(player, HoursI);
                    } else {
                        if (Hours.get(player) > 22) {
                            HoursI = 22;
                            Hours.remove(player);
                            Hours.put(player, HoursI);
                        }
                        HoursI = Hours.get(player);
                        HoursI++;
                        e.getCurrentItem().setAmount(HoursI);
                        Hours.remove(player);
                        Hours.put(player, HoursI);
                    }
                } else if (e.getClick().isRightClick()) {
                    if (e.getClick().isShiftClick()) {
                        HoursI = 0;
                        e.getCurrentItem().setAmount(1);
                        Hours.remove(player);
                        Hours.put(player, HoursI);
                    } else {
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
                }
                ItemMeta meta = e.getCurrentItem().getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + String.valueOf(Hours.get(player)) + " hours");
                e.getCurrentItem().setItemMeta(meta);
            } else if (e.getCurrentItem().getType().equals(Material.CHIPPED_ANVIL)) {
                int MinuteI;
                if (e.getClick().isLeftClick()) {
                    if (e.getClick().isShiftClick()) {
                        MinuteI = 59;
                        e.getCurrentItem().setAmount(MinuteI);
                        Minutes.remove(player);
                        Minutes.put(player, MinuteI);
                    } else {
                        if (Minutes.get(player) > 58) {
                            MinuteI = 58;
                            Minutes.remove(player);
                            Minutes.put(player, MinuteI);
                        }
                        MinuteI = Minutes.get(player);
                        MinuteI++;
                        e.getCurrentItem().setAmount(MinuteI);
                        Minutes.remove(player);
                        Minutes.put(player, MinuteI);
                    }
                    } else if (e.getClick().isRightClick()) {
                        if (e.getClick().isShiftClick()) {
                            MinuteI = 0;
                            e.getCurrentItem().setAmount(1);
                            Minutes.remove(player);
                            Minutes.put(player, MinuteI);
                        } else {
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
                }
                ItemMeta meta = e.getCurrentItem().getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + String.valueOf(Minutes.get(player)) + " minutes");
                e.getCurrentItem().setItemMeta(meta);
            } else if (e.getCurrentItem().getType().equals(Material.ANVIL)) {
                int SecondesI;
                if (e.getClick().isLeftClick()) {
                    if (e.getClick().isShiftClick()) {
                        SecondesI = 59;
                        e.getCurrentItem().setAmount(SecondesI);
                        Secondes.remove(player);
                        Secondes.put(player, SecondesI);
                    } else {
                        if (Secondes.get(player) > 58) {
                            SecondesI = 58;
                            Secondes.remove(player);
                            Secondes.put(player, SecondesI);
                        }
                        SecondesI = Secondes.get(player);
                        SecondesI++;
                        e.getCurrentItem().setAmount(SecondesI);
                        Secondes.remove(player);
                        Secondes.put(player, SecondesI);
                    }
                } else if (e.getClick().isRightClick()) {
                            if (e.getClick().isShiftClick()) {
                                SecondesI = 0;
                                e.getCurrentItem().setAmount(1);
                                Secondes.remove(player);
                                Secondes.put(player, SecondesI);
                            } else {
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
                }
                ItemMeta meta = e.getCurrentItem().getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + String.valueOf(Secondes.get(player)) + " seconds");
                e.getCurrentItem().setItemMeta(meta);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Continue")) {
                int TimeLeft = ((Hours.get(player) * 3600) + (Minutes.get(player) * 60) + (Secondes.get(player)));
                if (!(TimeLeft == 0)) {
                    flyers.remove(targettf.getName());
                    targettf.setAllowFlight(true);
                    flyers.add(targettf.getName());
                    Tempfly.time.put(targettf.getName(), TimeLeft);
                    Tempfly.timer.put(targettf.getName(), System.currentTimeMillis());
                    timem.put(targettf, 100000);
                    String MessageP = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tempfly-player"));
                    String MessageTP = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tempfly-target"));
                    int heures = (TimeLeft / 3600);
                    int minutes = ((TimeLeft - (TimeLeft / 3600) * 3600) / 60);
                    int seconds = TimeLeft - (heures * 3600 + minutes * 60);
                    String TimeleftP = MessageP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                    player.sendMessage(Prefix + " " + TimeleftP);
                    if (TargetMessage) {
                        String TimeleftTP = MessageTP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds)).replace("%player%", player.getName());
                        targettf.sendMessage(Prefix + " " + TimeleftTP);
                    }
                } else {
                    String WrongArgs = plugin.getConfig().getString("wrong-args");
                    player.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                }
                player.closeInventory();
            }
            e.setCancelled(true);
            } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "         === Player list ===")) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                name = ChatColor.stripColor(name);
                Player target = player.getServer().getPlayer(name);
                Inventory flygui = Bukkit.createInventory(player, 36, ChatColor.GOLD + "           === Fly GUI ===");

                ItemStack on = new ItemStack(Material.LIME_WOOL);
                ItemStack off = new ItemStack(Material.RED_WOOL);
                ItemStack tempfly = new ItemStack(Material.GOLD_BLOCK);

                ItemMeta metaOn = on.getItemMeta();
                metaOn.setDisplayName(ChatColor.GREEN + "ON");
                ArrayList<String> loreOn = new ArrayList<>();
                loreOn.add("Activate fly for " + target.getName());
                metaOn.setLore(loreOn);
                on.setItemMeta(metaOn);

                ItemMeta metaOff = off.getItemMeta();
                metaOff.setDisplayName(ChatColor.DARK_RED + "OFF");
                ArrayList<String> loreOff = new ArrayList<>();
                loreOff.add("Disable fly for " + target.getName());
                metaOff.setLore(loreOff);
                off.setItemMeta(metaOff);

                ItemMeta meta = tempfly.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + "Tempfly");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("Set temp-fly for " + target.getName());
                meta.setLore(lore);
                tempfly.setItemMeta(meta);


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


                flygui.setItem(20, on);
                flygui.setItem(24, off);
                flygui.setItem(31, tempfly);
                flygui.setItem(4, playerhead);


                player.openInventory(flygui);

                e.setCancelled(true);
            }
        }

    public void TargetFly(Player target, Player player, boolean state) {
        String On = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("on-state")) + ChatColor.RESET;
        String Off = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("off-state")) + ChatColor.RESET;
        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        Boolean TargetSendMessage = plugin.getConfig().getBoolean("fly-target-message");
        String FlySomeoneTarget = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-target")) + ChatColor.RESET;
        String FlySomeonePlayer = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fly-someone-player")) + ChatColor.RESET;
        if (state) {
            FlyMethod(target, true);
            String FSPr = FlySomeonePlayer.replace("%state%", On).replace("%target%", target.getName()).replace("%player%", player.getName());
            player.sendMessage(Prefix + " " + FSPr);
            if (TargetSendMessage) {
                String FSTr = FlySomeoneTarget.replace("%state%", On).replace("%target%", target.getName()).replace("%player%", player.getName());
                target.sendMessage(Prefix + " " + FSTr);
            }
        } else {
            FlyMethod(target, false);
            String FSPr = FlySomeonePlayer.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", player.getName());
            player.sendMessage(Prefix + " " + FSPr);
            if (TargetSendMessage) {
                String FSTr = FlySomeoneTarget.replace("%state%", Off).replace("%target%", target.getName()).replace("%player%", player.getName());
                target.sendMessage(Prefix + " " + FSTr);
            }
        }
    }

    public void TempflyMethod(Player player, Player target) {

        if (player.hasPermission("fly.gui")) {
            Inventory tempflygui = Bukkit.createInventory(player, 36, ChatColor.GOLD + "        === Tempfly GUI ===");

            ItemStack secondes = new ItemStack(Material.ANVIL);
            ItemStack minute = new ItemStack(Material.CHIPPED_ANVIL);
            ItemStack heures = new ItemStack(Material.DAMAGED_ANVIL);


            ItemStack valider = new ItemStack(Material.ARROW);


            ArrayList<String> loreT = new ArrayList<>();
            loreT.add(ChatColor.BLUE + "- Left click to add");
            loreT.add(ChatColor.BLUE + "- Right click to remove");

            ItemMeta validerItemMeta = valider.getItemMeta();

            validerItemMeta.setDisplayName(ChatColor.GREEN + "Continue");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "---->");
            validerItemMeta.setLore(lore);
            valider.setItemMeta(validerItemMeta);


            ItemMeta smeta = secondes.getItemMeta();
            smeta.setDisplayName(ChatColor.GOLD + "0 seconds");
            smeta.setLore(loreT);
            secondes.setItemMeta(smeta);

            ItemMeta mmeta = minute.getItemMeta();
            mmeta.setDisplayName(ChatColor.GOLD + "0 minutes");
            mmeta.setLore(loreT);
            minute.setItemMeta(mmeta);

            ItemMeta hmeta = heures.getItemMeta();
            hmeta.setDisplayName(ChatColor.GOLD + "0 hours");
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

            player.openInventory(tempflygui);
        }
    }

}
