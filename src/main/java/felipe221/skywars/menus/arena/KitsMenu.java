package felipe221.skywars.menus.arena;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.load.VariblesLoad;
import felipe221.skywars.object.Kit;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class KitsMenu {

    public static void open(Player player){
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.KITS.getTitle(), MenuLoad.Menus.KITS.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.KITS);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        if (!KitLoad.getKits().isEmpty()) {
            for (Kit kit : KitLoad.getKits()) {
                if (player.hasPermission("skywars.kit." + kit.getConfigName())) {
                    ItemStack copy = ItemBuilder.start(kit.getItemMenu().getType()).name(kit.getItemMenu().getItemMeta().getDisplayName().replaceAll("%kit_name%", kit.getName())).build();
                    ItemMeta meta = copy.getItemMeta();
                    meta.setLore(null);
                    meta.setLore(VariblesLoad.VariablesList.SHOP_MENU_LORE.groupWith(kit.getLore()));
                    copy.setItemMeta(meta);
                    inventory.addItem(copy);
                } else {
                    inventory.addItem(ItemBuilder.start(Material.RED_STAINED_GLASS_PANE).lore(VariblesLoad.VariablesList.SHOP_MENU_LORE.groupWith(kit.getLore())).name(kit.getName()).build());
                }

                entrys.put(counter, kit.getConfigName());
                counter++;
            }
        }else{
            player.sendMessage(ChatColor.RED + "¡No hay ningún kit creado!");

            return;
        }

        MenuLoad.Menus.KITS.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
