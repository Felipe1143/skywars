package felipe221.skywars.listener;

import felipe221.skywars.load.MenuLoad;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        for (MenuLoad.Menus menus : MenuLoad.Menus.values()){
            if (menus.getTitle().equalsIgnoreCase(e.getView().getTitle())){
                e.setCancelled(true);
                menus.setPlayer(player);

                if (e.getClick() == ClickType.LEFT) {
                    menus.action(e.getCurrentItem(), e.getSlot(), ClickType.LEFT);
                }else if(e.getClick() == ClickType.RIGHT){
                    menus.action(e.getCurrentItem(), e.getSlot(), ClickType.RIGHT);
                }else{
                    menus.action(e.getCurrentItem(), e.getSlot(), ClickType.LEFT);
                }
            }
        }
    }
}
