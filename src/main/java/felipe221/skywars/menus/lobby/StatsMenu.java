package felipe221.skywars.menus.lobby;

import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.object.User;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class StatsMenu {
    public static void open(Player open, UUID other){
        int ROWS = MenuLoad.Menus.STATS.getRows();
        String TITLE = MenuLoad.Menus.STATS.getTitle();
        Inventory menu = Bukkit.createInventory(open, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.STATS.getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();

            if (Bukkit.getPlayer(other) == null || !Bukkit.getPlayer(other).isOnline()) {
                //get stats from mysql
                open.sendMessage(MessagesLoad.MessagesLine.PLAYER_STAT_DISCONNECT.getMessage().replaceAll("%player%", Bukkit.getOfflinePlayer(other).getName()));

                return;
            } else {
                menu.setItem(slot, ItemBuilder.start(item.getType()).lore(BukkitUtil.replaceVariables(Bukkit.getPlayer(other), null, item.getItemMeta().getLore())).name(BukkitUtil.replaceVariables(Bukkit.getPlayer(other), User.getUser(Bukkit.getPlayer(other)).getArena(), item.getItemMeta().getDisplayName())).build());
            }
        }

        open.openInventory(menu);
    }
}
