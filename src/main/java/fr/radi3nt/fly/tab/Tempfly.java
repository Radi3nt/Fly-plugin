package fr.radi3nt.fly.tab;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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

        if (args.length == 2) {
            List<String> PlayersNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length ; i++) {
                PlayersNames.add(players[i].getName());
            }
            return PlayersNames;
        }
        return null;
    }
}
