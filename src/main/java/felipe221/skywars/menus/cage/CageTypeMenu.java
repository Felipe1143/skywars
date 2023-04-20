package felipe221.skywars.menus.cage;

import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CageTypeMenu {
    public static void open(Player player) {
        int ROWS = MenuLoad.Menus.CAGE_TYPE.getRows();
        String TITLE = MenuLoad.Menus.CAGE_TYPE.getTitle();
        Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.CAGE_TYPE.getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();
            ItemStack copy = ItemBuilder.start(item.getType())
                    .name(BukkitUtil.replaceVariables(player, null, item.getItemMeta().getDisplayName()))
                    .lore(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(player, null, item.getItemMeta().getLore()), "%cage_status%", (player.hasPermission("skywars.type." + MenuLoad.Menus.CAGE_TYPE.getEntrys().get(slot)) == true ? "Desbloqueado" : "Bloqueado"))).build();
            menu.setItem(slot, copy);
        }

        player.openInventory(menu);
    }
}
