package fr.radi3nt.fly.utilis;

import fr.radi3nt.fly.MainFly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateCheck {

    public static boolean upToDate = false;
    public static boolean minorVersion = true;
    public static String latest = "";
    Plugin plugin = MainFly.getPlugin(MainFly.class);
    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);


    ConsoleCommandSender console = Bukkit.getConsoleSender();

    public void run() {
        console.sendMessage(Prefix + " Checking for plugin updates...");
        InputStream in = null;
        try {
            in = new URL("https://raw.githubusercontent.com/Radi3nt/Fly-plugin/master/version.txt").openStream();
        } catch (MalformedURLException e) {
            console.sendMessage(Prefix + " Unable to check for updates!");
            e.printStackTrace();
        } catch (IOException e) {
            console.sendMessage(Prefix + " Unable to check for updates!");

            e.printStackTrace();
        }

        try {
            latest = IOUtils.readLines(in).get(0);
        } catch (IOException e) {
            console.sendMessage(Prefix + " Unable to determine update!");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }

        console.sendMessage(Prefix + " Latest version is " + latest);
        upToDate = MainFly.VERSION.equals(latest);
        if (!upToDate) {
            Integer lnumber = Integer.valueOf(latest.charAt(3));
            Integer cnumber = Integer.valueOf(MainFly.VERSION.charAt(3));
            if (lnumber > cnumber) {
                minorVersion = false;
            }
        }
        if (upToDate) {
            console.sendMessage(Prefix + " Plugin is the latest version!");
        } else {
            console.sendMessage(Prefix + " Plugin is out of date! Please update");
        }
    }

    public boolean upToDate() {
        return upToDate;
    }

    public String getLatestVersion() {
        return latest;
    }
}