package felipe221.skywars.menus.arena;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.Kit;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
                if (player.hasPermission(kit.getPermission())) {
                    ItemStack kitItem = MenuLoad.Menus.KITS.getItems().get(0);
                    ItemStack copy = ItemBuilder.start(kit.getItemMenu().getType()).lore(kit.getLore()).name(kitItem.getItemMeta().getDisplayName().replaceAll("%kit_name%", kit.getName())).build();
                    inventory.addItem(copy);
                    entrys.put(counter, kit.getConfigName());
                    counter++;
                } else {
                    inventory.addItem(ItemBuilder.start(Material.RED_STAINED_GLASS_PANE).lore(kit.getLore()).name(kit.getName()).build());
                }
            }
        }else{
            player.sendMessage(ChatColor.RED + "¡No hay ningún kit creado!");

            return;
        }

        MenuLoad.Menus.KITS.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
