package felipe221.skywars.load;

import felipe221.skywars.Main;

public class VariblesLoad {
    private static String chatFormat = "";

    public enum Variables{
        XP_KILL(0),
        XP_WIN(0),
        COINS_WIN(0),
        COINS_KILL(0);

        private int value;
        private String path;

        Variables(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static void load(){
        for (Variables var : Variables.values()){
            var.setValue(Main.getConfigManager().getConfig("config.yml").getInt("Game-Stats." + var.name()));
        }

        chatFormat = Main.getConfigManager().getConfig("config.yml").getString("Chat-Format");
    }

    public static String getChatFormat() {
        return chatFormat;
    }
}
