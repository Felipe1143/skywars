package felipe221.skywars.object;

import org.bukkit.entity.Player;

public class Hearts {
    public enum TypeHearts{
        C10("10 corazones", 20), C20("20 corazones", 40), C30("30 corazones", 60);

        private String name;
        private int hearts;

        TypeHearts(String name, int hearts) {
            this.name = name;
            this.hearts = hearts;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHearts() {
            return hearts;
        }

        public void setHearts(int hearts) {
            this.hearts = hearts;
        }

        public void set(Arena arena){
            for (Player player : arena.getPlayersAlive()){
                player.setMaxHealth(this.hearts);
                player.setHealth(this.hearts);
            }
        }
    }
}
