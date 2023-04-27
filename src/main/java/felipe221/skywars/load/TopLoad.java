package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class TopLoad {
    public enum TypeTop{
        KILLS_SOLO("Asesinatos"),
        KILLS_TEAM("Asesinatos"),
        WINS_SOLO("Victorias"),
        WINS_TEAM("Victorias"),
        LEVEL("Nivel");

        private HashMap<Integer, UUID> values;
        private HashMap<Integer, Integer> stat;

        private Long lastUpdate;
        private String name;

        TypeTop(String name) {
            this.values = new HashMap<>();
            this.stat = new HashMap<>();
            this.name = name;
        }

        public HashMap<Integer, Integer> getStat() {
            return stat;
        }

        public void setStat(HashMap<Integer, Integer> stat) {
            this.stat = stat;
        }

        public HashMap<Integer, UUID> getValues() {
            return values;
        }

        public void setValues(HashMap<Integer, UUID> values) {
            this.values = values;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(Long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getFormattedLastUpdate(){
            double seconds = (System.currentTimeMillis() - this.lastUpdate) / 1000.0;
            int minutos = (int) (seconds / 60);
            return minutos + " minutos y " + ((int)seconds - (minutos*60)) + " segundos";
        }

        public void update(){
            this.lastUpdate = System.currentTimeMillis();

            if (this == KILLS_SOLO){
                HashMap<Integer, UUID> tops = new HashMap<>();
                int counter = 0;
                try {
                    ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats_solo` ORDER BY kills DESC LIMIT 10;").getResultSet();

                    for (int a = 0; a<10;a++) {
                        if (st.next()) {
                            tops.put(counter, UUID.fromString(st.getString("uuid")));
                            stat.put(counter, st.getInt("kills"));

                            counter++;
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (counter < 10){
                    for (int a = counter; counter < 10; counter++){
                        tops.put(a, null);
                        stat.put(a, null);
                    }
                }
                this.values = tops;
            }

            if (this == KILLS_TEAM){
                HashMap<Integer, UUID> tops = new HashMap<>();
                ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats_team` ORDER BY kills DESC LIMIT 10;").getResultSet();
                int counter = 0;
                try {
                    for (int a = 0; a<10;a++) {
                        if (st.next()) {
                            tops.put(counter, UUID.fromString(st.getString("uuid")));
                            stat.put(counter, st.getInt("kills"));
                            counter++;
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (counter < 10){
                    for (int a = counter; counter < 10; counter++){
                        tops.put(a, null);
                        stat.put(a, null);
                    }
                }
                this.values = tops;
            }

            if (this == WINS_SOLO){
                HashMap<Integer, UUID> tops = new HashMap<>();
                ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats_solo` ORDER BY wins DESC LIMIT 10;").getResultSet();
                int counter = 0;
                try {
                    for (int a = 0; a<10;a++) {
                        if (st.next()) {
                            tops.put(counter, UUID.fromString(st.getString("uuid")));
                            stat.put(counter, st.getInt("wins"));
                            counter++;
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (counter < 10){
                    for (int a = counter; counter < 10; counter++){
                        tops.put(a, null);
                        stat.put(a, null);
                    }
                }
                this.values = tops;
            }

            if (this == WINS_TEAM){
                HashMap<Integer, UUID> tops = new HashMap<>();
                ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats_team` ORDER BY wins DESC LIMIT 10;").getResultSet();
                int counter = 0;
                try {
                    for (int a = 0; a<10;a++) {
                        if (st.next()) {
                            tops.put(counter, UUID.fromString(st.getString("uuid")));
                            stat.put(counter, st.getInt("wins"));

                            counter++;
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (counter < 10){
                    for (int a = counter; counter < 10; counter++){
                        tops.put(a, null);
                        stat.put(a, null);
                    }
                }
                this.values = tops;
            }
            if (this == LEVEL){
                HashMap<Integer, UUID> tops = new HashMap<>();
                ResultSet st = Main.getDatabaseManager().query("SELECT * FROM `minecraft`.`players_stats` ORDER BY xp DESC LIMIT 10;").getResultSet();
                int counter = 0;
                try {
                    for (int a = 0; a<10;a++) {
                        if (st.next()) {
                            tops.put(counter, UUID.fromString(st.getString("uuid")));
                            stat.put(counter, st.getInt("xp"));

                            counter++;
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (counter < 10){
                    for (int a = counter; counter < 10; counter++){
                        tops.put(a, null);
                        stat.put(a, null);
                    }
                }
                this.values = tops;
            }
        }
    }

    public static void load(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for (TypeTop tops : TypeTop.values()){
                    tops.update();
                }

                SignLoad.Signs.TOP.update();
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, (20*60)*60);
    }
}
