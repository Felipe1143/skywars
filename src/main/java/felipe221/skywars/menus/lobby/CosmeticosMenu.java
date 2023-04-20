package felipe221.skywars.menus.lobby;

import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.User;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CosmeticosMenu {
    public static void open(Player player) {
        int ROWS = MenuLoad.Menus.COSMETICS.getRows();
        String TITLE = MenuLoad.Menus.COSMETICS.getTitle();
        Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.COSMETICS.getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();
            ItemStack newItem = ItemBuilder.start(item.getType()).name(BukkitUtil.replaceVariables(player, null, item.getItemMeta().getDisplayName())).lore(BukkitUtil.replaceVariables(player, null, item.getItemMeta().getLore())).build();
            menu.setItem(slot, newItem);
        }

        player.openInventory(menu);
    }
}
