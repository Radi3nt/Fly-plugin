package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class FlyGui implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String InvalidPlayer = plugin.getConfig().getString("invalid-player");
        String NoPermission = plugin.getConfig().getString("no-permission");
        String NoArgs = plugin.getConfig().getString("no-args");


        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);



        if (sender instanceof Player) {
            if (args.length > 0) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("fly.gui")) {
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
                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
                    }
                } else {
                    sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                }
            } else {
                Player player = (Player) sender;

                List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                if (player.hasPermission("fly.gui")) {
                    if (list.size() < 54) {
                            Inventory playerlist = Bukkit.createInventory(player, 54, ChatColor.GOLD + "         === Player list ===");
                            for (int i = 0; i < list.size(); i++) {

                                Player target = list.get(i);
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

                                playerlist.addItem(playerhead);


                            }
                            player.openInventory(playerlist);
                        } else {
                            sender.sendMessage(Prefix + ChatColor.RED + " " + InvalidPlayer);
                        }
                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + " " + NoPermission);
                    }
                }
            } else {
            sender.sendMessage(Prefix + ChatColor.RED + " This command MUST be run by a player");
        }
        return true;
    }
}