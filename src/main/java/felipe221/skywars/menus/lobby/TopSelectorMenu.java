package felipe221.skywars.menus.lobby;

import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.load.TopLoad;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class TopSelectorMenu {
    public static void open(Player player) {
        int ROWS = MenuLoad.Menus.TOPS_SELECTOR.getRows();
        String TITLE = MenuLoad.Menus.TOPS_SELECTOR.getTitle();
        Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.TOPS_SELECTOR.getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();
            ItemStack copy = ItemBuilder.start(item.getType())
                    .name(BukkitUtil.replaceVariables(player, null, item.getItemMeta().getDisplayName()))
                    .lore(BukkitUtil.replaceVariableInList(item.getItemMeta().getLore(), "%last_update%", TopLoad.TypeTop.KILLS_SOLO.getFormattedLastUpdate())).build();
            menu.setItem(slot, copy);
        }

        player.openInventory(menu);
    }
}
