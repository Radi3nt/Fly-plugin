package fr.radi3nt.fly.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class FlyAlert implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> things = new ArrayList<>();
            things.add("chat");
            things.add("bossbar");
            things.add("title");
            things.add("sounds");
            things.add("all");
            if (sender.hasPermission("fly.admin")) {
                things.add("dust");
            }
            return things;
        }

        if (args.length == 2) {
            List<String> toggle = new ArrayList<>();
            toggle.add("on");
            toggle.add("off");
            return toggle;
        }

        return null;
    }
}
