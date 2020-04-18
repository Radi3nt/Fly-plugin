package fr.radi3nt.fly.Tab;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class FlySpeed implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            List<String> numbers = new ArrayList<>();
            numbers.add("1");
            numbers.add("2");
            numbers.add("3");
            numbers.add("4");
            numbers.add("5");
            numbers.add("6");
            numbers.add("7");
            numbers.add("8");
            numbers.add("9");
            return numbers;
        }
        return null;
    }
}
