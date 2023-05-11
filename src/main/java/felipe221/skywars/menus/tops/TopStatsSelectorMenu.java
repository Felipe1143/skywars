package felipe221.skywars.menus.tops;

import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.iStats;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class TopStatsSelectorMenu {
    public static void open(Player player) {
        int ROWS = MenuLoad.Menus.TOPS_STATS_SELECTOR.getRows();
        String TITLE = MenuLoad.Menus.TOPS_STATS_SELECTOR.getTitle();
        Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.TOPS_STATS_SELECTOR.getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();

            ItemStack copy = ItemBuilder.start(item.getType())
                    .name(BukkitUtil.replaceVariables(player, null, item.getItemMeta().getDisplayName()))
                    .lore(item.getItemMeta().getLore()).build();
            menu.setItem(slot, copy);
        }

        player.openInventory(menu);
    }
}
