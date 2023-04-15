package felipe221.skywars.util;

import felipe221.skywars.Main;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


public class BukkitUtil {
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, subtitle);
    }

    public static void sendSound(Player player, String soundName, int i, int i1) {
        try {
            Sound sound = Sound.valueOf(soundName);
            player.playSound(player.getLocation(), sound, i, i1);
        } catch (Exception ignored) {}
    }

    public static ItemStack createItem(final Material material, final String displayName, final int amount, final short damage) {
        final ItemStack item = new ItemStack(material, amount, damage);
        final ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(displayName);
        item.setItemMeta(itemMeta);

        return item;
    }

    private static final Pattern STRIP_COLOR_PATTERN_TWO = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('&') + "[0-9A-FK-OR]");

    public static String stripcolor(String input) {
        String newInput = "";
        newInput = STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
        newInput = STRIP_COLOR_PATTERN_TWO.matcher(newInput).replaceAll("");

        return newInput;
    }
    public static ItemStack createItem(final Material material, final String displayName, final int amount) {
        return createItem(material, displayName, 1, (short) 0);
    }

    public static ItemStack createItem(final Material material, final String displayName) {
        return createItem(material, displayName, 1);
    }

    public static void runSync(Runnable runnable) {
        Plugin plugin = Main.getInstance();
        Server server = plugin.getServer();

        if (plugin.isEnabled() && !server.isPrimaryThread()) {
            Objects.requireNonNull(runnable);
            server.getScheduler().runTask(plugin, runnable);
        } else {
            runnable.run();
        }
    }


    public static String replaceVariables(Player player, Arena arena, String msg){
        if (msg == null){
            return "";
        }

        if (player != null){
            User user = User.getUser(player);

            msg = msg.replaceAll("%player%", player.getName())
                    .replaceAll("%stats_level%", "" + user.getLevel())
                    .replaceAll("%stats_xp%", "" + user.getXP())
                   // .replaceAll("%stats_kit_active%", "" + user.getKit().getName())
                    .replaceAll("%stats_kills%", "" + user.getKills())
                    .replaceAll("%stats_deaths%", "" + user.getLosses())
                    .replaceAll("%stats_elo%", "" + user.getRankedElo())
                    .replaceAll("%stats_kills%", "" + user.getKills());
                   // .replaceAll("%stats_trail%", "" + user.getTrail().getName())
                    //.replaceAll("%stats_kill_efect%", "" + user.getKillEffect().getName())
                    //.replaceAll("%stats_win_effect%", "" + user.getWinEffect())
        }

        if (arena != null){
            msg=msg.replaceAll("%arena%", arena.getName())
                    .replaceAll("%arena_world%", arena.getWorld().getName())
                    .replaceAll("%arena_mode%", arena.getMode().getName())
                    .replaceAll("%arena_players%", "" + arena.getPlayers().size())
                    .replaceAll("%arena_min%", "" + arena.getMin())
                    .replaceAll("%arena_max%", "" + arena.getMax())
                    .replaceAll("%arena_time%", "" + arena.getTime())
                    .replaceAll("%arena_chest%", arena.getChest().getName())
                    .replaceAll("%arena_projectiles%", arena.getProjectiles().getName())
                    .replaceAll("%arena_scenario%", "" + arena.getScenario())
                    .replaceAll("%arena_status%", "" + arena.getStatus().getName());
        }

        return ChatColor.translateAlternateColorCodes('&',msg);
    }

    public static List<String> replaceVariables(Player player, Arena arena, List<String> msg){
        List<String> newList = new ArrayList<>();

        for (String line : msg) {
            if (msg == null) {
                return null;
            }


            if (player != null) {
                User user = User.getUser(player);

                line.replaceAll("%player%", player.getName())
                                .replaceAll("%stats_level%", "" + user.getLevel())
                                .replaceAll("%stats_xp%", "" + user.getXP())
                                // .replaceAll("%stats_kit_active%", "" + user.getKit().getName())
                                .replaceAll("%stats_kills%", "" + user.getKills())
                                .replaceAll("%stats_deaths%", "" + user.getLosses())
                                .replaceAll("%stats_elo%", "" + user.getRankedElo())
                                .replaceAll("%stats_kills%", "" + user.getKills());
                        // .replaceAll("%stats_trail%", "" + user.getTrail().getName())
                        //.replaceAll("%stats_kill_efect%", "" + user.getKillEffect().getName())
                        //.replaceAll("%stats_win_effect%", "" + user.getWinEffect())

            }

            if (arena != null) {
                line.replaceAll("%arena%", arena.getName())
                        .replaceAll("%arena_world%", arena.getWorld().getName())
                        .replaceAll("%arena_mode%", arena.getMode().getName())
                        .replaceAll("%arena_players%", "" + arena.getPlayers().size())
                        .replaceAll("%arena_min%", "" + arena.getMin())
                        .replaceAll("%arena_max%", "" + arena.getMax())
                        .replaceAll("%arena_time%", "" + arena.getTime())
                        .replaceAll("%arena_chest%", arena.getChest().getName())
                        .replaceAll("%arena_projectiles%", arena.getProjectiles().getName())
                        .replaceAll("%arena_scenario%", "" + arena.getScenario())
                        .replaceAll("%arena_status%", "" + arena.getStatus().getName());
            }
            line = ChatColor.translateAlternateColorCodes('&', line);
            newList.add(line);
        }

        return newList;
    }

    public static Location parseLocation(World w, String in) {
        String[] params = in.split(",");
        for (String s : params) {
            s.replace("-0", "0");
        }
        if (params.length == 3 || params.length == 5) {
            double x = Double.parseDouble(params[0]);
            double y = Double.parseDouble(params[1]);
            double z = Double.parseDouble(params[2]);
            Location loc = new Location(w, x, y, z);
            if (params.length == 5) {
                loc.setYaw(Float.parseFloat(params[4]));
                loc.setPitch(Float.parseFloat(params[5]));
            }
            return loc;
        }
        return null;
    }

    public static void runAsync(Runnable runnable) {
        Plugin plugin = Main.getInstance();
        Server server = plugin.getServer();

        if (plugin.isEnabled() && server.isPrimaryThread()) {
            server.getScheduler().runTaskAsynchronously(plugin, runnable);
        } else {
            runnable.run();
        }
    }
}