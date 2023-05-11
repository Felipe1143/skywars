package felipe221.skywars.menus.arena;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.Kit;
import felipe221.skywars.object.Teams;
import felipe221.skywars.object.User;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TeamMenu {
    public static void open(Player player){
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.TEAMS.getTitle(), MenuLoad.Menus.TEAMS.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.TEAMS);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        if (!User.getUser(player).getArena().getTeams().isEmpty()) {
            for (Teams team : User.getUser(player).getArena().getTeams()) {
                ItemStack teamItem = MenuLoad.Menus.TEAMS.getItems().get(0);
                ItemStack copy = ItemBuilder.start(Material.valueOf(team.getColor().name() + "_WOOL"))
                        .lore(BukkitUtil.replaceVariableInList(
                                BukkitUtil.replaceVariableInList(
                                        BukkitUtil.replaceVariableInList(
                                BukkitUtil.replaceVariables(player, null, teamItem.getItemMeta().getLore()), "%team_color%", "" + team.getColor().getColor()), "%team_players", "" + team.getPlayers().size()), "%team_max%", "" + team.getSize()))
                                .name(BukkitUtil.replaceVariables(player, null, teamItem.getItemMeta().getDisplayName())
                                        .replaceAll("%team_color%", "" + team.getColor().getColor())
                                        .replaceAll("%team_players", "" + team.getPlayers().size())
                                        .replaceAll("%team_size%", "" + team.getSize())).build();
                inventory.addItem(copy);
                entrys.put(counter, "" + team.getID());
                counter++;
            }
        }else{
            player.sendMessage(ChatColor.RED + "¡No hay ningún equipo!");

            return;
        }

        MenuLoad.Menus.TEAMS.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
