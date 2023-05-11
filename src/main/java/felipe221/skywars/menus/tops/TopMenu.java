package felipe221.skywars.menus.tops;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.iTop;
import felipe221.skywars.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TopMenu {
    public static void open(Player player, iTop.StatTop top) {
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.TOPS_MENU.getTitle(), MenuLoad.Menus.TOPS_MENU.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.TOPS_MENU);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        if (!top.getValues().isEmpty()) {
            for (Map.Entry<Integer, iTop.TopFormat> tops : top.getValues().entrySet()){
                if (tops.getValue() != null) {
                    UUID uuid = tops.getValue().getUuid();
                    int value = tops.getValue().getValue();

                    ItemStack itemStack = MenuLoad.Menus.TOPS_MENU.getItems().get(0);
                    ItemStack head = new ItemStack(Material.PLAYER_HEAD);

                    SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                    skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
                    skullMeta.setDisplayName(BukkitUtil.replaceVariables(Bukkit.getOfflinePlayer(uuid).getPlayer(), null, itemStack.getItemMeta().getDisplayName().replaceAll("%player_name%", Bukkit.getOfflinePlayer(uuid).getName())));
                    skullMeta.setLore(BukkitUtil.replaceVariableInList(
                            BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(Bukkit.getOfflinePlayer(uuid).getPlayer(), null, itemStack.getItemMeta().getLore()),
                            "%type_stat%", top.getStats().getName()), "%stat%", "" + value));
                    head.setItemMeta(skullMeta);

                    inventory.addItem(head);
                    entrys.put(counter, tops.getValue().toString());
                    counter++;
                }
            }
        }else{
            player.sendMessage(ChatColor.RED + "¡No hay ningún jugador en el top (¿Error?)!");

            return;
        }

        MenuLoad.Menus.TOPS_MENU.setEntrys(entrys);

        player.openInventory(inventory.getInventory());
    }
}
