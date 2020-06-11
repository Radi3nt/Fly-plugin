package fr.radi3nt.fly.events;

import fr.radi3nt.fly.MainFly;
import fr.radi3nt.fly.commands.Fly;
import fr.radi3nt.fly.commands.Tempfly;
import fr.radi3nt.fly.timer.TempCheck;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static fr.radi3nt.fly.commands.Fly.FlyMethod;

public class OnDrinkFlyPotion implements Listener {

    @EventHandler
    public void OnDrinkFlyPotion(PlayerItemConsumeEvent e) {
        if (MainFly.getPlugin(MainFly.class).getConfig().getBoolean("fly-potions")) {

            if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Fly Potion ")) {
                int Tsec = 0;
                int Tmin = 0;
                int Th = 0;


                int secondesO = e.getItem().getItemMeta().getDisplayName().indexOf("sec");
                int secondesI = e.getItem().getItemMeta().getDisplayName().indexOf("min");

                if (secondesI == -1) {
                    secondesI = 13;
                }
                if (secondesI == 15) {
                    secondesI = 19;
                }
                if (secondesI == 16) {
                    secondesI = 20;
                }
                char[] secondsChar = new char[20];
                e.getItem().getItemMeta().getDisplayName().getChars(secondesI, secondesO, secondsChar, 0);
                StringBuilder sb = new StringBuilder();

                // Appends characters one by one
                for (Character ch : secondsChar) {
                    sb.append(ch);
                }

                // convert in string
                String secondsString = sb.toString();
                secondsString = secondsString.replace(" ", "");
                Tsec = Integer.parseInt(secondsString.trim());

                if (e.getItem().getItemMeta().getDisplayName().contains("min")) {
                    int minutesO = e.getItem().getItemMeta().getDisplayName().indexOf("min");
                    int minutesI = e.getItem().getItemMeta().getDisplayName().indexOf("h");

                    if (minutesI == -1) {
                        minutesI = 13;
                    }
                    if (minutesI == 11) {
                        minutesI = 13;
                    }
                    if (minutesI == 12) {
                        minutesI = 14;
                    }
                    char[] minutesChar = new char[20];
                    e.getItem().getItemMeta().getDisplayName().getChars(minutesI, minutesO, minutesChar, 0);
                    StringBuilder sb1 = new StringBuilder();

                    // Appends characters one by one
                    for (Character ch : minutesChar) {
                        sb1.append(ch);
                    }

                    // convert in string
                    String minutesString = sb1.toString();
                    minutesString = minutesString.replace(" ", "");
                    Tmin = Integer.parseInt(minutesString.trim());
                }
                if (e.getItem().getItemMeta().getDisplayName().contains("h")) {
                    int heuresO = e.getItem().getItemMeta().getDisplayName().indexOf("h");
                    int heuresI = e.getItem().getItemMeta().getDisplayName().indexOf("Plugin");

                    if (heuresI == -1) {
                        heuresI = 11;
                    }
                    if (heuresI == 10) {
                        heuresI = 13;
                    }
                    char[] heuressChar = new char[20];
                    e.getItem().getItemMeta().getDisplayName().getChars(heuresI, heuresO, heuressChar, 0);
                    StringBuilder sb1 = new StringBuilder();

                    // Appends characters one by one
                    for (Character ch : heuressChar) {
                        sb1.append(ch);
                    }

                    // convert in string
                    String heuresString = sb1.toString();
                    heuresString = heuresString.replace(" ", "");
                    Th = Integer.parseInt(heuresString.trim());
                }
                System.out.println(Th + ":" + Tmin + ":" + Tsec);


                Tmin = Tmin * 60;
                Th = Th * 3600;

                int all = Tsec + Tmin + Th;

                System.out.println(all);

                if (all != 0) {
                    Plugin plugin = MainFly.getPlugin(MainFly.class);

                    Map<String, Long> timer = Tempfly.timer;
                    Map<String, Integer> time = Tempfly.time;
                    ArrayList<String> flyers = Fly.flyers;


                    HashMap<Player, Integer> timem = TempCheck.timem;


                    Boolean Stackable = plugin.getConfig().getBoolean("stack-tempfly");


                    String MessageP = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tempfly-player"));


                    File locations = new File("plugins/FlyPlugin", "flyers.yml");
                    if (!locations.exists()) {
                        try {
                            locations.createNewFile();
                        } catch (IOException event) {
                            event.printStackTrace();
                        }
                    }
                    FileConfiguration loc = YamlConfiguration.loadConfiguration(locations);


                    Player player = e.getPlayer();
                    if (Stackable) {
                        if (time.containsKey(player.getName())) {
                            time.put(player.getName(), (int) (all + ((timer.get(player.getName()) / 1000) + time.get(player.getName())) - (System.currentTimeMillis() / 1000)));
                        } else {
                            time.put(player.getName(), all);
                        }
                    } else {
                        time.put(player.getName(), all);
                    }
                    flyers.remove(player.getName());
                    loc.set("flyers." + player.getName(), time.get(player.getName()));
                    FlyMethod(player, true);
                    timem.put(player, 100000);
                    timer.put(player.getName(), System.currentTimeMillis());
                    int timeleft = time.get(player.getName());
                    int heures = (timeleft / 3600);
                    int minutes = ((timeleft - (timeleft / 3600) * 3600) / 60);
                    int seconds = timeleft - (heures * 3600 + minutes * 60);
                    String TimeleftP = MessageP.replace("%hours%", String.valueOf(heures)).replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds));
                }

            }
        }
    }

}
