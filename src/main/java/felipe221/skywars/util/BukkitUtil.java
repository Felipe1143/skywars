package felipe221.skywars.util;

import felipe221.skywars.Main;
import felipe221.skywars.controller.LevelController;
import felipe221.skywars.load.CageLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public static List<String> replaceVariableInList(List<String> array,String replace, String to){
        List<String> newList = new ArrayList<>();

        for (String line : array){
            line = line.replaceAll(replace, to);
            newList.add(line);
        }

        return newList;
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
                    .replaceAll("%stats_next_level_xp%", "" + LevelController.levels.get(user.getLevel() + 1))
                    .replaceAll("%stats_next_level%", "" + user.getLevel() + 1)
                    .replaceAll("%stats_coins%", "" + user.getCoins())
                    .replaceAll("%stats_kit_active%", (user.getKit() == null ? "Ninguno" : user.getKit().getName()))
                    .replaceAll("%stats_solo_kills%", "" + user.getSoloKills())
                    .replaceAll("%stats_solo_losses%", "" + user.getSoloLosses())
                    .replaceAll("%stats_solo_wins%", "" + user.getSoloWins())
                    .replaceAll("%stats_solo_games%", "" + user.getSoloGames())
                    .replaceAll("%stats_solo_block_placed%", "" + user.getSoloBlockPlaced())
                    .replaceAll("%stats_solo_block_broken%", "" + user.getSoloBlockBreak())
                    .replaceAll("%stats_solo_arrow_hit%", "" + user.getSoloArrowHit())
                    .replaceAll("%stats_team_losses%", "" + user.getTeamLosses())
                    .replaceAll("%stats_team_kills%", "" + user.getTeamKills())
                    .replaceAll("%stats_team_wins%", "" + user.getTeamWins())
                    .replaceAll("%stats_team_games%", "" + user.getTeamGames())
                    .replaceAll("%stats_team_block_placed%", "" + user.getTeamBlockPlaced())
                    .replaceAll("%stats_team_block_broken%", "" + user.getTeamBlockBreak())
                    .replaceAll("%stats_team_arrow_hit%", "" + user.getTeamArrowHit())
                    .replaceAll("%stats_trail%", "" + user.getTrail().getName())
                    .replaceAll("%stats_kill_effect%", "" + user.getKillEffect().getName())
                    .replaceAll("%stats_kill_tematica%", user.getKillTematicaName())
                    .replaceAll("%stats_cage_material%", CageLoad.getNameByMaterial(user.getCage().getMaterialCage()))
                    .replaceAll("%stats_cage_type%", user.getCage().getType().getName())
                    .replaceAll("%stats_win_effect%", "" + user.getWinEffect().getName());
        }

        if (arena != null){
            msg=msg.replaceAll("%arena_name%", arena.getName())
                    .replaceAll("%arena_world%", arena.getWorld().getName())
                    .replaceAll("%arena_mode%", arena.getMode().getName())
                    .replaceAll("%arena_players%", "" + arena.getPlayersAlive().size())
                    .replaceAll("%arena_spectators%", "" + arena.getSpectators().size())
                    .replaceAll("%arena_min%", "" + arena.getMin())
                    .replaceAll("%arena_max%", "" + arena.getMax())
                    .replaceAll("%arena_seconds%", "" + (arena.getTime() < 0 ? -arena.getTime() : arena.getTime()))
                    .replaceAll("%arena_chest%", arena.getChest().getName())
                    .replaceAll("%arena_vote_time%", "" + arena.getTimeGame().getName())
                    .replaceAll("%arena_vote_projectile%", arena.getProjectiles().getName())
                    .replaceAll("%arena_vote_scenario%", "" + arena.getScenario().getName())
                    .replaceAll("%arena_vote_hearts%", "" + arena.getHearts().getName())
                    .replaceAll("%arena_scenario%", "" + arena.getScenario().getName())
                    .replaceAll("%arena_status%", "" + arena.getStatus().getName());
        }
        String pattern = "dd/MM/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        msg = msg.replace("%date%", date);

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

                line = line.replaceAll("%player%", player.getName())
                        .replaceAll("%stats_level%", "" + user.getLevel())
                        .replaceAll("%stats_xp%", "" + user.getXP())
                        .replaceAll("%stats_next_level_xp%", "" + LevelController.levels.get(user.getLevel() + 1))
                        .replaceAll("%stats_next_level%", "" + user.getLevel() + 1)
                        .replaceAll("%stats_coins%", "" + user.getCoins())
                        .replaceAll("%stats_kit_active%", (user.getKit() == null ? "Ninguno" : user.getKit().getName()))
                        .replaceAll("%stats_solo_kills%", "" + user.getSoloKills())
                        .replaceAll("%stats_solo_losses%", "" + user.getSoloLosses())
                        .replaceAll("%stats_solo_wins%", "" + user.getSoloWins())
                        .replaceAll("%stats_solo_games%", "" + user.getSoloGames())
                        .replaceAll("%stats_solo_block_placed%", "" + user.getSoloBlockPlaced())
                        .replaceAll("%stats_solo_block_broken%", "" + user.getSoloBlockBreak())
                        .replaceAll("%stats_solo_arrow_hit%", "" + user.getSoloArrowHit())
                        .replaceAll("%stats_team_losses%", "" + user.getTeamLosses())
                        .replaceAll("%stats_team_kills%", "" + user.getTeamKills())
                        .replaceAll("%stats_team_wins%", "" + user.getTeamWins())
                        .replaceAll("%stats_team_games%", "" + user.getTeamGames())
                        .replaceAll("%stats_team_block_placed%", "" + user.getTeamBlockPlaced())
                        .replaceAll("%stats_team_block_broken%", "" + user.getTeamBlockBreak())
                        .replaceAll("%stats_team_arrow_hit%", "" + user.getTeamArrowHit())
                        .replaceAll("%stats_trail%", "" + user.getTrail().getName())
                        .replaceAll("%stats_kill_effect%", "" + user.getKillEffect().getName())
                        .replaceAll("%stats_kill_tematica%", user.getKillTematicaName())
                        .replaceAll("%stats_cage_material%", CageLoad.getNameByMaterial(user.getCage().getMaterialCage()))
                        .replaceAll("%stats_cage_type%", user.getCage().getType().getName())
                        .replaceAll("%stats_win_effect%", "" + user.getWinEffect().getName());

            }

            if (arena != null) {
                line=line.replaceAll("%arena_name%", arena.getName())
                        .replaceAll("%arena_world%", arena.getWorld().getName())
                        .replaceAll("%arena_mode%", arena.getMode().getName())
                        .replaceAll("%arena_players%", "" + arena.getPlayersAlive().size())
                        .replaceAll("%arena_spectators%", "" + arena.getSpectators().size())
                        .replaceAll("%arena_min%", "" + arena.getMin())
                        .replaceAll("%arena_max%", "" + arena.getMax())
                        .replaceAll("%arena_seconds%", "" + (arena.getTime() < 0 ? -arena.getTime() : arena.getTime()))
                        .replaceAll("%arena_chest%", arena.getChest().getName())
                        .replaceAll("%arena_vote_time%", "" + arena.getTimeGame().getName())
                        .replaceAll("%arena_vote_projectile%", arena.getProjectiles().getName())
                        .replaceAll("%arena_vote_scenario%", "" + arena.getScenario().getName())
                        .replaceAll("%arena_vote_hearts%", "" + arena.getHearts().getName())
                        .replaceAll("%arena_scenario%", "" + arena.getScenario().getName())
                        .replaceAll("%arena_status%", "" + arena.getStatus().getName());
            }
            String pattern = "dd/MM/yy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            line = line.replace("%date%", date);

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