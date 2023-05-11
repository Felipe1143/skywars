package felipe221.skywars.menus.lobby;

import felipe221.skywars.controller.ShopController;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ShopMenu {
    public static void open(ShopController.TypeShop shop, String objectShop, int price, ItemStack itemStack, Player player) {
        int ROWS = MenuLoad.Menus.SHOP.getRows();
        String TITLE = MenuLoad.Menus.SHOP.getTitle();
        Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.SHOP.getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();

            if (MenuLoad.Menus.SHOP.getEntrys().get(slot).equalsIgnoreCase("ITEM")){
                ItemMeta copy = itemStack.getItemMeta();
                copy.setLore(null);
                itemStack.setItemMeta(copy);

                menu.setItem(slot, itemStack);

                continue;
            }

            ItemStack item = entry.getValue();
            ItemStack copy = ItemBuilder.start(item.getType())
                    .name(BukkitUtil.replaceVariables(player, null, item.getItemMeta().getDisplayName()))
                    .lore(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(player, null, item.getItemMeta().getLore()), "%cage_status%", (player.hasPermission("skywars.type." + MenuLoad.Menus.CAGE_TYPE.getEntrys().get(slot)) == true ? "Desbloqueado" : "Bloqueado"))).build();
            menu.setItem(slot, copy);
        }

        ShopController shopController = new ShopController(player, price, objectShop, shop);
        player.openInventory(menu);
    }
}
