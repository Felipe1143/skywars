package felipe221.skywars.menus.arena;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Kit;
import felipe221.skywars.object.User;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class TPMenu {
    public static void open(Player player){
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.SPECTATOR_TP.getTitle(), MenuLoad.Menus.SPECTATOR_TP.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.SPECTATOR_TP);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        Arena arena = User.getUser(player).getArena();

        if (arena != null) {
            for (Player players : arena.getPlayersAlive()) {
                ItemStack kitItem = MenuLoad.Menus.SPECTATOR_TP.getItems().get(0);

                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                skullMeta.setOwningPlayer(players);
                skullMeta.setDisplayName(kitItem.getItemMeta().getDisplayName().replaceAll("%player_name%", players.getName()));
                head.setItemMeta(skullMeta);

                inventory.addItem(head);
                entrys.put(counter, players.getName());
                counter++;
            }
        }else{
            player.sendMessage(ChatColor.RED + "¡No estás en ninguna arena!");

            return;
        }

        MenuLoad.Menus.SPECTATOR_TP.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
