package fr.radi3nt.fly.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlyZone implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        File FlyingZone = new File("plugins/FlyPlugin", "zones.yml");
        if (!FlyingZone.exists()) {
            try {
                FlyingZone.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration FlyingZoneConfig = YamlConfiguration.loadConfiguration(FlyingZone);


        List<String> things = new ArrayList<>();
        if (args.length == 1) {
            things.add("list");
            things.add("wand");
            things.add("edit");
            things.add("create");
            things.add("gui");
        }
        if (args.length == 2 && args[0].equals("edit")) {
            things.add("delete");
            things.add("location");
            things.add("name");
            things.add("permission");
        }
        if (args.length == 3 && args[0].equals("edit") && (args[1].equals("delete") || args[1].equals("location") || args[1].equals("name") || args[1].equals("permission"))) {
            things.addAll(FlyingZoneConfig.getConfigurationSection("Zones").getKeys(false));
        }
        return things;
    }
}
