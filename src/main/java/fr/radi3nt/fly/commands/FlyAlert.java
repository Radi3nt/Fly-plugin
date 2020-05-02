package fr.radi3nt.fly.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FlyAlert implements CommandExecutor {

    public static HashMap<Player, Boolean> NotifyChat = new HashMap<>();
    public static HashMap<Player, Boolean> NotifyTitle = new HashMap<>();
    public static HashMap<Player, Boolean> NotifyBossBar = new HashMap<>();



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 2) {
                switch (args[0]) {
                    case "chat":
                        if (args[1].equals("on")) {
                            NotifyChat.remove(player);
                            NotifyChat.put(player, true);
                        } else if (args[1].equals("off")){
                            NotifyChat.remove(player);
                            NotifyChat.put(player, false);
                        } else {
                            //TODO message erreur
                        }
                        break;

                    case "title":
                        if (args[1].equals("on")) {
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, true);
                        } else if (args[1].equals("off")){
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, false);
                        } else {
                            //TODO message erreur
                        }
                        break;

                    case "bossbar":
                        if (args[1].equals("on")) {
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, true);
                        } else if (args[1].equals("off")){
                            NotifyTitle.remove(player);
                            NotifyTitle.put(player, false);
                        } else {
                            //TODO message erreur
                        }
                        break;
                }
            }
        }
        return true;

    }
}
