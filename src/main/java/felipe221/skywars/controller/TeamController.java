package felipe221.skywars.controller;

import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.object.Teams;
import felipe221.skywars.object.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TeamController implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) {
            return;
        }

        if (User.getUser(player).getArena() == null) {
            return;
        }

        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        //team menu
        if (e.getView().getTitle().equals("Equipos:")) {
            Teams team_selected = null;
            boolean isInTeam = false;

            for (Teams teams : User.getUser(player).getArena().getTeams()) {
                if (teams.getPlayers().contains(player)){
                    isInTeam = true;
                    break;
                }
                if (e.getCurrentItem().equals(teams.getItemStack())) {
                    team_selected = teams;
                    break;
                }
            }

            if (isInTeam){
                player.sendMessage(MessagesLoad.MessagesLine.HAVE_TEAM.setPlayer(player).setArena(User.getUser(player).getArena()).getMessage());

                return;
            }
            if (team_selected.getPlayers().size() >= team_selected.getSize()){
                player.sendMessage(MessagesLoad.MessagesLine.TEAM_MAX.setPlayer(player).setArena(User.getUser(player).getArena()).getMessage());

                return;
            }

            team_selected.addPlayer(player);
        }
    }
}
