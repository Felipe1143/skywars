package felipe221.skywars.object;

import felipe221.skywars.FastBoard;
import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class Teams {
    private int size;
    private Colors color;
    private int id;
    private ArrayList<Player> players;
    private Location spawn;
    private Team team;

    public Teams(int size, Colors color, int id, Arena arena, Location spawn) {
        this.size = size;
        this.color = color;
        this.id = id;
        this.spawn = spawn;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        this.team = board.registerNewTeam("T/" + id + "/" + arena.getID());
        this.team.setColor(color.getColor());
        players = new ArrayList<>();
    }

    public boolean isAlive(){
        int aliveUsers = 0;

        for (Player player : getPlayers()){
            if (User.getUser(player).isAlive()){
                aliveUsers++;
            }
        }

        return aliveUsers > 0;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void removePlayer(Player player){
        this.players.remove(player);
        this.team.removeEntry(player.getName());
    }

    //5, 4, 5, 5
    public boolean isBalanced(ArrayList<Integer> integers, int valueJoin){
        int maxValue=-1;
        int minValue=1000;

        for (int a : integers){
            if (a>maxValue){
                maxValue=a;
            }

            if (a<minValue){
                minValue=a;
            }
        }

        if (minValue == valueJoin){
            return true;
        }

        //5 4 5 5
        //minValue 4
        //valueJoin 6
        //5 < 6
        if (minValue + 1 < valueJoin){
            return false;
        }else{
            return true;
        }
    }

    public void addPlayer(Player player){
        Teams team_selected = null;
        boolean isInTeam = false;

        for (Teams teams : User.getUser(player).getArena().getTeams()) {
            if (teams.getPlayers().contains(player)){
                isInTeam = true;
                break;
            }
        }

        if (isInTeam){
            player.sendMessage(MessagesLoad.MessagesLine.HAVE_TEAM.setPlayer(player).setArena(User.getUser(player).getArena()).getMessage());

            return;
        }

        if (this.size < this.players.size()){
            this.players.add(player);
            this.team.addEntry(player.getName());

            player.sendMessage(MessagesLoad.MessagesLine.JOIN_TEAM.getMessage().replaceAll("%team_name%", this.color.getName()).replaceAll("%team_color%", "" + color.getColor()));
        }else{
            player.sendMessage(MessagesLoad.MessagesLine.JOIN_TEAM_FULL.getMessage());
        }
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public enum Colors{
        NEGRO(ChatColor.BLACK, "Negro", 0),
        AZUL(ChatColor.BLUE, "Azul", 1),
        VERDE(ChatColor.DARK_GREEN, "Verde", 2),
        AZUL_OSCURO(ChatColor.DARK_BLUE, "Azul oscuro", 3),
        ROJO(ChatColor.DARK_RED, "Rojo", 4),
        VIOLETA(ChatColor.DARK_PURPLE, "Violeta", 5),
        DORADO(ChatColor.GOLD, "Dorado", 6),
        GRIS(ChatColor.DARK_GRAY, "Gris", 7),
        GRIS_CLARO(ChatColor.GRAY, "Gris claro", 8),
        VERDE_CLARO(ChatColor.GREEN, "Verde claro", 9),
        CELESTE(ChatColor.AQUA, "Celeste", 10),
        ROJO_CLARO(ChatColor.RED, "Rojo claro", 11),
        ROSA(ChatColor.LIGHT_PURPLE, "Rosa", 12),
        CIAN(ChatColor.DARK_AQUA, "Cian", 13),
        AMARILLO(ChatColor.YELLOW, "Amarillo", 14);

        private ChatColor color;
        private String name;
        private int id;

        public ChatColor getColor() {
            return color;
        }

        public void setColor(ChatColor color) {
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        Colors(ChatColor color, String name, int id) {
            this.color = color;
            this.name = name;
            this.id = id;
        }
    }
}
