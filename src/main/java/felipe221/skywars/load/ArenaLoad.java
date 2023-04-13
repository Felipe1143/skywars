package felipe221.skywars.load;

import felipe221.skywars.Main;
import felipe221.skywars.gui.MenuGUI;
import felipe221.skywars.object.Arena;
import felipe221.skywars.object.Mode;
import felipe221.skywars.object.Teams;
import felipe221.skywars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaLoad implements Listener {
	private static HashMap<Player, Arena> editing = new HashMap<>();
	//CREATE, TEAMS, SIZE, NAME
	private static HashMap<Player, Object> creating = new HashMap<>();

	//from config load worlds and arenas
	public static void load() {
		//get id 
		ConfigurationSection config = Main.getConfigManager().getConfig("arenas.yml").getConfigurationSection("Arenas");

		System.out.println("[SkyWars] Arenas cargadas: ");
		for (Map.Entry<String, Object> entry : config.getValues(false).entrySet()) {
			 String id = entry.getKey();
			 
			 Arena arena = new Arena(Integer.parseInt(id));

			 System.out.println("[" + (Integer.parseInt(id) + 1) + "] " +
					 arena.getName() + " [" +
					 arena.getWorld().getName() + "]" +
					 " (" + arena.getMax() + "/" + arena.getMin() + ")");
		}
	}

	public static void fromConfigList(Player player){
		MenuGUI inventory = new MenuGUI("Mapas para editar: ", 4);
		inventory.initConfigMaps();

		//add kit list
		for (Arena arena : Arena.getListArenas()) {
			ItemBuilder build = ItemBuilder.start(Material.GRASS);

			if (arena.getMode() == Mode.TypeMode.RANKED_SOLO){
				build.material(Material.DIAMOND);
			}else if (arena.getMode() == Mode.TypeMode.SOLO){
				build.material(Material.IRON_CHESTPLATE);
			}else if (arena.getMode() == Mode.TypeMode.ROOMS){
				build.material(Material.DRAGON_EGG);
			}else if (arena.getMode() == Mode.TypeMode.TEAM){
				build.material(Material.WHITE_WOOL);
			}else if (arena.getMode() == Mode.TypeMode.RANKED_TEAM){
				build.material(Material.BLAZE_POWDER);
			}

			build.lore("&7",
							"&7Modo: &b" + arena.getMode().name().toUpperCase().replace("_", " "),
							"&7Jugadores minimos: &e" + arena.getMin(),
							"&7Capacidad máxima: &c" + arena.getMax(),
							"&7ID: &6[" + arena.getID() +"]")
					.name("&a&n" + arena.getName());

			inventory.addItem(build.build());
		}

		player.openInventory(inventory.getInventory());
	}

	public void changeModeMenu(Arena arena, Player player){
		Inventory inventory = Bukkit.createInventory(player, 9*3, "Modo " + arena.getName()+":");

		inventory.setItem(11, ItemBuilder.start(Material.IRON_CHESTPLATE).name("&aModo SOLO").lore("&7", "&7¡Haz click para seleciconar!").build());
		inventory.setItem(12, ItemBuilder.start(Material.DIAMOND).name("&aModo RANKED SOLO").lore("&7", "&7¡Haz click para seleciconar!").build());
		inventory.setItem(13, ItemBuilder.start(Material.DRAGON_EGG).name("&aModo ROOMS").lore("&7", "&7¡Haz click para seleciconar!").build());
		inventory.setItem(14, ItemBuilder.start(Material.WHITE_WOOL).name("&aModo TEAM").lore("&7", "&7¡Haz click para seleciconar!").build());
		inventory.setItem(15, ItemBuilder.start(Material.BLAZE_POWDER).name("&aModo RANKED TEAM").lore("&7", "&7¡Haz click para seleciconar!").build());
		inventory.setItem(18, ItemBuilder.start(Material.SLIME_BALL).name("&aVolver al menú").build());

		player.openInventory(inventory);
	}

	public static void fromConfig(Player player, Arena arena) {
		Inventory inventory = null;

		if (arena.isSoloGame()){
			inventory = Bukkit.createInventory(player, 9 * 3, arena.getName());

			inventory.setItem(26, ItemBuilder.start(Material.BARRIER).name("&c&lELIMINAR ARENA").build());
			inventory.setItem(18, ItemBuilder.start(Material.SLIME_BALL).name("&aVolver al menú de arenas").build());
		}else{
			inventory = Bukkit.createInventory(player, 9 * 4, arena.getName());

			inventory.setItem(22, ItemBuilder.start(Material.WHITE_WOOL).name("&3Cambiar tamaño de equipo")
					.lore("&7Cambia la cantidad de jugadores por",
							"&7equipo dentro de la arena. Debe ser",
							"&7divisible por el número máximo de jugadores"

					).build());

			inventory.setItem(35, ItemBuilder.start(Material.BARRIER).name("&c&lELIMINAR ARENA").build());
			inventory.setItem(27, ItemBuilder.start(Material.SLIME_BALL).name("&aVolver al menú de arenas").build());
		}

		editing.put(player, arena);

		inventory.setItem(10,ItemBuilder.start(Material.NAME_TAG).name("&aCambiar nombre")
				.lore("&7¡Selecciona un nuevo nombre para la arena!",
						"&7",
						"&7Nombre actual: &a" + arena.getName()).build());

		inventory.setItem(11, ItemBuilder.start(Material.GRASS_BLOCK).name("&eCambiar mundo")
				.lore("&7Cambia el mundo donde se desarrollará la ",
						"&7partida, o crea uno desde 0 (vacío)",
						"&7",
						"&7Mundo actual: &e" + arena.getWorld().getName()).build());

		inventory.setItem(12, ItemBuilder.start(Material.DRAGON_EGG).name("&bCambiar modo")
				.lore("&7¡Cambia el modo de juego de la arena!",
						"&7",
						"&7Modo actual: &b" + arena.getMode().name().toUpperCase().replace("_", " ")).build());

		inventory.setItem(13, ItemBuilder.start(Material.ARROW).name("&6Cambiar jugadores máximos")
				.lore("&7Cambia la cantidad de jugadores",
						"&7que pueden ingresar a la arena",
						"&7",
						"&7Cantidad actual: &6" + arena.getMax()).build());

		inventory.setItem(14, ItemBuilder.start(Material.ARROW).name("&dCambiar jugadores minimos")
				.lore("&7Cambia la cantidad de jugadores",
						"&7que se necesitan para comenzar",
						"&7",
						"&7Cantidad actual: &d" + arena.getMin()).build());

		inventory.setItem(15, ItemBuilder.start(Material.CLOCK).name("&cCambiar tiempo de comienzo")
				.lore("&7Cambia el tiempo en que tardará",
						"&7la arena en comenzar",
						"&7",
						"&7Tiempo actual: &c" + arena.getTime() + " segundos").build());

		inventory.setItem(16, ItemBuilder.start(Material.GLASS).name("&cCambiar/agregar spawns")
				.lore("&7Cambia la ubicación de los spawns",
						"&7donde aparecerán los juagdores al",
						"&7ingresar a la arena").build());

		player.openInventory(inventory);
	}

	public void sendToConfig(Player player, Arena arena){
		int id = arena.getID();

		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Name", arena.getName());
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".World-Name", arena.getWorld().getName());

		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Mode", arena.getMode().name());
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Max-Players", arena.getMax());
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Min-Players", arena.getMin());
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Spawns", arena.getSpawns().keySet());
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Time-To-Start", arena.getTime());
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Teams", arena.getTeams());
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Team-Size", arena.getTeamSize());

		Main.getConfigManager().save("arenas.yml");

		System.out.println("[Debug - SkyWars] El mapa " + arena.getName() + "[" + id + "]" + " fue actualizado correctamente");

		editing.remove(player);
		creating.remove(player);

		player.sendMessage(ChatColor.GREEN + "¡Configuración cargada correctamente!");
	}

	public void remove(Player player, Arena arena){
		int id = arena.getID();

		Arena.getListArenas().remove(arena);
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id, null);

		Main.getConfigManager().save("arenas.yml");

		System.out.println("[Debug - SkyWars] El mapa " + arena.getName() + "[" + id + "]" + " fue eliminado correctamente");

		editing.remove(player);
		creating.remove(player);

		player.sendMessage(ChatColor.RED + "¡Mapa eliminado correctamente!");
	}

	public void createConfig(String name, String world, int id){
		List<String> spawns = new ArrayList<>();
		List<Teams> teams = new ArrayList<>();

		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Name", name);
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".World-Name", world);

		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Mode", "SOLO");
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Max-Players", 10);
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Min-Players", 5);
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Spawns", spawns);
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Time-To-Start", 10);
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Teams", teams);
		Main.getConfigManager().getConfig("arenas.yml").set("Arenas." + id + ".Team-Size", 0);

		Main.getConfigManager().save("arenas.yml");

		System.out.println("[Debug - SkyWars] La creacion del mapa " + name + " fue actualizada correctamente");
	}

	//kit create on chat
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerChat(PlayerChatEvent e){
		Player player = e.getPlayer();
		String msg = e.getMessage();

		if (!creating.containsKey(player)){
			return;
		}

		if (getTypeCreating(player).equals("MAX")){
			Arena arena = editing.get(player);

			if (!isNumeric(msg)){
				player.sendMessage(ChatColor.RED + "¡Porfavor coloque un valor númerico!");
				player.sendMessage(ChatColor.RED + "Vuelva a escribir la cantidad máxima en el chat");
			}else{
				creating.remove(player);
				arena.setMax(Integer.parseInt(msg));

				player.sendMessage(ChatColor.GREEN + "¡Jugadores máximos cambiados correctamente!" );

				new BukkitRunnable() {
					@Override
					public void run() {
						fromConfig(player, arena);
					}
				}.runTaskLater(Main.getInstance(), 2);

			}
			return;
		}

		if (getTypeCreating(player).equals("NAME")){
			Arena arena = editing.get(player);

			if (isNumeric(msg)){
				player.sendMessage(ChatColor.RED + "El nombre del mapa no puede ser un número");
				player.sendMessage(ChatColor.RED + "Vuelva a escribir el nombre de la arena en el chat");
			}else{
				if (isNameTaken(msg)){
					player.sendMessage(ChatColor.RED + "¡El nombre " + ChatColor.UNDERLINE + msg + ChatColor.RED + " ya está en uso!");
					player.sendMessage(ChatColor.RED + "Porfavor, vuelva a escribir el nombre de la arena");
				}else{
					creating.remove(player);
					arena.setName(arena.getName());

					player.sendMessage(ChatColor.GREEN + "¡Nombre cambiado correctamente!" );

					new BukkitRunnable() {
						@Override
						public void run() {
							fromConfig(player, arena);
						}
					}.runTaskLater(Main.getInstance(), 2);

				}
			}
			return;
		}

		if (getTypeCreating(player).equals("NAME")){
			Arena arena = editing.get(player);

			if (isNumeric(msg)){
				player.sendMessage(ChatColor.RED + "El nombre del mapa no puede ser un número");
				player.sendMessage(ChatColor.RED + "Vuelva a escribir el nombre de la arena en el chat");
			}else{
				if (isNameTaken(msg)){
					player.sendMessage(ChatColor.RED + "¡El nombre " + ChatColor.UNDERLINE + msg + ChatColor.RED + " ya está en uso!");
					player.sendMessage(ChatColor.RED + "Porfavor, vuelva a escribir el nombre de la arena");
				}else{
					creating.remove(player);
					arena.setName(arena.getName());

					player.sendMessage(ChatColor.GREEN + "¡Nombre cambiado correctamente!" );

					new BukkitRunnable() {
						@Override
						public void run() {
							fromConfig(player, arena);
						}
					}.runTaskLater(Main.getInstance(), 2);

				}
			}
			return;
		}

		if (getTypeCreating(player).equals("WORLD")){
			Arena arena = editing.get(player);

			if (isNumeric(msg)){
				player.sendMessage(ChatColor.RED + "El nombre del mundo no puede ser un número");
				player.sendMessage(ChatColor.RED + "Vuelva a escribir el nombre del mundo en el chat");
			}else{
				creating.remove(player);
				
				if (Bukkit.getWorld(msg) == null){

				}
				arena.setWorld();

				player.sendMessage(ChatColor.GREEN + "¡Mundo cambiado correctamente!" );

				new BukkitRunnable() {
					@Override
					public void run() {
						fromConfig(player, arena);
					}
				}.runTaskLater(Main.getInstance(), 2);

			}
			return;
		}



		//TODO all anothers configs, max, min, teams, etc
		if (getTypeCreating(player).equals("CREATE")){
			if (isNumeric(msg)){
				player.sendMessage(ChatColor.RED + "El nombre de la arena no puede ser un número");
				player.sendMessage(ChatColor.RED + "Porfavor, vuelva a escribir el nombre de la arena");
			}else{
				if (!isNameTaken((msg))) {
					createConfig(e.getMessage(), player.getWorld().getName(), Arena.getListArenas().size());
					Arena newArena = new Arena(Arena.getListArenas().size());

					editing.put(player, newArena);
					creating.remove(player);

					player.sendMessage(ChatColor.GREEN + "¡Arena " + msg + " creada correctamente!");
					player.sendMessage(ChatColor.GREEN + "Abriendo configuración...");

					new BukkitRunnable() {
						@Override
						public void run() {
							fromConfig(player, newArena);
						}
					}.runTaskLater(Main.getInstance(), 2);
				}else{
					player.sendMessage(ChatColor.RED + "¡El nombre " + ChatColor.UNDERLINE + msg + ChatColor.RED + " ya está en uso!");
					player.sendMessage(ChatColor.RED + "Porfavor, vuelva a escribir el nombre de la arena");
				}
			}
		}
	}

	//prevent move panels
	@EventHandler
	public void onPlayerClickInventory(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (player == null) {
			return;
		}

		if (!editing.containsKey(player)) {
			return;
		}

		if (e.getCurrentItem() == null) {
			return;
		}

		if (e.getCurrentItem().getType() == Material.AIR) {
			return;
		}

		Inventory inv = e.getInventory();
		Player edit = null;

		for (Player players : editing.keySet()) {
			edit = players;
		}

		Arena arena = editing.get(player);

		e.setCancelled(true);

		//main menu
		if (e.getView().getTitle().equals(arena.getName())){
			if (e.getCurrentItem().getType() == Material.DRAGON_EGG){
				creating.put(player, "MODE");
				player.closeInventory();
				changeModeMenu(arena, player);

				return;
			}

			if (e.getSlot() == 13){
				creating.put(player, "MAX");
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN + "Ingresa la cantidad de jugadores máximos para la arena: ");
				return;
			}

			if (e.getSlot() == 14){
				creating.put(player, "MIN");
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN + "Ingresa la cantidad de jugadores minimos para la arena: ");

				return;
			}

			if (e.getCurrentItem().getType() == Material.GRASS_BLOCK){
				creating.put(player, "WORLD");
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN + "Ingresa el nombre del mundo para la arena: ");

				return;
			}

			if (e.getCurrentItem().getType() == Material.BARRIER){
				if ((Arena.getListArenas().size() - 1) == 0){
					player.sendMessage(ChatColor.RED + "¡No puedes eliminar la última arena!");
					player.sendMessage(ChatColor.RED + "Primero crea otra y luego procede a eliminarla");

				}else{
					remove(player, arena);
					player.closeInventory();
					fromConfigList(player);
				}
			}

			if (e.getCurrentItem().getType() == Material.SLIME_BALL){
				player.closeInventory();
				fromConfigList(player);
			}
		}

		//editing mode
		if (e.getView().getTitle().equals("Modo " + arena.getName() + ":")){
			if (e.getCurrentItem().getType() == Material.DIAMOND){
				arena.setMode(Mode.TypeMode.RANKED_SOLO);
			}else if (e.getCurrentItem().getType() == Material.IRON_CHESTPLATE){
				arena.setMode(Mode.TypeMode.SOLO);
			}else if (e.getCurrentItem().getType() == Material.DRAGON_EGG){
				arena.setMode(Mode.TypeMode.ROOMS);
			}else if (e.getCurrentItem().getType() == Material.WHITE_WOOL){
				arena.setMode(Mode.TypeMode.TEAM);

				if (arena.getTeamSize() == 0){
					arena.setTeamSize(1);
				}
			}else if (e.getCurrentItem().getType() == Material.BLAZE_POWDER){
				arena.setMode(Mode.TypeMode.RANKED_TEAM);

				if (arena.getTeamSize() == 0){
					arena.setTeamSize(1);
				}
			}

			player.closeInventory();
			creating.remove(player);

			if (e.getCurrentItem().getType() != Material.SLIME_BALL) {
				player.sendMessage(ChatColor.GREEN + "¡Modo cambiado correctamente!");
			}

			new BukkitRunnable() {
				@Override
				public void run() {
					fromConfig(player, arena);
				}
			}.runTaskLater(Main.getInstance(), 2);
		}
	}

	@EventHandler
	public void onPlayerCloseInventory(InventoryCloseEvent e) {
		if (editing.isEmpty()) {
			return;
		}

		Player player = (Player) e.getPlayer();
		Inventory inv = e.getInventory();
		Player edit = null;

		for (Player players : editing.keySet()) {
			edit = players;
		}

		Arena arena = editing.get(player);

		//prevent send to config on editing price, name...
		if (edit.getUniqueId() == player.getUniqueId() && !creating.containsKey(player)){
			sendToConfig(player, arena);
		}
	}

	public static ArrayList<Arena> getArenasPerMode(Mode.TypeMode mode){
		ArrayList<Arena> arenas = new ArrayList<>();

		for (Arena arena : Arena.getListArenas()){
			if (arena.getMode() == mode){
				arenas.add(arena);
			}
		}

		return arenas;
	}

	public boolean isNameTaken(String arenaName){
		boolean taken = false;

		for (Arena arenas : Arena.getListArenas()){
			if (arenas.getName().equalsIgnoreCase(arenaName)){
				taken = true;
				break;
			}
		}

		return taken;
	}

	public boolean isWorldTaken(World world){
		boolean taken = false;

		for (Arena arenas : Arena.getListArenas()){
			if (arenas.getWorld().getName().equalsIgnoreCase(world.getName())){
				taken = true;
				break;
			}
		}

		return taken;
	}

	public static HashMap<Player, Arena> getEditing() {
		return editing;
	}

	public static boolean isEditing(Player player){
		return editing.containsKey(player);
	}

	public static void removeEditing(Player player){
		if (editing.containsKey(player)){
			editing.remove(player);
		}
	}

	public static void removeCreating(Player player){
		if (creating.containsKey(player)){
			creating.remove(player);
		}
	}

	public static void addEditing(Player player, Arena arena) {
		editing.put(player, arena);
	}


	public static Object getTypeCreating(Player player) {
		return creating.get(player);
	}

	public static boolean isCreating(Player player) {
		return creating.containsKey(player);
	}

	public static void setTypeCreating(Player player, Object type) {
		creating.put(player, type);
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
