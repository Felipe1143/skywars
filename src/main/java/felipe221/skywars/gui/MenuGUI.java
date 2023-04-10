package felipe221.skywars.gui;

import felipe221.skywars.load.KitLoad;
import felipe221.skywars.object.Kit;
import felipe221.skywars.util.BukkitUtil;
import felipe221.skywars.util.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MenuGUI implements InventoryHolder, Listener {
    private Map<Integer, ItemStack> items;
    private int rows;
    private int currentPage;
    private String name;

    private boolean configKit;

    public MenuGUI(){
        //to listener
    }

    public MenuGUI(String name, int rows){
        items = new HashMap<>();
        currentPage = 0;
        if (rows > 5){
            this.rows = 5;
        }else {
            this.rows = rows;
        }
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.configKit = false;
    }

    public void initConfigKit(){
        this.configKit = true;
    }

    public void setDisplayName(String name){
        this.name = ChatColor.translateAlternateColorCodes('&', name);
    }

    public String getDisplayName(){
        return name;
    }

    public void addItem(ItemStack item){
        int slot = 0;

        if(!items.isEmpty()) {
            // Find the highest slot
            int highestSlot = -1;
            for(int itemSlot : items.keySet()){
                if(itemSlot > highestSlot){
                    highestSlot = itemSlot;
                }
            }

            // Set the target slot to one higher than the highest slot.
            slot = highestSlot + 1;
        }

        // Put the button in that slot.
        items.put(slot, item);
    }

    public void setButton(int slot, ItemStack item){
        items.put(slot, item);
    }

    public void removeButton(int slot){
        items.remove(slot);
    }

    public ItemStack getButton(int slot){
        return items.get(slot);
    }

    public boolean nextPage(){
        if(currentPage < getFinalPage()){
            currentPage++;
            return true;
        }else{
            return false;
        }
    }

    public boolean previousPage(){
        if(currentPage > 0) {
            currentPage--;
            return true;
        }else{
            return false;
        }
    }

    public int getMaxPage(){
        return getFinalPage();
    }

    public int getFinalPage(){
        // Get the highest slot number.
        int slot = 0;
        for(int nextSlot : items.keySet()){
            if(nextSlot > slot){
                slot = nextSlot;
            }
        }

        // Add one to make the math easier.
        double highestSlot = slot + 1;

        return (int) Math.ceil(highestSlot / (double) (rows*9)) - 1;
    }

    public int getBackButtonSlot(){
        if ((rows+1)*9 == 54){
            return 48;
        }

        if ((rows+1)*9 == 45){
            return 39;
        }

        if ((rows+1)*9 == 36){
            return 30;
        }

        if ((rows+1)*9 == 27){
            return 21;
        }

        return 0;
    }

    public void refreshInventory(HumanEntity holder){
        holder.closeInventory();
        holder.openInventory(getInventory());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() != null
                && event.getInventory().getHolder() instanceof MenuGUI) {
            ItemStack button = event.getCurrentItem();

            if (button != null) {
                event.setCancelled(true);
                MenuGUI menu = (MenuGUI) event.getClickedInventory().getHolder();

                //next page
                if (button.getItemMeta().getDisplayName().contains("siguiente")) {
                    if (!menu.nextPage()) {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "¡Estás en la ultima página!");

                        return;
                    }

                    menu.refreshInventory(event.getWhoClicked());
                }

                //back page
                if (button.getItemMeta().getDisplayName().contains("anterior")) {
                    if (!menu.previousPage()) {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "¡Estás en la primera página!");

                        return;
                    }

                    menu.refreshInventory(event.getWhoClicked());
                }

                if (menu.configKit){
                    if (button.getType() == Material.NAME_TAG){
                        return;
                    }

                    Kit openKit = null;

                    for (Kit kit : KitLoad.getKits()){
                        System.out.println(BukkitUtil.stripcolor(button.getItemMeta().getDisplayName()));
                        if (BukkitUtil.stripcolor(button.getItemMeta().getDisplayName()).equals(kit.getName())){
                            openKit = kit;
                            System.out.println("RECONOCE KIT");

                            break;
                        }
                    }

                    if (openKit != null){
                        event.getWhoClicked().closeInventory();
                        KitLoad.fromConfig((Player) event.getWhoClicked(), openKit);
                    }
                }
            }
        }
    }

    @Override
    public Inventory getInventory() {
        int countrows= (rows+1)*9;
        Inventory inventory = Bukkit.createInventory(this, countrows, name);

        /* BEGIN PAGINATION */
        ItemStack backButton = ItemBuilder.start(Material.ARROW).name("&cPágina anterior").build();
        ItemStack pageIndicator = ItemBuilder.start(Material.NAME_TAG).name("&6Página " + (currentPage +1) + "/" + (getFinalPage() +1)).build();
        ItemStack nextButton = ItemBuilder.start(Material.ARROW).name("&aPágina siguiente").build();
        /* END PAGINATION */

        //buttons
        inventory.setItem(getBackButtonSlot(), backButton);
        inventory.setItem(getBackButtonSlot() + 1, pageIndicator);
        inventory.setItem(getBackButtonSlot() + 2, nextButton);

        // Add the main inventory items
        int counter = 0;
        for(int key = (currentPage * (rows*9)); key <= Collections.max(items.keySet()); key++){
            if(counter >= (rows*9)) {
                break;
            }

            if(items.containsKey(key)) {
                inventory.setItem(counter, items.get(key));
            }

            counter++;
        }

        return inventory;
    }
}
