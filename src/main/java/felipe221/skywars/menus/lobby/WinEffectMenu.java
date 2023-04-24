package felipe221.skywars.menus.lobby;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.Effect;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WinEffectMenu {
    public static void open(Player player) {
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.WIN_EFFECT.getTitle(), MenuLoad.Menus.WIN_EFFECT.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.WIN_EFFECT);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        for (Effect.WinEffect effect : Effect.WinEffect.values()) {
            ItemStack itemStack = MenuLoad.Menus.WIN_EFFECT.getItems().get(0);
            ItemStack copy = ItemBuilder.start(effect.getMaterial())
                    .name(BukkitUtil.replaceVariables(player, null, itemStack.getItemMeta().getDisplayName())
                            .replaceAll("%effect_name%", effect.getName()).replaceAll("%effect_price", "" +effect.getPrice()))
                    .lore(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(player, null, itemStack.getItemMeta().getLore()), "%effect_status%", (player.hasPermission("skywars.win." + effect.name()) == true ? "Desbloqueado" : "Bloqueado")), "%effect_price%", "" + effect.getPrice())).build();
            inventory.addItem(copy);
            entrys.put(counter, effect.name());
            counter++;
        }

        MenuLoad.Menus.WIN_EFFECT.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
