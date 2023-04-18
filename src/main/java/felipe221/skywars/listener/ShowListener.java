package felipe221.skywars.listener;

import felipe221.skywars.Main;
import felipe221.skywars.events.PlayerJoinGameEvent;
import felipe221.skywars.events.PlayerLeaveGameEvent;
import felipe221.skywars.object.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ShowListener implements Listener {

    @EventHandler
    public void onPlayerLeaveGame(PlayerLeaveGameEvent e){
        Player player = e.getPlayer();

        for (Player allPlayers : Bukkit.getOnlinePlayers()){
            if (User.getUser(allPlayers).getArena() == null){
                allPlayers.showPlayer(Main.getInstance(), player);
                player.showPlayer(Main.getInstance(), allPlayers);
            }else{
                allPlayers.hidePlayer(Main.getInstance(), player);
                player.hidePlayer(Main.getInstance(), allPlayers);
            }
        }
    }

    @EventHandler
    public void onPlayerJoinGame(PlayerJoinGameEvent e) {
        Player player = e.getPlayer();

        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
            if (User.getUser(allPlayers).getArena().getID() == User.getUser(player).getArena().getID()){
                allPlayers.showPlayer(Main.getInstance(), player);
                player.showPlayer(Main.getInstance(), allPlayers);
            }else{
                allPlayers.hidePlayer(Main.getInstance(), player);
                player.hidePlayer(Main.getInstance(), allPlayers);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        for (Player allPlayers : Bukkit.getOnlinePlayers()){
            if (User.getUser(allPlayers).getArena() == null){
                allPlayers.showPlayer(Main.getInstance(), player);
                player.showPlayer(Main.getInstance(), allPlayers);
            }else{
                allPlayers.hidePlayer(Main.getInstance(), player);
                player.hidePlayer(Main.getInstance(), allPlayers);
            }
        }
    }

    public static void deathHide(Player player){
        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
            if (User.getUser(allPlayers).getArena().getID() == User.getUser(player).getArena().getID()){
                allPlayers.hidePlayer(Main.getInstance(), player);
            }
        }
    }

    //items lobbys
    public static void showPlayers(Player player){
        for (Player allPlayers : Bukkit.getOnlinePlayers()){
            if (User.getUser(allPlayers).getArena() == null){
                allPlayers.showPlayer(Main.getInstance(), player);
                player.showPlayer(Main.getInstance(), allPlayers);
            }else{
                allPlayers.hidePlayer(Main.getInstance(), player);
                player.hidePlayer(Main.getInstance(), allPlayers);
            }
        }
    }

    public static void hidePlayers(Player player){
        for (Player allPlayers : Bukkit.getOnlinePlayers()){
            if (User.getUser(allPlayers).getArena() == null){
                allPlayers.hidePlayer(Main.getInstance(), player);
                player.hidePlayer(Main.getInstance(), allPlayers);
            }else{
                allPlayers.hidePlayer(Main.getInstance(), player);
                player.hidePlayer(Main.getInstance(), allPlayers);
            }
        }
    }
}
