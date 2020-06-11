package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FlyZone implements CommandExecutor {

    public static HashMap<Player, Location> OMark = new HashMap<>();
    public static HashMap<Player, Location> IMark = new HashMap<>();
    Plugin plugin = MainFly.getPlugin(MainFly.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);
        String NoArgs = plugin.getConfig().getString("no-args");
        String WrongArgs = plugin.getConfig().getString("wrong-args");
        String NoPermission = plugin.getConfig().getString("no-permission");
        String FlyzonesNoSelect = plugin.getConfig().getString("flyzones-noselect");
        String FlyzonesNameNoExist = plugin.getConfig().getString("flyzones-namenoexist");
        String FlyzonesNameAlreadyExist = plugin.getConfig().getString("flyzones-namealreadyexist");
        String FlyzonesPermReplace = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyzones-permreplace"));
        String FlyzonesPermReplaceNull = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyzones-permreplacenull"));

        String FlyZonesCreate = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyzones-create"));
        String FlyZonesName = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyzones-name"));
        String FlyZonesDelete = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("flyzones-delete"));


        File FlyingZone = new File("plugins/FlyPlugin", "zones.yml");
        if (!FlyingZone.exists()) {
            try {
                FlyingZone.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration FlyingZoneConfig = YamlConfiguration.loadConfiguration(FlyingZone);


        if (args.length > 0) {
            switch (args[0]) {

                case "wand":
                    if (sender.hasPermission("fly.zones.wand")) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            GiveWand(player);
                        }
                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + NoPermission);
                    }
                    break;

                case "list":
                    if (sender.hasPermission("fly.zones.list")) {

                        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "------------ Zones -----------");
                        try {
                            for (String zones : FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false)) {
                                String world = FlyingZoneConfig.getString("Zones." + zones + ".World");
                                int Ox = FlyingZoneConfig.getInt("Zones." + zones + ".Ox");
                                int Oy = FlyingZoneConfig.getInt("Zones." + zones + ".Oy");
                                int Oz = FlyingZoneConfig.getInt("Zones." + zones + ".Oz");

                                int Ix = FlyingZoneConfig.getInt("Zones." + zones + ".Ix");
                                int Iy = FlyingZoneConfig.getInt("Zones." + zones + ".Iy");
                                int Iz = FlyingZoneConfig.getInt("Zones." + zones + ".Iz");

                                String perm = FlyingZoneConfig.getString("Zones." + zones + ".Perm");

                                sender.sendMessage(ChatColor.BLUE + "- " + ChatColor.BOLD + zones + ":");
                                sender.sendMessage(ChatColor.GOLD + "  - World: " + ChatColor.BOLD + world);
                                sender.sendMessage(ChatColor.GOLD + "  - Location 1: " + ChatColor.BOLD + Ox + "x " + Oy + "y " + Oz + "z");
                                sender.sendMessage(ChatColor.GOLD + "  - Location 2: " + ChatColor.BOLD + Ix + "x " + Iy + "y " + Iz + "z");
                                sender.sendMessage(ChatColor.GOLD + "  - Permission: " + ChatColor.BOLD + perm);
                            }
                        } catch (NullPointerException e) {
                            //Exeption
                        }
                        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "-----------------------------");
                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + NoPermission);
                    }
                    break;

                //case "gui":
                //    OpenZoneGui();
                //    break;

                case "edit":
                    if (sender.hasPermission("fly.zones.edit")) {

                        if (args.length > 1) {
                            switch (args[1]) {

                                case "permission":
                                    if (sender.hasPermission("fly.zones.edit.permission")) {
                                        if (args.length > 2) {
                                            String perm = null;
                                            if (args.length > 3) {
                                                perm = args[3].replace("/", "").replace("'", "").replace("\"", "").replace("\\", "");
                                            }
                                            String name = args[2];
                                            try {

                                                if (FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false).isEmpty() || FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false).contains(name)) {
                                                    if (perm == null || perm.isEmpty()) {
                                                        FlyingZoneConfig.set("Zones." + name + ".Perm", null);
                                                        sender.sendMessage(Prefix + " " + FlyzonesPermReplaceNull);
                                                    } else {
                                                        FlyingZoneConfig.set("Zones." + name + ".Perm", perm);
                                                        FlyzonesPermReplace = FlyzonesPermReplace.replace("%perm%", perm);
                                                        sender.sendMessage(Prefix + " " + FlyzonesPermReplace);
                                                    }
                                                } else {
                                                    sender.sendMessage(Prefix + " " + ChatColor.RED + FlyzonesNameNoExist);
                                                }
                                            } catch (NullPointerException e) {
                                                sender.sendMessage(Prefix + " " + ChatColor.RED + FlyzonesNameNoExist);
                                            }
                                        } else {
                                            sender.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                                        }
                                    } else {
                                        sender.sendMessage(Prefix + ChatColor.RED + NoPermission);
                                    }
                                    break;

                                case "name":
                                    if (sender.hasPermission("fly.zones.edit.name")) {

                                        if (args.length > 3) {
                                            String name = args[2];
                                            String newname = args[3];
                                            try {
                                                if (FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false).contains(name)) {
                                                    if (FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false).contains(newname)) {


                                                        String world = FlyingZoneConfig.getString("Zones." + name + ".World");
                                                        int Ox = FlyingZoneConfig.getInt("Zones." + name + ".Ox");
                                                        int Oy = FlyingZoneConfig.getInt("Zones." + name + ".Oy");
                                                        int Oz = FlyingZoneConfig.getInt("Zones." + name + ".Oz");

                                                        int Ix = FlyingZoneConfig.getInt("Zones." + name + ".Ix");
                                                        int Iy = FlyingZoneConfig.getInt("Zones." + name + ".Iy");
                                                        int Iz = FlyingZoneConfig.getInt("Zones." + name + ".Iz");

                                                        String perm = FlyingZoneConfig.getString("Zones." + name + ".Perm");

                                                        FlyingZoneConfig.set("Zones." + name, null);

                                                        FlyingZoneConfig.set("Zones." + newname + ".World", world);
                                                        FlyingZoneConfig.set("Zones." + newname + ".Ox", Ox);
                                                        FlyingZoneConfig.set("Zones." + newname + ".Oy", Oy);
                                                        FlyingZoneConfig.set("Zones." + newname + ".Oz", Oz);
                                                        FlyingZoneConfig.set("Zones." + newname + ".Ix", Ix);
                                                        FlyingZoneConfig.set("Zones." + newname + ".Iy", Iy);
                                                        FlyingZoneConfig.set("Zones." + newname + ".Iz", Iz);
                                                        if (perm != null) {
                                                            FlyingZoneConfig.set("Zones." + newname + ".Perm", perm);
                                                        }

                                                        sender.sendMessage(Prefix + " " + FlyZonesName);
                                                    } else {
                                                        sender.sendMessage(Prefix + " " + ChatColor.RED + FlyzonesNameAlreadyExist);
                                                    }
                                                } else {
                                                    sender.sendMessage(Prefix + " " + ChatColor.RED + FlyzonesNameNoExist);
                                                }
                                            } catch (NullPointerException e) {
                                                sender.sendMessage(Prefix + " " + ChatColor.RED + FlyzonesNameNoExist);
                                            }
                                        } else {
                                            sender.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                                        }
                                    } else {
                                        sender.sendMessage(Prefix + ChatColor.RED + NoPermission);
                                    }
                                    break;

                                case "delete":
                                    if (sender.hasPermission("fly.zones.edit.delete")) {

                                        if (args.length > 2) {
                                            String name = args[2];
                                            try {
                                                if (FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false).contains(name)) {
                                                    FlyingZoneConfig.set("Zones." + name, null);
                                                    sender.sendMessage(Prefix + " " + FlyZonesDelete);
                                                } else {
                                                    sender.sendMessage(Prefix + " " + ChatColor.RED + FlyzonesNameNoExist);
                                                }
                                            } catch (NullPointerException e) {
                                                sender.sendMessage(Prefix + " " + ChatColor.RED + FlyzonesNameNoExist);
                                            }
                                        } else {
                                            sender.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                                        }
                                    } else {
                                        sender.sendMessage(Prefix + ChatColor.RED + NoPermission);
                                    }
                                    break;

                                case "location":
                                    if (sender.hasPermission("fly.zones.edit")) {

                                        if (sender instanceof Player) {
                                            Player player = (Player) sender;
                                            if (OMark.containsKey(player) && IMark.containsKey(player)) {
                                                if (args.length > 2) {
                                                    String name = args[2];
                                                    FlyingZoneConfig.set("Zones." + name + ".World", OMark.get(player).getWorld().getName());
                                                    FlyingZoneConfig.set("Zones." + name + ".Ox", OMark.get(player).getBlockX());
                                                    FlyingZoneConfig.set("Zones." + name + ".Oy", OMark.get(player).getBlockY());
                                                    FlyingZoneConfig.set("Zones." + name + ".Oz", OMark.get(player).getBlockZ());
                                                    FlyingZoneConfig.set("Zones." + name + ".Ix", IMark.get(player).getBlockX());
                                                    FlyingZoneConfig.set("Zones." + name + ".Iy", IMark.get(player).getBlockY());
                                                    FlyingZoneConfig.set("Zones." + name + ".Iz", IMark.get(player).getBlockZ());
                                                } else {
                                                    sender.sendMessage(Prefix + ChatColor.RED + WrongArgs);
                                                }
                                            } else {
                                                sender.sendMessage(Prefix + ChatColor.RED + FlyzonesNoSelect);
                                            }
                                        } else {
                                            sender.sendMessage(Prefix + ChatColor.RED + "This command must be run by a player");
                                        }
                                    } else {
                                        sender.sendMessage(Prefix + ChatColor.RED + NoPermission);
                                    }
                                    break;

                                default:
                                    sender.sendMessage(Prefix + ChatColor.RED + WrongArgs);
                                    break;
                            }
                        }
                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + NoPermission);
                    }
                    break;

                case "create":
                    if (sender.hasPermission("fly.zones.create")) {

                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            if (OMark.containsKey(player) && IMark.containsKey(player)) {
                                if (args.length > 1) {
                                    String name = args[1].replace(".", "").replace("/", "").replace("'", "").replace("\"", "").replace("\\", "");
                                    try {
                                        if (!FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false).contains(name)) {
                                            if (!name.isEmpty()) {
                                                FlyingZoneConfig.set("Zones." + name + ".World", OMark.get(player).getWorld().getName());
                                                FlyingZoneConfig.set("Zones." + name + ".Ox", OMark.get(player).getBlockX());
                                                FlyingZoneConfig.set("Zones." + name + ".Oy", OMark.get(player).getBlockY());
                                                FlyingZoneConfig.set("Zones." + name + ".Oz", OMark.get(player).getBlockZ());
                                                FlyingZoneConfig.set("Zones." + name + ".Ix", IMark.get(player).getBlockX());
                                                FlyingZoneConfig.set("Zones." + name + ".Iy", IMark.get(player).getBlockY());
                                                FlyingZoneConfig.set("Zones." + name + ".Iz", IMark.get(player).getBlockZ());
                                                if (args.length > 2) {
                                                    String perm = args[2].replace("/", "").replace("'", "").replace("\"", "").replace("\\", "");
                                                    if (!perm.isEmpty()) {
                                                        FlyingZoneConfig.set("Zones." + name + ".Perm", perm);
                                                    }
                                                }
                                                sender.sendMessage(Prefix + " " + FlyZonesCreate);
                                            } else {
                                                sender.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                                            }
                                        } else {
                                            sender.sendMessage(Prefix + " " + ChatColor.RED + FlyzonesNameAlreadyExist);
                                        }
                                    } catch (NullPointerException e) {
                                        if (!name.isEmpty()) {
                                            FlyingZoneConfig.set("Zones." + name + ".World", OMark.get(player).getWorld().getName());
                                            FlyingZoneConfig.set("Zones." + name + ".Ox", OMark.get(player).getBlockX());
                                            FlyingZoneConfig.set("Zones." + name + ".Oy", OMark.get(player).getBlockY());
                                            FlyingZoneConfig.set("Zones." + name + ".Oz", OMark.get(player).getBlockZ());
                                            FlyingZoneConfig.set("Zones." + name + ".Ix", IMark.get(player).getBlockX());
                                            FlyingZoneConfig.set("Zones." + name + ".Iy", IMark.get(player).getBlockY());
                                            FlyingZoneConfig.set("Zones." + name + ".Iz", IMark.get(player).getBlockZ());
                                            if (args.length > 2) {
                                                String perm = args[2].replace("/", "").replace("'", "").replace("\"", "").replace("\\", "");
                                                if (!perm.isEmpty()) {
                                                    FlyingZoneConfig.set("Zones." + name + ".Perm", perm);
                                                }
                                            }
                                            sender.sendMessage(Prefix + " " + FlyZonesCreate);
                                        } else {
                                            sender.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                                        }
                                    }

                                } else {
                                    sender.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                                }
                            } else {
                                sender.sendMessage(Prefix + " " + ChatColor.YELLOW + FlyzonesNoSelect);
                            }
                        } else {
                            sender.sendMessage(Prefix + " " + ChatColor.RED + "This command must be run by a player");
                        }
                    } else {
                        sender.sendMessage(Prefix + ChatColor.RED + NoPermission);
                    }
                    break;

                default:
                    sender.sendMessage(Prefix + " " + ChatColor.RED + WrongArgs);
                    break;
            }
        } else {
            sender.sendMessage(Prefix + " " + ChatColor.RED + NoArgs);
        }
        try {
            FlyingZoneConfig.save(FlyingZone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void GiveWand(Player player) {
        ItemStack wand = new ItemStack(Material.ARROW, 1);
        ItemMeta itemMeta = wand.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Fly Wand");
        ArrayList lore = new ArrayList();
        lore.add(ChatColor.GRAY + "Right to mark first position");
        lore.add(ChatColor.GRAY + "Left to mark second position");
        itemMeta.setLore(lore);
        wand.setItemMeta(itemMeta);
        player.getInventory().addItem(wand);
    }

    private void OpenZoneGui() {
    }
}
