package felipe221.skywars.events;

import felipe221.skywars.object.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class PlayerLeaveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Arena arena;
    private Player player;

    public PlayerLeaveEvent(Arena arena, Player player) {
        this.arena = arena;
        this.player = player;
    }

    public Arena getArena() {
        return arena;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}