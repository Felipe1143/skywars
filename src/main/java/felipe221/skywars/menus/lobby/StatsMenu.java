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

public class StatsMenu {
    public static void open(Player player){
        int ROWS = MenuLoad.Menus.STATS.getRows();
        String TITLE = MenuLoad.Menus.STATS.getTitle();
        Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.STATS.getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();

            menu.setItem(slot, ItemBuilder.start(item.getType()).lore(BukkitUtil.replaceVariables(player, User.getUser(player).getArena(), item.getItemMeta().getLore())).name(BukkitUtil.replaceVariables(player, User.getUser(player).getArena(), item.getItemMeta().getDisplayName())).build());
        }

        player.openInventory(menu);
    }

}
