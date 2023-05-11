package felipe221.skywars.listener;

import felipe221.skywars.load.VariblesLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        e.setCancelled(true);

        Player player = e.getPlayer();
        Arena arena = User.getUser(player).getArena();

        if (arena == null){
            for (Player players : Bukkit.getOnlinePlayers()){
                if (User.getUser(players).getArena() == null){
                    players.sendMessage(BukkitUtil.replaceVariables(player, null, VariblesLoad.getChatFormat())
                            .replaceAll("%msg%", e.getMessage()));
                }
            }
        }else{
            if (arena.isSoloGame()) {
                for (Player arenaPlayer : arena.getAllPlayers()) {
                    arenaPlayer.sendMessage(BukkitUtil.replaceVariables(player, null, VariblesLoad.getChatFormat())
                            .replaceAll("%msg%", e.getMessage()));

                }
            }else{
                //global
                if (e.getMessage().startsWith("!") || User.getUser(player).getTeam() == null){
                    for (Player players : arena.getAllPlayers()) {
                        players.sendMessage(ChatColor.GRAY + "(Global) " + ChatColor.RESET + BukkitUtil.replaceVariables(player, null, VariblesLoad.getChatFormat())
                                .replaceAll("%msg%", e.getMessage().replace("!", "")));

                        return;
                    }
                }

                //team
                for (Player players : User.getUser(player).getTeam().getPlayers()) {
                    players.sendMessage(ChatColor.GRAY + "(Equipo) " + ChatColor.RESET +BukkitUtil.replaceVariables(player, null, VariblesLoad.getChatFormat())
                                .replaceAll("%msg%", e.getMessage()));
                }
            }
        }
    }
}
