package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.object.Arena;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessagesLoad {
    public enum MessagesLine{
        ARENA_MAX(""),
        COUNT_JOIN(""),
        START_IN(""),
        PLAYER_OUT_MIN(""),
        SUCCESSFULL_JOIN(""),
        TEAM_MAX(""),
        HAVE_TEAM(""),
        BORDER_MOVE(""),
        NOT_ARENA(""),
        KIT_LOCKED(""),
        CAGE_MATERIAL_SELECTED(""),
        CAGE_MATERIAL_LOCKED(""),
        CAGE_TYPE_SELECTED(""),
        CAGE_TYPE_LOCKED(""),
        ARENA_STARTED(""),
        KIT_SELECTED("");

        private String message;
        private Arena arena;
        private Player player;

        MessagesLine(String message){
            this.message = message;
            this.player = null;
            this.arena = null;
        }

        public String getMessage() {
            return BukkitUtil.replaceVariables(player, arena,message);
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Arena getArena() {
            return arena;
        }

        public MessagesLine setArena(Arena arena) {
            this.arena = arena;
            return this;
        }

        public Player getPlayer() {
            return player;
        }

        public MessagesLine setPlayer(Player player) {
            this.player = player;
            return this;
        }
    }

    public enum MessagesList{
        VOTES_CLOSE(new ArrayList<>());


        private List<String> message;
        private Arena arena;
        private Player player;

        MessagesList(List<String> message){
            this.message = message;
            this.player = null;
            this.arena = null;
        }

        public List<String> getMessage() {
            return BukkitUtil.replaceVariables(player, arena,message);
        }

        public void setMessage(List<String> message) {
            this.message = message;
        }

        public Arena getArena() {
            return arena;
        }

        public MessagesList setArena(Arena arena) {
            this.arena = arena;
            return this;
        }

        public Player getPlayer() {
            return player;
        }

        public MessagesList setPlayer(Player player) {
            this.player = player;
            return this;
        }
    }

    public static void load(){
        for (MessagesLine line : MessagesLine.values()){
            String msg = line.name().toUpperCase();

            line.setMessage(Main.getConfigManager().getConfig("messages.yml").getString("Messages." + msg));
        }

        for (MessagesList lines : MessagesList.values()){
            String msg = lines.name().toUpperCase();

            lines.setMessage(Main.getConfigManager().getConfig("messages.yml").getStringList("Messages-List." + msg));
        }
    }
}
