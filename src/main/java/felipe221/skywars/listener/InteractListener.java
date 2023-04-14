package felipe221.skywars.listener;

import felipe221.skywars.load.ArenaLoad;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InteractListener implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (BukkitUtil.stripcolor(e.getView().getTitle()).equals("Arenas:")) {
            if (e.getCurrentItem().getType() == Material.CHEST){
                player.closeInventory();
                ArenaLoad.getAllArenaListMenu(player);
            }
        }
    }
}
