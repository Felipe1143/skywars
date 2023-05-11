package felipe221.skywars.object;

import felipe221.skywars.Main;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class iStats {
    public enum Stats{
        KILLS("Asesinatos", "kills"),
        LOSSES("Derrotas","losses"),
        WINS("Victorias", "wins"),
        GAMES("Partidas", "games"),
        ARROWS("Golpes con arco", "arrow_hit"),
        BLOCK_PLACED("Bloques colocados","block_placed"),
        BLOCK_BROKEN("Bloques rotos", "block_broken");

        private String mysqlName;
        private String name;

        Stats(String name, String mysqlName) {
            this.name = name;
            this.mysqlName = mysqlName;
        }

        public String getMYSQLName() {
            return mysqlName;
        }

        public String getName() {
            return name;
        }
    }

    public enum TypeStats{
        SOLO("Solo"), TEAM("Team");

        private String name;

        TypeStats(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum TimeStats{
        GLOBAL("Global"), MONTH("Mensual"), WEEK("Semanal");

        private String name;

        TimeStats(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class StatFormat {
        private TypeStats typeStats;
        private TimeStats timeStats;
        private Stats stats;
        private int value;

        public StatFormat(Stats stats, TypeStats type, TimeStats timeStats, int value) {
            this.stats = stats;
            this.timeStats = timeStats;
            this.typeStats= type;
            this.value = value;
        }

        public Stats getStats() {
            return stats;
        }

        public TypeStats getType() {
            return typeStats;
        }

        public TimeStats getTimeStats() {
            return timeStats;
        }

        public int getValue() {
            return value;
        }

        public void addValue(int add){
            this.value += add;
        }

        public void setValue(int value){
            this.value = value;
        }
    }

    private Player player;
    private ArrayList<StatFormat> statFormat;

    public iStats(Player player){
        this.player = player;
        this.statFormat = new ArrayList<>();

        checkExist();

        for (TypeStats type : TypeStats.values()) {
            for (TimeStats time : TimeStats.values()) {
                for (Stats stats : Stats.values()) {
                    String tableName = "player_stats";
                    tableName += "_" + type.name().toLowerCase();
                    if (time != TimeStats.GLOBAL){
                        tableName += "_" + time.name().toLowerCase();
                    }

                    ResultSet rsStats = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`" + tableName + "` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
                    try {
                        if (rsStats.next()) {
                            int value = rsStats.getInt(stats.getMYSQLName());
                            statFormat.add(new StatFormat(stats, type, time, value));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void checkExist(){
        for (TypeStats type : TypeStats.values()) {
            for (TimeStats time : TimeStats.values()) {
                String tableName = "player_stats";
                tableName += "_" + type.name().toLowerCase();
                if (time != TimeStats.GLOBAL) {
                    tableName += "_" + time.name().toLowerCase();
                }

                ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`" + tableName +"` WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
                try {
                    if (!st.next()) {
                        Main.getDatabaseManager().query("INSERT INTO `minecraft`.`" + tableName + "` SET `uuid`='" + player.getUniqueId() + "';");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void send(){
        for (TypeStats type : TypeStats.values()) {
            for (TimeStats time : TimeStats.values()) {
                for (Stats stats : Stats.values()) {
                    String tableName = "player_stats";
                    StatFormat format = getStatFormat(stats, type, time);
                    int value = format.getValue();

                    tableName += "_" + type.name().toLowerCase();

                    if (time != TimeStats.GLOBAL) {
                        tableName += "_" + time.name().toLowerCase();
                    }

                    Main.getDatabaseManager().query("UPDATE `minecraft`.`" + tableName + "` " +
                            "SET `" + stats.getMYSQLName() + "`='" + value + "' " +
                            "WHERE uuid = '" + player.getUniqueId() + "';").getResultSet();
                }
            }
        }
    }

    public StatFormat getStatFormat(Stats stats, TypeStats type, TimeStats time){
        for (StatFormat format : statFormat){
            if (format.getType() == type && format.getTimeStats() == time && format.getStats() == stats){
                return format;
            }
        }

        return null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addStatValue(TypeStats type, Stats stat, int value) {
        for (TimeStats time : TimeStats.values()) {
            StatFormat format = getStatFormat(stat, type, time);
            format.addValue(value);
        }
    }

    public void setStatValue(TypeStats type, Stats stat, TimeStats time, int value){
        StatFormat format = getStatFormat(stat, type, time);
        format.setValue(value);
    }
}
