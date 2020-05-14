package fr.radi3nt.fly.commands;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.utilis.UpdateCheck;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FlyUpdate implements CommandExecutor {

    Plugin plugin = MainFly.getPlugin(MainFly.class);
    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("fly.update")) {
            UpdateCheck updater = new UpdateCheck();
            updater.run();
            if (!UpdateCheck.upToDate) {
                TextComponent tc = new TextComponent();
                tc.setText(ChatColor.BLUE + " → spigot link");
                tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/fly-plugin-tempfly-gui.77618"));
                tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("➤ Click here to get the link").create()));


                if (UpdateCheck.PreRelease.equals("true")) {
                    if (UpdateCheck.MajorVersion) {
                        sender.sendMessage(Prefix + " Update found: " + UpdateCheck.latest + ", currently on version " + MainFly.VERSION + ".\n This is a MAJOR pre-release version! The update can be found here :");
                    } else {
                        sender.sendMessage(Prefix + " Update found: " + UpdateCheck.latest + ", currently on version " + MainFly.VERSION + ".\n This is a minor pre-release version! The update can be found here :");
                    }
                } else {
                    if (UpdateCheck.MajorVersion) {
                        sender.sendMessage(Prefix + " Update found: " + UpdateCheck.latest + ", currently on version " + MainFly.VERSION + ".\n This is a MAJOR version! The update can be found here :");
                    } else {
                        sender.sendMessage(Prefix + " Update found: " + UpdateCheck.latest + ", currently on version " + MainFly.VERSION + ".\n This is a minor version! The update can be found here :");
                    }
                }

                if (sender instanceof Player) {
                    sender.spigot().sendMessage(tc);
                } else {
                    sender.sendMessage("https://www.spigotmc.org/resources/fly-plugin-tempfly-gui.77618");
                }
            } else {
                sender.sendMessage(Prefix + ChatColor.RED + " No update available");
            }
        }

        return true;
    }
}
