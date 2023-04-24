package felipe221.skywars.menus.lobby;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.Effect;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TrailsMenu {
    public static void open(Player player) {
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.TRAILS.getTitle(), MenuLoad.Menus.TRAILS.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.TRAILS);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        for (Effect.WinEffect effect : Effect.WinEffect.values()) {
            ItemStack itemStack = MenuLoad.Menus.TRAILS.getItems().get(0);
            ItemStack copy = ItemBuilder.start(effect.getMaterial())
                    .name(BukkitUtil.replaceVariables(player, null, itemStack.getItemMeta().getDisplayName())
                            .replaceAll("%trail_name%", effect.getName()).replaceAll("%trail_price", "" +effect.getPrice()))
                    .lore(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(player, null, itemStack.getItemMeta().getLore()), "%trail_status%", (player.hasPermission("skywars.trail." + effect.name()) == true ? "Desbloqueado" : "Bloqueado")), "%trail_price%", "" + effect.getPrice())).build();
            inventory.addItem(copy);
            entrys.put(counter, effect.name());
            counter++;
        }

        MenuLoad.Menus.TRAILS.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
