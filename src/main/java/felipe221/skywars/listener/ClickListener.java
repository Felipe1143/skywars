package felipe221.skywars.listener;

import felipe221.skywars.Main;
import felipe221.skywars.load.ItemsLoad;
import felipe221.skywars.menus.ArenaMenu;
import felipe221.skywars.object.User;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClickListener implements Listener{
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        User user = User.getUser(player);
        ItemStack item = e.getItem();

        if (item == null){
            return;
        }

        if (!e.getItem().hasItemMeta()){
            return;
        }

        String ITEM_NAME = BukkitUtil.stripcolor(e.getItem().getItemMeta().getDisplayName());

        //player in lobby
        if (user.getArena() == null) {
            //selector game
            if (ITEM_NAME.equals(BukkitUtil.stripcolor(ItemsLoad.Items.GAME_SELECTOR.getName()))) {
                ArenaMenu.openSelect(player);
            }
        }
    }
}
