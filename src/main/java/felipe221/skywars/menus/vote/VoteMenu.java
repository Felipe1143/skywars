package felipe221.skywars.menus.vote;

import felipe221.skywars.load.MenuLoad;
import felipe221.skywars.object.User;
import felipe221.skywars.object.Vote;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class VoteMenu {
    public static void open(Player player){
        int ROWS = MenuLoad.Menus.VOTE.getRows();
        String TITLE = MenuLoad.Menus.VOTE.getTitle();
        Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.VOTE.getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();

            menu.setItem(slot, ItemBuilder.start(item.getType()).lore(BukkitUtil.replaceVariables(player, User.getUser(player).getArena(), item.getItemMeta().getLore())).name(BukkitUtil.replaceVariables(player, User.getUser(player).getArena(), item.getItemMeta().getDisplayName())).build());
        }

        player.openInventory(menu);
    }

    public static void openVote(Player player, Vote.TypeVote type){
        int ROWS = MenuLoad.Menus.valueOf(type.name()).getRows();
        String TITLE = MenuLoad.Menus.valueOf(type.name()).getTitle();
        Inventory menu = Bukkit.createInventory(player, 9 * ROWS, TITLE);

        for (Map.Entry<Integer, ItemStack> entry : MenuLoad.Menus.valueOf(type.name()).getItemsWithSlot().entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();
            String nameType = MenuLoad.Menus.valueOf(type.name()).getEntrys().get(slot);
            int votes = User.getUser(player).getArena().getVoteByEnum(type).getVotes(nameType);

            menu.setItem(slot, ItemBuilder.start(item.getType()).lore(BukkitUtil.replaceVariableInList(BukkitUtil.replaceVariables(player, User.getUser(player).getArena(), item.getItemMeta().getLore()), "%votes%", "" + votes)).name(BukkitUtil.replaceVariables(player, User.getUser(player).getArena(), item.getItemMeta().getDisplayName())).build());
        }

        player.openInventory(menu);
    }
}
