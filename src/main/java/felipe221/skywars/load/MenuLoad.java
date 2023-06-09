package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.controller.ArenaController;
import felipe221.skywars.controller.ShopController;
import felipe221.skywars.menus.cage.CageMaterialMenu;
import felipe221.skywars.menus.cage.CageMenu;
import felipe221.skywars.menus.cage.CageTypeMenu;
import felipe221.skywars.menus.lobby.*;
import felipe221.skywars.menus.tops.TopMenu;
import felipe221.skywars.menus.tops.TopStatsSelectorMenu;
import felipe221.skywars.menus.tops.TopTimeSelectorMenu;
import felipe221.skywars.menus.tops.TopTypeSelectorMenu;
import felipe221.skywars.menus.vote.VoteMenu;
import felipe221.skywars.object.*;
import felipe221.skywars.object.cosmetics.*;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class MenuLoad {
    public enum Menus{
        //LOBBY
        SOUNDS("", 0, new HashMap<>(), new HashMap<>()),
        COSMETICS("", 0, new HashMap<>(), new HashMap<>()),
        ARENA_SELECTOR("", 0, new HashMap<>(), new HashMap<>()),
        SOLO("", 0, new HashMap<>(), new HashMap<>()),
        TEAM("", 0, new HashMap<>(), new HashMap<>()),
        ROOMS("", 0, new HashMap<>(), new HashMap<>()),
        //TOPS
        TOPS_TYPE_SELECTOR("", 0, new HashMap<>(), new HashMap<>()),
        TOPS_TIME_SELECTOR("", 0, new HashMap<>(), new HashMap<>()),
        TOPS_STATS_SELECTOR("", 0, new HashMap<>(), new HashMap<>()),
        TOPS_MENU("", 0, new HashMap<>(), new HashMap<>()),
        //SHOP
        SHOP("", 0, new HashMap<>(), new HashMap<>()),
        //STATS
        STATS("", 0, new HashMap<>(), new HashMap<>()),
        //TEMATICAS
        TEMATICAS("", 0, new HashMap<>(), new HashMap<>()),
        //EFFECTS
        WIN_EFFECT("", 0, new HashMap<>(), new HashMap<>()),
        KILL_EFFECT("", 0, new HashMap<>(), new HashMap<>()),
        TRAILS("", 0, new HashMap<>(), new HashMap<>()),
        //CAGES
        CAGE_TYPE("", 0, new HashMap<>(), new HashMap<>()),
        CAGE_MATERIAL("", 0, new HashMap<>(), new HashMap<>()),
        CAGE_MAIN("", 0, new HashMap<>(), new HashMap<>()),
        //ARENA
        KITS("", 0, new HashMap<>(), new HashMap<>()),
        SPECTATOR_TP("", 0, new HashMap<>(), new HashMap<>()),
        TEAMS("", 0, new HashMap<>(), new HashMap<>()),
        //VOTES
        VOTE("", 0, new HashMap<>(), new HashMap<>()),
        SCENARIOS("", 0, new HashMap<>(), new HashMap<>()),
        CHESTS("", 0, new HashMap<>(), new HashMap<>()),
        HEARTS("", 0, new HashMap<>(), new HashMap<>()),
        TIME("", 0, new HashMap<>(), new HashMap<>()),
        PROJECTILES("", 0, new HashMap<>(), new HashMap<>());

        private String title;
        private int rows;
        private HashMap<Integer, ItemStack> items;
        private HashMap<Integer, String> entrys;
        private HashMap<Integer, Object> data;
        private Player player;

        Menus(String title, int rows, HashMap<Integer, ItemStack> items, HashMap<Integer, String> entrys) {
            this.title = title;
            this.rows = rows;
            this.items = items;
            this.entrys = entrys;
            this.player = null;
            this.data = null;
        }

        public String getTitle() {
            return ChatColor.translateAlternateColorCodes('&',title);
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

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public HashMap<Integer, ItemStack> getItemsWithSlot() {
            return items;
        }

        public ArrayList<ItemStack> getItems(){
            return items.values().stream().collect(Collectors.toCollection(ArrayList::new));
        }

        public void setData(int slot, Object object){
            if (this.data == null){
                this.data = new HashMap<>();
            }

            this.data.put(slot, object);
        }

        public Object getData(int slot){
            return this.data.get(slot);
        }

        public HashMap<Integer, String> getEntrys() {
            return entrys;
        }

        public void setEntrys(HashMap<Integer, String> entrys) {
            this.entrys = entrys;
        }

        public void setItems(HashMap<Integer, ItemStack> items) {
            this.items = items;
        }

        public void action(ItemStack itemStack, int slot, ClickType type){
            //is a paged menu, with many items within config
            if (this.data != null){
                for (Map.Entry<Integer, Object> entry : this.data.entrySet()) {
                    int slotEntry = entry.getKey();

                    if (slotEntry == slot) {
                        Object value = entry.getValue();

                        if (this == KITS) {
                            //value data = kit config name
                            Kit kitSelected = KitLoad.getKitPerNameConfig((String) value);

                            if (type == ClickType.LEFT) {
                                if (player.hasPermission("skywars.kit." + value)) {
                                    User.getUser(player).setKit(kitSelected);
                                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', MessagesLoad.TitleLine.KIT_SELECTED.setPlayer(player).getTitle().replaceAll("%kit_name%", kitSelected.getName())),ChatColor.translateAlternateColorCodes('&', MessagesLoad.TitleLine.KIT_SELECTED.setPlayer(player).getSubTitle().replaceAll("%kit_name%", kitSelected.getName())), 10, 50, 10);
                                    player.sendMessage(MessagesLoad.MessagesLine.KIT_SELECTED.setPlayer(this.player).getMessage().replaceAll("%kit_name%", kitSelected.getName()));

                                    this.player.closeInventory();
                                }else{
                                    player.sendMessage(MessagesLoad.MessagesLine.KIT_LOCKED.setPlayer(this.player).getMessage().replaceAll("%kit_name%", kitSelected.getName()));
                                }
                            }else {
                                if (player.hasPermission("skywars.kit." + value)) {
                                    player.sendMessage(MessagesLoad.MessagesLine.ALREADY_HAVE_KIT.setPlayer(this.player).getMessage().replaceAll("%kit_name%", kitSelected.getName()));
                                } else{
                                    this.player.closeInventory();

                                    ShopMenu.open(ShopController.TypeShop.KIT, (String) value, kitSelected.getPrice(), itemStack, this.player);
                                }
                            }
                        }

                        if (this == TEAMS) {
                            //value data = teams id
                            Teams team = User.getUser(player).getArena().getTeamByID(Integer.valueOf((String) value));

                            team.addPlayer(player);

                            this.player.closeInventory();
                        }

                        if (this == TEMATICAS) {
                            //value data = tematica name
                            String tematica = (String) value;

                            if (type == ClickType.LEFT) {
                                if (player.hasPermission("skywars.tematica." + tematica)) {
                                    User.getUser(player).setKillTematica(tematica);
                                    player.sendMessage(MessagesLoad.MessagesLine.TEMATICA_SELECTED.setPlayer(this.player).getMessage().replaceAll("%tematica_name%", (tematica.equals("NONE") ? "Ninguna" : tematica)));

                                    this.player.closeInventory();
                                } else {
                                    player.sendMessage(MessagesLoad.MessagesLine.TEMATICA_LOCKED.setPlayer(this.player).getMessage().replaceAll("%tematica_name%", tematica));
                                }
                            }else{
                                if (player.hasPermission("skywars.tematica." + tematica)) {
                                    player.sendMessage(MessagesLoad.MessagesLine.ALREADY_HAVE_TEMATICA.setPlayer(this.player).getMessage().replaceAll("%tematica_name%", (tematica.equals("NONE") ? "Ninguna" : tematica)));
                                } else{
                                    this.player.closeInventory();

                                    ShopMenu.open(ShopController.TypeShop.TEMATICA, (String) value,  KillsLoad.getPriceForTematica(tematica), itemStack, this.player);
                                }
                            }
                        }

                        if (this == KILL_EFFECT) {
                            //value data = effect name
                            String effect = (String) value;
                            Effect.KillEffect killEffect = Effect.KillEffect.valueOf(effect);

                            if (type == ClickType.LEFT) {
                                if (player.hasPermission("skywars.kill." + effect)) {
                                    User.getUser(player).setKillEffect(killEffect);
                                    player.sendMessage(MessagesLoad.MessagesLine.KILL_EFFECT_SELECTED.setPlayer(this.player).getMessage().replaceAll("%effect_name%", killEffect.getName()));

                                    this.player.closeInventory();
                                } else {
                                    player.sendMessage(MessagesLoad.MessagesLine.KILL_EFFECT_LOCKED.setPlayer(this.player).getMessage().replaceAll("%effect_name%", killEffect.getName()));
                                }
                            }else{
                                if (player.hasPermission("skywars.kill." + effect)) {
                                    player.sendMessage(MessagesLoad.MessagesLine.ALREADY_HAVE_KILL_EFFECT.setPlayer(this.player).getMessage().replaceAll("%effect_name%", killEffect.getName()));
                                } else{
                                    this.player.closeInventory();

                                    ShopMenu.open(ShopController.TypeShop.KILL_EFFECT, (String) value, killEffect.getPrice(), itemStack, this.player);
                                }
                            }
                        }

                        if (this == WIN_EFFECT) {
                            //value data = effect name
                            String effect = (String) value;
                            Effect.WinEffect winEffect = Effect.WinEffect.valueOf(effect);

                            if (type == ClickType.LEFT) {
                                if (player.hasPermission("skywars.win." + effect)) {
                                    User.getUser(player).setWinEffect(winEffect);
                                    player.sendMessage(MessagesLoad.MessagesLine.WIN_EFFECT_SELECTED.setPlayer(this.player).getMessage().replaceAll("%effect_name%", winEffect.getName()));

                                    this.player.closeInventory();
                                } else {
                                    player.sendMessage(MessagesLoad.MessagesLine.WIN_EFFECT_LOCKED.setPlayer(this.player).getMessage().replaceAll("%effect_name%", winEffect.getName()));
                                }
                            }else{
                                if (player.hasPermission("skywars.win." + effect)) {
                                    player.sendMessage(MessagesLoad.MessagesLine.ALREADY_HAVE_WIN_EFFECT.setPlayer(this.player).getMessage().replaceAll("%effect_name%", winEffect.getName()));
                                } else{
                                    this.player.closeInventory();

                                    ShopMenu.open(ShopController.TypeShop.WIN_EFFECT, (String) value, winEffect.getPrice(), itemStack, this.player);
                                }
                            }
                        }

                        if (this == TRAILS) {
                            //value data = effect name
                            String effect = (String) value;
                            Effect.Trail trailEffect = Effect.Trail.valueOf(effect);
                            if (type == ClickType.LEFT) {
                                if (player.hasPermission("skywars.trail." + effect)) {
                                    User.getUser(player).setTrail(trailEffect);
                                    player.sendMessage(MessagesLoad.MessagesLine.TRAIL_EFFECT_SELECTED.setPlayer(this.player).getMessage().replaceAll("%trail_name%", trailEffect.getName()));

                                    this.player.closeInventory();
                                } else {
                                    player.sendMessage(MessagesLoad.MessagesLine.TRAIL_EFFECT_LOCKED.setPlayer(this.player).getMessage().replaceAll("%trail_name%", trailEffect.getName()));
                                }
                            }else{
                                if (player.hasPermission("skywars.trail." + effect)) {
                                    player.sendMessage(MessagesLoad.MessagesLine.ALREADY_HAVE_TRAIL_EFFECT.setPlayer(this.player).getMessage().replaceAll("%effect_name%", trailEffect.getName()));
                                } else{
                                    this.player.closeInventory();

                                    ShopMenu.open(ShopController.TypeShop.TRAIL, (String) value, trailEffect.getPrice(), itemStack, this.player);
                                }
                            }
                        }


                        if (this == CAGE_MATERIAL) {
                            //value data = material id
                            Material material = Material.getMaterial((String) value);

                            if (type == ClickType.LEFT) {
                                if (player.hasPermission("skywars.material." + material.name())) {
                                    User.getUser(player).getCage().setMaterialCage(material);
                                    player.sendMessage(MessagesLoad.MessagesLine.CAGE_MATERIAL_SELECTED.setPlayer(this.player).getMessage());

                                    this.player.closeInventory();
                                } else {
                                    player.sendMessage(MessagesLoad.MessagesLine.CAGE_MATERIAL_LOCKED.setPlayer(this.player).getMessage());
                                }
                            }else{
                                if (player.hasPermission("skywars.material." + material.name())) {
                                    player.sendMessage(MessagesLoad.MessagesLine.ALREADY_HAVE_MATERIAL.setPlayer(this.player).getMessage().replaceAll("%material_name%", material.name()));
                                } else{
                                    this.player.closeInventory();

                                    ShopMenu.open(ShopController.TypeShop.CAGE_MATERIAL,  material.name(), CageLoad.getPriceByMaterial(material), itemStack, this.player);
                                }
                            }
                        }

                        if (this == SOUNDS){
                            Sound sound = Sound.valueOf((String) value);
                            player.playSound(player.getLocation(), sound, 1,1);
                        }

                        if (this == TOPS_MENU){
                            UUID uuidPlayer = UUID.fromString((String) value);
                            this.player.closeInventory();
                            StatsMenu.open(player, uuidPlayer);
                        }

                        if (this == SOLO || this == TEAM || this == ROOMS) {
                            //value data = arena id
                            Arena arena = Arena.getByName((String) value);

                            ArenaController.join(player, arena);

                            this.player.closeInventory();
                        }
                    }
                }
            }else{
                //others menu
                for (Map.Entry<Integer, String> entry : this.entrys.entrySet()) {
                    int slotEntry = entry.getKey();

                    if (slotEntry == slot) {
                        String value = entry.getValue();

                        if (this == ARENA_SELECTOR) {
                            for (Mode.TypeMode modes : Mode.TypeMode.values()) {
                                if (modes.name().equals(value)) {
                                    this.player.closeInventory();
                                    ArenaJoinMenu.open(this.player, modes);

                                    return;
                                }
                            }
                        }

                        if (this == VOTE) {
                            for (Vote.TypeVote votes : Vote.TypeVote.values()) {
                                if (votes.name().equals(value)) {
                                    this.player.closeInventory();
                                    VoteMenu.openVote(player, votes);

                                    return;
                                }
                            }
                        }

                        if (this == SCENARIOS) {
                            for (Scenario.TypeScenario votes : Scenario.TypeScenario.values()) {
                                if (votes.name().equals(value)) {
                                    this.player.closeInventory();
                                    User.getUser(player).getArena().getVoteByEnum(Vote.TypeVote.SCENARIOS).addVote(player, votes.name());

                                    return;
                                }
                            }
                        }

                        if (this == CHESTS) {
                            for (iChest.TypeChest votes : iChest.TypeChest.values()) {
                                if (votes.name().equals(value)) {
                                    this.player.closeInventory();
                                    User.getUser(player).getArena().getVoteByEnum(Vote.TypeVote.CHESTS).addVote(player, votes.name());

                                    return;
                                }
                            }
                        }

                        if (this == PROJECTILES) {
                            for (Projectiles.TypeProjectiles votes : Projectiles.TypeProjectiles.values()) {
                                if (votes.name().equals(value)) {
                                    this.player.closeInventory();
                                    User.getUser(player).getArena().getVoteByEnum(Vote.TypeVote.PROJECTILES).addVote(player, votes.name());

                                    return;
                                }
                            }
                        }

                        if (this == TIME) {
                            for (Time.TypeTime votes : Time.TypeTime.values()) {
                                if (votes.name().equals(value)) {
                                    this.player.closeInventory();
                                    User.getUser(player).getArena().getVoteByEnum(Vote.TypeVote.TIME).addVote(player, votes.name());

                                    return;
                                }
                            }
                        }

                        if (this == HEARTS) {
                            for (Hearts.TypeHearts votes : Hearts.TypeHearts.values()) {
                                if (votes.name().equals(value)) {
                                    this.player.closeInventory();
                                    User.getUser(player).getArena().getVoteByEnum(Vote.TypeVote.HEARTS).addVote(player, votes.name());
                                    return;
                                }
                            }
                        }

                        if (this == TOPS_TYPE_SELECTOR) {
                            for (iStats.TypeStats tops : iStats.TypeStats.values()) {
                                if (tops.name().equals(value)) {
                                    this.player.closeInventory();
                                    User.getUser(this.player).setData("TOP_TYPE", tops.name());

                                    TopTimeSelectorMenu.open(player);

                                    return;
                                }
                            }
                        }

                        if (this == TOPS_TIME_SELECTOR) {
                            for (iStats.TimeStats tops : iStats.TimeStats.values()) {
                                if (tops.name().equals(value)) {
                                    this.player.closeInventory();
                                    User.getUser(this.player).setData("TOP_TIME", tops.name());

                                    TopStatsSelectorMenu.open(player);

                                    return;
                                }
                            }
                        }

                        if (this == TOPS_STATS_SELECTOR) {
                            for (iStats.Stats tops : iStats.Stats.values()) {
                                if (tops.name().equals(value)) {
                                    this.player.closeInventory();
                                    User.getUser(this.player).setData("TOP_STATS", tops.name());

                                    iStats.TypeStats typeStats = iStats.TypeStats.valueOf((String) User.getUser(player).getData("TOP_TYPE"));
                                    iStats.TimeStats timeStats = iStats.TimeStats.valueOf((String) User.getUser(player).getData("TOP_TIME"));
                                    iStats.Stats stats = iStats.Stats.valueOf((String) User.getUser(player).getData("TOP_STATS"));

                                    iTop.StatTop statTop = iTop.getInstance().getStatTop(stats, typeStats, timeStats);

                                    TopMenu.open(this.player, statTop);

                                    return;
                                }
                            }
                        }

                        if (this == CAGE_TYPE) {
                            for (Cage.TypeCage typeCage : Cage.TypeCage.values()) {
                                if (typeCage.name().equalsIgnoreCase(value)) {
                                    if (type == ClickType.LEFT) {
                                        if (player.hasPermission("skywars.type." + typeCage.name())) {
                                            this.player.closeInventory();
                                            User.getUser(player).getCage().setType(typeCage);
                                            player.sendMessage(MessagesLoad.MessagesLine.CAGE_TYPE_SELECTED.setPlayer(this.player).getMessage());

                                        } else {
                                            player.sendMessage(MessagesLoad.MessagesLine.CAGE_TYPE_LOCKED.setPlayer(this.player).getMessage());
                                        }

                                        return;
                                    }else{
                                        if (player.hasPermission("skywars.type." + typeCage.name())) {
                                            player.sendMessage(MessagesLoad.MessagesLine.ALREADY_HAVE_CAGE.setPlayer(this.player).getMessage().replaceAll("%cage_name%", typeCage.getName()));
                                        } else{
                                            this.player.closeInventory();

                                            ShopMenu.open(ShopController.TypeShop.CAGE_TYPE,  typeCage.name(), typeCage.getPrice(), itemStack, this.player);
                                        }
                                    }
                                }
                            }
                        }

                        if (this == CAGE_MAIN) {
                            this.player.closeInventory();

                            if (value.equalsIgnoreCase("TYPE")) {
                                CageTypeMenu.open(player);
                            }else if (value.equalsIgnoreCase("MATERIAL")){
                                CageMaterialMenu.open(player);
                            }

                            return;
                        }

                        if (this == SHOP) {
                            if (value.equalsIgnoreCase("ACEPTAR")) {
                                ShopController.getShop(player).shopItem();
                            }else if (value.equalsIgnoreCase("CANCELAR")){
                                this.player.closeInventory();
                            }

                            return;
                        }
                        if (this == COSMETICS) {
                            this.player.closeInventory();

                            if (value.equalsIgnoreCase("CAJAS")) {
                                CageMenu.open(player);
                            }else if (value.equalsIgnoreCase("STATS")) {
                                StatsMenu.open(player, player.getUniqueId());
                            }else if (value.equalsIgnoreCase("TEMATICA")) {
                                TematicasMenu.open(player);
                            }else if (value.equalsIgnoreCase("WIN_EFFECT")) {
                                WinEffectMenu.open(player);
                            }else if (value.equalsIgnoreCase("KILL_EFFECT")) {
                                KillEffectMenu.open(player);
                            }else if (value.equalsIgnoreCase("TRAILS")) {
                                TrailsMenu.open(player);
                            }else if (value.equalsIgnoreCase("TOPS")){
                                TopTypeSelectorMenu.open(player);
                            }

                            return;
                        }
                    }
                }
            }
        }
    }

    public static void load(){
        for (Menus menu : Menus.values()){
            menu.setTitle(Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Title"));
            menu.setRows(Main.getConfigManager().getConfig("menus.yml").getInt("Menus." + menu.name() + ".Rows"));

            HashMap<Integer, ItemStack> itemsList = new HashMap<>();
            HashMap<Integer, String> entrysList = new HashMap<>();

            if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items")) {
                ConfigurationSection itemsConfig = Main.getConfigManager().getConfig("menus.yml").getConfigurationSection("Menus." + menu.name() + ".Items");

                for (Map.Entry<String, Object> entry : itemsConfig.getValues(false).entrySet()) {
                    int slot = 0;
                    Material material = Material.CARROT;
                    String name = "";
                    List<String> lore = new ArrayList<>();
                    lore.add("");

                    if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items." + entry.getKey() + ".Slot")) {
                        slot = Main.getConfigManager().getConfig("menus.yml").getInt("Menus." + menu.name() + ".Items." + entry.getKey() + ".Slot");
                    }
                    if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items." + entry.getKey() + ".ID")) {
                        material = Material.valueOf(Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Items." + entry.getKey() + ".ID"));
                    }
                    if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items." + entry.getKey() + ".Name")) {
                        name = Main.getConfigManager().getConfig("menus.yml").getString("Menus." + menu.name() + ".Items." + entry.getKey() + ".Name");
                    }
                    if (Main.getConfigManager().getConfig("menus.yml").contains("Menus." + menu.name() + ".Items." + entry.getKey() + ".Lore")) {
                        lore = Main.getConfigManager().getConfig("menus.yml").getStringList("Menus." + menu.name() + ".Items." + entry.getKey() + ".Lore");
                    }
                    itemsList.put(slot, ItemBuilder.start(material).name(name).lore(lore).build());
                    entrysList.put(slot, entry.getKey());
                }
            }

            menu.setEntrys(entrysList);
            menu.setItems(itemsList);
        }
    }
}
