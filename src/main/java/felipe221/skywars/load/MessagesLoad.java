package felipe221.skywars.load;

import felipe221.skywars.Main;
import org.bukkit.ChatColor;

public class MessagesLoad {
    public enum MessagesLine{
        ARENA_MAX(""),
        COUNT_JOIN(""),
        START_IN(""),
        PLAYER_OUT_MIN(""),
        SUCCESSFULL_JOIN("");

        private String message;

        MessagesLine(String message){
            this.message = message;
        }

        public String getMessage() {
            //TODO replace with all variables
            return ChatColor.translateAlternateColorCodes('&', message);
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static void load(){
        for (MessagesLine line : MessagesLine.values()){
            String msg = line.name().toUpperCase();

            line.setMessage(Main.getConfigManager().getConfig("messages.yml").getString("Messages." + msg));
        }
    }
}
