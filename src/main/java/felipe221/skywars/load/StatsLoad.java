package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.object.cosmetics.Cage;
import felipe221.skywars.object.cosmetics.Effect;
import felipe221.skywars.object.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsLoad {
    public static void load(Player player){
        User user = User.getUser(player);
        checkExist(player);

        ResultSet stb = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`player_stats` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
        try {
            if (stb.next()) {
                user.setCoins(stb.getInt("coins"));
                user.setLevel(stb.getInt("level"));
                user.setTrail(Effect.Trail.valueOf(stb.getString("trail")));
                user.setWinEffect(Effect.WinEffect.valueOf(stb.getString("win_effect")));
                user.setKillEffect(Effect.KillEffect.valueOf(stb.getString("kill_effect")));
                user.setKillTematica(stb.getString("tematica"));
                user.setXP(stb.getInt("xp"));
                Material material = Material.valueOf(stb.getString("cage_material"));
                Cage.TypeCage type = Cage.TypeCage.valueOf(stb.getString("cage_type"));

                if (CageLoad.exist(material)){
                    user.setCage(new Cage(material, type, null));
                }else{
                    user.setCage(new Cage(Material.GLASS, type, null));
                }

                if (stb.getString("kit").equalsIgnoreCase("NONE")){
                    user.setKit(null);
                }else{
                    user.setKit(KitLoad.getKitPerNameConfig(stb.getString("kit")));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void send(Player player){
        User user = User.getUser(player);

        Main.getDatabaseManager().query("UPDATE `minecraft`.`player_stats` SET " +
                "`trail`='" + user.getTrail().name() + "', " +
                "`coins`='" + user.getCoins() + "', " +
                "`level`='" + user.getLevel() + "', " +
                "`xp`='" + user.getXP() + "', " +
                "`win_effect`='" + user.getWinEffect().name() + "', " +
                "`tematica`='" + user.getKillTematica() + "', " +
                "`kill_effect`='" + user.getKillEffect().name() + "', " +
                "`cage_material`='" + user.getCage().getMaterialCage().name() + "', " +
                "`cage_type`='" + user.getCage().getType().name() + "', " +
                "`kit`='" + (user.getKit() == null ? "NONE" : user.getKit().getConfigName()) + "' " +
                "WHERE uuid = '" + player.getUniqueId() + "'");
    }

    public static void checkExist(Player player) {
        ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`player_stats` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
        try {
            if (!st.next()) {
                Main.getDatabaseManager().query("INSERT INTO `minecraft`.`player_stats` SET `uuid`='" + player.getUniqueId() + "';");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
