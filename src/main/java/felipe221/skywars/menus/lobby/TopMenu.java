package felipe221.skywars.menus.lobby;

import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.load.CageLoad;
import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.load.TopLoad;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TopMenu {
    public static void open(Player player, TopLoad.TypeTop type) {
        MenuGUI inventory = new MenuGUI(MenuLoad.Menus.TOPS_MENU.getTitle(), MenuLoad.Menus.TOPS_MENU.getRows());
        inventory.initBeauty();
        inventory.initList();
        inventory.setTypeMenu(MenuLoad.Menus.TOPS_MENU);

        HashMap<Integer, String> entrys = new HashMap<>();
        int counter = 0;

        if (!type.getValues().isEmpty()) {
            for (Map.Entry<Integer, UUID> tops : type.getValues().entrySet()){
                if (tops.getValue() != null) {
                    ItemStack itemStack = MenuLoad.Menus.TOPS_MENU.getItems().get(0);
                    ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                    skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(tops.getValue()));
                    skullMeta.setDisplayName(BukkitUtil.replaceVariables(Bukkit.getOfflinePlayer(tops.getValue()).getPlayer(), null, itemStack.getItemMeta().getDisplayName().replaceAll("%player_name%", Bukkit.getOfflinePlayer(tops.getValue()).getName())));
                    skullMeta.setLore(BukkitUtil.replaceVariableInList(
                            BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(Bukkit.getOfflinePlayer(tops.getValue()).getPlayer(), null, itemStack.getItemMeta().getLore()),
                            "%type_stat%", type.getName()), "%stat%", "" + type.getStat().get(counter)));
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
