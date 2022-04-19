package me.itsnathang.picturelogin.commands;

import me.itsnathang.picturelogin.PictureLogin;
import me.itsnathang.picturelogin.util.PictureUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.itsnathang.picturelogin.util.Translate.tl;

public class BaseCommand implements CommandExecutor, TabCompleter {
    private final PictureLogin plugin;
    private final PictureUtil util;

    public BaseCommand(PictureLogin plugin) {
        this.plugin = plugin;
        util = plugin.getPictureUtil();
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.hasPermission("picturelogin.main")) {
            s.sendMessage(tl("no_permission"));
            return true;
        }

        if (args.length < 1) {
            s.sendMessage(ChatColor.GREEN + "PictureLogin " + ChatColor.GRAY + "v" + ChatColor.GREEN
                    + plugin.getDescription().getVersion() + ChatColor.GRAY + " by " + ChatColor.GREEN + "_NickVo");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getConfigManager().reloadConfig();
            s.sendMessage(tl("reload_config"));
            return true;
        }

        if (!(s instanceof Player player)) {
            s.sendMessage(ChatColor.RED + "The test command can only be ran in-game.");
            return true;
        }

        // Test command allows admins to test the images they've configured
        if (args[0].equalsIgnoreCase("test")) {
            if (args.length < 2) {
                util.sendImage(player); // If they don't specify another arg, just show join image
                return true;
            }

            switch (args[1]) {
                case "leave" -> util.getLeaveMessage(player).sendToPlayer(player);
                case "join" -> util.sendImage(player);
                case "first-join" -> util.getFirstJoinMessage(player).sendToPlayer(player);
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("picturelogin.main")) {
            return List.of(); // Empty list to stop further tab completion
        }

        if (args.length == 1) {
            return getResults(args, List.of("reload", "test"));
        }

        // Sub-commands for the test command
        if (args.length == 2 && args[0].equalsIgnoreCase("test")) {
            return getResults(args, List.of("first-join", "join", "leave"));
        }

        return List.of(); // Empty list to stop further tab completion
    }

    private List<String> getResults(String[] args, List<String> toSearch) {
        List<String> results = new ArrayList<>();
        // This loop narrows down the tab complete results while typing
        for (String s : toSearch) {
            int index = args.length - 1;
            if (s.toLowerCase().startsWith(args[index].toLowerCase())) {
                results.add(s);
            }
        }
        return results;
    }

}
