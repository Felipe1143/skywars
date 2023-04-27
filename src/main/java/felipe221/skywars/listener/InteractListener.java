package felipe221.skywars.listener;

import felipe221.skywars.load.ItemsLoad;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getItem() == null) {
            return;
        }

        if (!e.getItem().hasItemMeta()){
            return;
        }

        if (player.getOpenInventory().getType() == InventoryType.CHEST){
            return;
        }

        for (ItemsLoad.Items items : ItemsLoad.Items.values()) {
            if (items.getItemStack().getType() == e.getItem().getType()) {
                if (BukkitUtil.stripcolor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(BukkitUtil.stripcolor(items.getItemStack().getItemMeta().getDisplayName()))) {
                    items.setPlayer(player);
                    items.action();
                }
            }
        }
    }
}
