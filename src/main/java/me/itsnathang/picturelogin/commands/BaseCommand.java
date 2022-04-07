package me.itsnathang.picturelogin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.itsnathang.picturelogin.PictureLogin;

import static me.itsnathang.picturelogin.util.Translate.tl;

public class BaseCommand implements CommandExecutor {
    private final PictureLogin plugin;

    public BaseCommand(PictureLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.hasPermission("picturelogin.main")) {
            s.sendMessage(tl("no_permission"));
            return false;
        }

        // Check if command is /picturelogin reload
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.getConfigManager().reloadConfig();
            s.sendMessage(tl("reload_config"));
            return true;
        }

        // Default to sending help
        s.sendMessage(ChatColor.GREEN + "PictureLogin " + ChatColor.GRAY + "v" +
                ChatColor.GREEN + plugin.getDescription().getVersion() + ChatColor.GRAY + " by " +
                ChatColor.GREEN + "NathanG");
        s.sendMessage(tl("reload_config_help"));
        return true;
    }
}
