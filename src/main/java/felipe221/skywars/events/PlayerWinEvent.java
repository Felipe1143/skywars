package felipe221.skywars.events;

import felipe221.skywars.object.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class PlayerWinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Arena arena;
    private ArrayList<Player> players;

    public PlayerWinEvent(Arena arena, ArrayList<Player> players) {
        this.arena = arena;
        this.players = players;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
