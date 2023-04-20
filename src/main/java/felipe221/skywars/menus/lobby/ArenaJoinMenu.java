package felipe221.skywars.menus.lobby;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.ArenaLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Mode;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ArenaJoinMenu {
    public static void open(Player player, Mode.TypeMode mode){
        int ROWS = MenuLoad.Menus.valueOf(mode.name()).getRows();
        String TITLE = MenuLoad.Menus.valueOf(mode.name()).getTitle();
        MenuGUI inventory = new MenuGUI(TITLE, ROWS);

        inventory.initList();
        inventory.initBeauty();
        inventory.setTypeMenu(MenuLoad.Menus.valueOf(mode.name()));

        int counter = 0;
        HashMap<Integer, String> entrys = new HashMap<>();

        if (!ArenaLoad.getArenasPerMode(mode).isEmpty()) {
            for (Arena arena : ArenaLoad.getArenasPerMode(mode)) {
                ItemStack itemStack = MenuLoad.Menus.valueOf(mode.name()).getItems().get(0);

                if (itemStack != null) {
                    inventory.addItem(ItemBuilder.start(arena.getStatus().getMaterial()).name(BukkitUtil.replaceVariables(player, arena, itemStack.getItemMeta().getDisplayName()))
                            .lore(BukkitUtil.replaceVariables(player, arena, itemStack.getItemMeta().getLore())).build());

                    entrys.put(counter, arena.getName());
                    counter++;
                }else{
                    player.sendMessage(ChatColor.RED + "Ocurrió un error grave con los menús,¡porfavor avisar al staff!");
                }
            }
        }else{
            player.sendMessage(ChatColor.RED + "¡No existen arenas para este modo de juego!");

            return;
        }

        MenuLoad.Menus.valueOf(mode.name()).setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
