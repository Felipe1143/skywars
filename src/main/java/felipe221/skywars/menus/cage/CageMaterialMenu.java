package felipe221.skywars.menus.cage;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.CageLoad;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CageMaterialMenu {
    public static void open(Player player) {
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.CAGE_MATERIAL.getTitle(), MenuLoad.Menus.CAGE_MATERIAL.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.CAGE_MATERIAL);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        if (!CageLoad.getMaterials().isEmpty()) {
            for (Material material : CageLoad.getMaterials()){
                ItemStack itemStack = MenuLoad.Menus.CAGE_MATERIAL.getItems().get(0);
                ItemStack copy = ItemBuilder.start(material)
                        .name(BukkitUtil.replaceVariables(player,null,itemStack.getItemMeta().getDisplayName())
                                .replaceAll("%cage_name%", CageLoad.getMaterialName(material)))
                        .lore(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(player,null,itemStack.getItemMeta().getLore()), "%cage_status%", (player.hasPermission("skywars.material." + material.name()) == true ? "Desbloqueado" : "Bloqueado"))).build();
                inventory.addItem(copy);
                entrys.put(counter, material.name());
                counter++;
            }
        }else{
            player.sendMessage(ChatColor.RED + "¡No hay ningún material disponible!");

            return;
        }

        MenuLoad.Menus.CAGE_MATERIAL.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
