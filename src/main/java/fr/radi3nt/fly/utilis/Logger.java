package fr.radi3nt.fly.utilis;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    FileConfiguration logger;
    File file;

    public Logger(FileConfiguration logger, File file) {
        this.logger = logger;
        this.file = file;
    }

    public void log(String msg) {
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logFly(Boolean state, Player player, Player target) {
        String msg = target.getName() + "(" + target.getUniqueId() + ") ";
        if (state) {
            msg = msg + "started flying by " + player.getName() + "(" + player.getUniqueId() + ") ";
        } else {
            msg = msg + "stopped flying by " + player.getName() + "(" + player.getUniqueId() + ") ";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logFly(Boolean state, CommandSender sender, Player target) {
        String msg = target.getName() + "(" + target.getUniqueId() + ") ";
        if (state) {
            msg = msg + "started flying by " + sender.getName() + "(" + "console" + ") ";
        } else {
            msg = msg + "stopped flying by " + sender.toString() + "(" + "console" + ") ";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logFly(Boolean state, Player player, Reason reason) {
        String msg = player.getName() + "(" + player.getUniqueId() + ") ";
        if (state) {
            msg = msg + "started flying (" + reason.toString() + ")";
        } else {
            msg = msg + "stopped flying (" + reason.toString() + ")";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logFly(Boolean state, Player player) {
        String msg = player.getName() + "(" + player.getUniqueId() + ") ";
        if (state) {
            msg = msg + "started flying";
        } else {
            msg = msg + "stopped flying";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logTempFly(Boolean state, Integer time, Player player) {
        String msg = player.getName() + "(" + player.getUniqueId() + ") ";
        int heures = (time / 3600);
        int minutes = ((time - (time / 3600) * 3600) / 60);
        int seconds = time - (heures * 3600 + minutes * 60);
        if (state) {
            msg = msg + "started tempflying: time : (" + heures + ":" + minutes + ":" + seconds + ")";
        } else {
            msg = msg + "stopped tempflying: time left: (" + heures + ":" + minutes + ":" + seconds + ")";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logTempFly(Boolean state, Integer time, Player player, Reason reason) {
        String msg = player.getName() + "(" + player.getUniqueId() + ") ";
        int heures = (time / 3600);
        int minutes = ((time - (time / 3600) * 3600) / 60);
        int seconds = time - (heures * 3600 + minutes * 60);
        if (state) {
            msg = msg + "started tempflying: time : (" + heures + ":" + minutes + ":" + seconds + ") (" + reason.toString() + ")";
        } else {
            msg = msg + "stopped tempflying: time left: (" + heures + ":" + minutes + ":" + seconds + ") (" + reason.toString() + ")";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logTempFly(Boolean state, Integer time, Player player, Player target) {
        String msg = target.getName() + "(" + target.getUniqueId() + ") ";
        int heures = (time / 3600);
        int minutes = ((time - (time / 3600) * 3600) / 60);
        int seconds = time - (heures * 3600 + minutes * 60);
        if (state) {
            msg = msg + "started tempflying: time : (" + heures + ":" + minutes + ":" + seconds + ") by " + player.getName() + "(" + player.getUniqueId() + ") ";
        } else {
            msg = msg + "stopped tempflying: time left: (" + heures + ":" + minutes + ":" + seconds + ") by " + player.getName() + "(" + player.getUniqueId() + ") ";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logTempFly(Boolean state, Integer time, Player player, Player target, Reason reason) {
        String msg = target.getName() + "(" + target.getUniqueId() + ") ";
        int heures = (time / 3600);
        int minutes = ((time - (time / 3600) * 3600) / 60);
        int seconds = time - (heures * 3600 + minutes * 60);
        if (state) {
            msg = msg + "started tempflying: time : (" + heures + ":" + minutes + ":" + seconds + ") by " + player.getName() + "(" + player.getUniqueId() + ") (" + reason.toString() + ")";
        } else {
            msg = msg + "stopped tempflying: time left: (" + heures + ":" + minutes + ":" + seconds + ") by " + player.getName() + "(" + player.getUniqueId() + ") (" + reason.toString() + ")";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    public void logTempFly(Boolean state, Integer time, CommandSender sender, Player target) {
        String msg = target.getName() + "(" + target.getUniqueId() + ") ";
        int heures = (time / 3600);
        int minutes = ((time - (time / 3600) * 3600) / 60);
        int seconds = time - (heures * 3600 + minutes * 60);
        if (state) {
            msg = msg + "started tempflying: time : (" + heures + ":" + minutes + ":" + seconds + ") by " + sender.getName() + "(" + "console" + ") ";
        } else {
            msg = msg + "stopped tempflying: time left: (" + heures + ":" + minutes + ":" + seconds + ") by " + sender.getName() + "(" + "console" + ") ";
        }
        logger.set(getFullTime(), msg);
        saveConfig();
    }

    private String getFullTime() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        long milis = System.currentTimeMillis();
        String time = year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second + " | " + milis;
        return time;
    }

    private void saveConfig() {
        try {
            logger.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
