package felipe221.skywars.object;

import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class Teams {
    private int size;
    private ChatColor color;
    private int id;
    private ArrayList<Player> players;
    private Team team;

    public Teams(int size, ChatColor color, int id, Arena arena) {
        this.size = size;
        this.color = color;
        this.id = id;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        this.team = board.registerNewTeam("T/" + id + "/" + arena.getID());
        this.team.setColor(color);
        players = new ArrayList<>();
    }

    public ItemStack getItemStack(){
        ArrayList<String> loreNames = new ArrayList<>();
        if (getPlayers().isEmpty()){
            loreNames.add("&7¡Este equipo está vacio!");
        }else{
            loreNames.add("&7Jugadores: ");

            for (Player player : players){
                loreNames.add(getColor() + "- &7" + player.getName());
            }
        }
        loreNames.add("&7");
        loreNames.add(getColor() + "¡Haz click para unirte!");

        return ItemBuilder.start(Material.getMaterial(getColor().toString().toUpperCase() + "_WOOL"))
                .name(color + "Equipo " + id + " (" + getPlayers().size()+ "/" + getSize()+")")
                .lore(loreNames)
                .build();
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

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
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

    public void removePlayer(Player player){
        this.players.remove(player);
        this.team.removeEntry(player.getName());
    }

    public void addPlayer(Player player){
        this.players.add(player);
        this.team.addEntry(player.getName());
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
