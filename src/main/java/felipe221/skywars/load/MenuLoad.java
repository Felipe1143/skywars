package felipe221.skywars.load;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MenuLoad {
    public enum Menus{
        ARENA_SELECTOR("", 0, new HashMap<>());

        private String title;
        private int rows;
        private HashMap<Integer, ItemStack> items;

        Menus(String title, int rows, HashMap<Integer, ItemStack> items) {
            this.title = title;
            this.rows = rows;
            this.items = items;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public HashMap<Integer, ItemStack> getItemsWithSlot() {
            return items;
        }

        public ArrayList<ItemStack> getItems(){
            return items.values().stream().collect(Collectors.toCollection(ArrayList::new));
        }

        public void setItems(HashMap<Integer, ItemStack> items) {
            this.items = items;
        }
    }
}
