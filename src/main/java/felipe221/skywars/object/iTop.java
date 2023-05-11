package felipe221.skywars.object;

import felipe221.skywars.Main;
import felipe221.skywars.load.SignLoad;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class iTop {
    private ArrayList<StatTop> tops = new ArrayList<>();
    private static iTop iTop = new iTop();
    private Long lastUpdate;

    public static iTop getInstance(){
        return iTop;
    }

    public class TopFormat{
        private UUID uuid;
        private int value;

        public TopFormat(UUID uuid,int value) {
            this.uuid = uuid;
            this.value = value;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }


        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public class StatTop {
        private iStats.TypeStats typeStats;
        private iStats.TimeStats timeStats;
        private iStats.Stats stats;

        //position - values
        private HashMap<Integer, TopFormat> values;

        public StatTop(iStats.TypeStats typeStats, iStats.TimeStats timeStats, iStats.Stats stats) {
            this.typeStats = typeStats;
            this.timeStats = timeStats;
            this.stats = stats;
            this.values = new HashMap<>();

            tops.add(this);
        }

        public iStats.TypeStats getTypeStats() {
            return typeStats;
        }

        public void setTypeStats(iStats.TypeStats typeStats) {
            this.typeStats = typeStats;
        }

        public iStats.TimeStats getTimeStats() {
            return timeStats;
        }

        public void setTimeStats(iStats.TimeStats timeStats) {
            this.timeStats = timeStats;
        }

        public iStats.Stats getStats() {
            return stats;
        }

        public void setStats(iStats.Stats stats) {
            this.stats = stats;
        }

        public HashMap<Integer, TopFormat> getValues() {
            return values;
        }

        public void setValues(HashMap<Integer, TopFormat> values) {
            this.values = values;
        }

        public void update(){
            this.values.clear();

            try {
                String tableName = "player_stats";
                tableName += "_" + typeStats.name().toLowerCase();
                if (timeStats != iStats.TimeStats.GLOBAL) {
                    tableName += "_" + timeStats.name().toLowerCase();
                }

                ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`" + tableName + "` ORDER BY " + stats.getMYSQLName() + " DESC LIMIT 10;").getResultSet();

                for (int a = 0; a<10;a++) {
                    if (st.next()) {
                        UUID uuid = UUID.fromString(st.getString("uuid"));
                        int value = st.getInt(stats.getMYSQLName());

                        values.put(a, new TopFormat(uuid, value));
                    }else{
                        values.put(a, null);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getFormattedLastUpdate(){
        double seconds = (System.currentTimeMillis() - this.lastUpdate) / 1000.0;
        int minutos = (int) (seconds / 60);
        return minutos + " minutos y " + ((int)seconds - (minutos*60)) + " segundos";
    }

    public void load(){
        this.lastUpdate = System.currentTimeMillis();

        tops.clear();

        for (iStats.TypeStats type : iStats.TypeStats.values()) {
            for (iStats.TimeStats time : iStats.TimeStats.values()) {
                for (iStats.Stats stats : iStats.Stats.values()) {
                    new StatTop(type, time, stats);
                }
            }
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                for (StatTop top : tops){
                    top.update();
                }
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 72000);
    }

    public TopFormat getTopPosition(iStats.Stats stat, iStats.TypeStats type, iStats.TimeStats time, int position){
        for (StatTop tops : tops) {
            if (tops.getStats() == stat && tops.getTypeStats() == type && tops.getTimeStats() == time){
                return tops.getValues().get(position);
            }
        }

        return null;
    }

    public StatTop getStatTop(iStats.Stats stat, iStats.TypeStats type, iStats.TimeStats time){
        for (StatTop tops : tops) {
            if (tops.getStats() == stat && tops.getTypeStats() == type && tops.getTimeStats() == time){
                return tops;
            }
        }

        return null;
    }
}
