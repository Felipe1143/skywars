package felipe221.skywars.menus;

import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.load.ArenaLoad;
import felipe221.skywars.load.KitLoad;
import felipe221.skywars.object.Mode;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConfigMenu {
    public static void openConfigMenu(Player player){
        Inventory inventory = Bukkit.createInventory(player, 9*3,"Configuración:");

        ItemStack games = ItemBuilder.start(Material.GRASS_BLOCK).name("&aArenas")
                .lore("&7Al entrar aquí podrás configurar cualquier arena",
                        "&7y tambien podrás eliminar o crear una",
                        "&7",
                        "&7Arenas SOLO: &b" + ArenaLoad.getArenasPerMode(Mode.TypeMode.SOLO).size(),
                        "&7Arenas TEAM: &c" + ArenaLoad.getArenasPerMode(Mode.TypeMode.TEAM).size(),
                        "&7Arenas RANKED SOLO: &6" + ArenaLoad.getArenasPerMode(Mode.TypeMode.RANKED_SOLO).size(),
                        "&7Arenas RANKED TEAM: &e" + ArenaLoad.getArenasPerMode(Mode.TypeMode.RANKED_TEAM).size(),
                        "&7Arenas ROOMS: &c" + ArenaLoad.getArenasPerMode(Mode.TypeMode.ROOMS).size()).build();

        ItemStack kits = ItemBuilder.start(Material.BOW).name("&eKits")
                .lore("&7Al entrar aquí podrás configurar cualquier kit",
                        "&7y tambien podrás eliminar o crear uno",
                        "&7",
                        "&7Kits actuales: &e" + KitLoad.getKits().size()).build();

        ItemStack chests = ItemBuilder.start(Material.CHEST).name("&6Cofres")
                .lore("&7Al entrar aquí podrás configurar los contenidos",
                        "&7de los cofres para las partidas").build();

        inventory.setItem(11, games);
        inventory.setItem(13, kits);
        inventory.setItem(15, chests);

        player.openInventory(inventory);
    }
}
