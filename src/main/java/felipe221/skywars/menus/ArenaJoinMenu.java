package felipe221.skywars.menus;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.ArenaLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Mode;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArenaJoinMenu {
    public static void open(Player player, Mode.TypeMode mode){
        int ROWS = MenuLoad.Menus.valueOf(mode.name()).getRows();
        String TITLE = MenuLoad.Menus.valueOf(mode.name()).getTitle();
        MenuGUI inventory = new MenuGUI(TITLE, ROWS);
        inventory.initMapList();

        for (Arena arena : ArenaLoad.getArenasPerMode(mode)) {
            ItemStack itemStack = MenuLoad.Menus.valueOf(mode.name()).getItems().get(0);
            inventory.addItem(ItemBuilder.start(arena.getStatus().getMaterial()).name(itemStack.getItemMeta().getDisplayName())
                    .lore(BukkitUtil.replaceVariables(player, arena, itemStack.getItemMeta().getLore())).build());
        }

        player.openInventory(inventory.getInventory());
    }
}
