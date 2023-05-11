package felipe221.skywars.controller;

import felipe221.skywars.load.MessagesLoad;
import felipe221.skywars.load.VariblesLoad;
import felipe221.skywars.object.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ShopController {
    private static HashMap<Player, ShopController> shops = new HashMap<>();

    public enum TypeShop{
        TRAIL("skywars.trail."),
        KIT("skywars.kit."),
        TEMATICA("skywars.tematica."),
        KILL_EFFECT("skywars.kill."),
        WIN_EFFECT("skywars.win."),
        CAGE_TYPE("skywars.type."),
        CAGE_MATERIAL("skywars.material.");

        private String permission;

        TypeShop(String permission) {
            this.permission = permission;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }
    }

    private int price;
    private String objectShop;
    private TypeShop typeShop;
    private Player player;

    public ShopController(Player player, int price, String objectShop, TypeShop typeShop) {
        this.player = player;
        this.price = price;
        this.objectShop = objectShop;
        this.typeShop = typeShop;
        shops.put(player, this);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static ShopController getShop(Player player){
        return shops.get(player);
    }

    public static HashMap<Player, ShopController> getShops() {
        return shops;
    }

    public static void setShops(HashMap<Player, ShopController> shops) {
        ShopController.shops = shops;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getObjectShop() {
        return objectShop;
    }

    public void setObjectShop(String objectShop) {
        this.objectShop = objectShop;
    }

    public TypeShop getTypeShop() {
        return typeShop;
    }

    public void setTypeShop(TypeShop typeShop) {
        this.typeShop = typeShop;
    }

    public void shopItem(){
        User user = User.getUser(this.player);
        ShopController controller = shops.get(this.player);

        if (user.getCoins() <= controller.getPrice()){
            player.sendMessage(MessagesLoad.MessagesLine.NOT_HAVE_MONEY.getMessage());

            return;
        }

        user.setCoins(user.getCoins() - controller.getPrice());
        player.sendMessage(MessagesLoad.MessagesLine.PURCHASE_SUCCESSFULL.getMessage());

        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), VariblesLoad.getCommandShop().replaceAll("%player%", player.getName()).replaceAll("%permission%", this.getTypeShop().getPermission() + this.getObjectShop()));
    }
}
