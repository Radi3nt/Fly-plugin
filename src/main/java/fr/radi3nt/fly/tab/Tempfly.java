package fr.radi3nt.fly.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Tempfly implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            List<String> numbers = new ArrayList<>();
            numbers.add("60");
            numbers.add("180");
            numbers.add("300");
            numbers.add("600");
            numbers.add("1800");
            numbers.add("2700");
            numbers.add("3600");
            numbers.add("10800");
            numbers.add("21600");
            numbers.add("43200");
            numbers.add("86400");
            return numbers;
        }
        return null;
    }
}
