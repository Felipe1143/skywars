package felipe221.skywars.menus.lobby;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SoundListMenu {
    public static void open(Player player) {
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.SOUNDS.getTitle(), MenuLoad.Menus.SOUNDS.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.SOUNDS);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        for (Sound sound : Sound.values()) {
                ItemStack copy = ItemBuilder.start(Material.PAPER)
                    .name(sound.name()).build();
            inventory.addItem(copy);
            entrys.put(counter, sound.name());
            counter++;
        }

        MenuLoad.Menus.SOUNDS.setEntrys(entrys);
        player.openInventory(inventory.getInventory());
    }
}
