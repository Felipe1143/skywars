package felipe221.skywars.menus.lobby;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.CageLoad;
import felipe221.skywars.load.KillsLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.load.VariblesLoad;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TematicasMenu {
    public static void open(Player player) {
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.TEMATICAS.getTitle(), MenuLoad.Menus.TEMATICAS.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.TEMATICAS);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        if (!KillsLoad.getListTematicas().isEmpty()) {
            for (String killTematicas : KillsLoad.getListTematicas().keySet()){
                ItemStack itemStack = MenuLoad.Menus.TEMATICAS.getItems().get(0);
                ItemStack copy = ItemBuilder.start(KillsLoad.getMaterialForTematica(killTematicas))
                        .name(BukkitUtil.replaceVariables(player,null,itemStack.getItemMeta().getDisplayName())
                                .replaceAll("%tematica_name%", (killTematicas.equals("NONE") ? "Ninguna" : killTematicas)).replaceAll("%tematica_price", "" + KillsLoad.getPriceForTematica(killTematicas)))
                        .lore(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(player,null, VariblesLoad.VariablesList.SHOP_MENU_LORE.groupWith(itemStack.getItemMeta().getLore())), "%tematica_status%", (player.hasPermission("skywars.tematica." + killTematicas) == true ? "Desbloqueado" : "Bloqueado")), "%tematica_price%", "" + KillsLoad.getPriceForTematica(killTematicas))).build();
                inventory.addItem(copy);
                entrys.put(counter, killTematicas);
                counter++;
            }
        }else{
            player.sendMessage(ChatColor.RED + "¡No hay ningúna temática disponible!");

            return;
        }

        MenuLoad.Menus.TEMATICAS.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
