package felipe221.skywars.controller;

import felipe221.skywars.load.ArenaLoad;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.User;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfigMenuController implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (BukkitUtil.stripcolor(e.getView().getTitle()).equals("Configuración:")){
            if (e.getCurrentItem().getType() == Material.GRASS_BLOCK){
                if (ArenaLoad.getEditing().isEmpty()) {
                    ArenaLoad.getMapsFromConfig(player);
                }else{
                    e.setCancelled(true);

                    player.sendMessage(ChatColor.RED + "Ya hay un usuario editantando las arenas, ¡espera que termine!");
                }

                return;
            }

            if (e.getCurrentItem().getType() == Material.BOW){
                if (KitController.getEditing().isEmpty()) {
                    KitLoad.fromConfigList(player);
                }else{
                    e.setCancelled(true);

                    player.sendMessage(ChatColor.RED + "Ya hay un usuario editantando los kits, ¡espera que termine!");
                }

                return;
            }

            if (e.getCurrentItem().getType() == Material.CHEST){
                player.closeInventory();
                ChestController.set(player);

                return;
            }
        }
    }
}
