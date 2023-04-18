package felipe221.skywars.menus;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.Kit;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitsMenu {

    public static void open(Player player){
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.KITS.getTitle(), MenuLoad.Menus.KITS.getRows());
        inventory.initBeauty();
        inventory.initKitList();

        for (Kit kit : KitLoad.getKits()){
            if (player.hasPermission(kit.getPermission())){
                ItemStack kitItem = MenuLoad.Menus.KITS.getItems().get(0);
                inventory.addItem(ItemBuilder.start(kit.getItemMenu().getType()).lore(kit.getLore()).name(kitItem.getItemMeta().getDisplayName().replaceAll("%kit_name%", kit.getName())).build());
            }else{
                inventory.addItem(ItemBuilder.start(Material.RED_STAINED_GLASS_PANE).lore(kit.getLore()).name(kit.getName()).build());
            }
        }

        player.openInventory(inventory.getInventory());
    }
}
