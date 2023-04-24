package felipe221.skywars.object;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.VortexEffect;
import de.slikey.effectlib.util.ParticleEffect;
import felipe221.skywars.Main;
import org.bukkit.Location;
import org.bukkit.Material;

public class Effect {
    public enum WinEffect{
        NONE("Ninguno", 0, Material.AIR);

        private String name;
        private int price;
        private Material material;

        WinEffect(String name, int price, Material material) {
            this.name = name;
            this.price = price;
            this.material = material;
        }

        public String getName() {
            return name;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    public enum Trail{
        NONE("Ninguno", 0, Material.AIR);

        private String name;
        private int price;
        private Material material;

        Trail(String name, int price, Material material) {
            this.name = name;
            this.price = price;
            this.material = material;
        }

        public String getName() {
            return name;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    public enum KillEffect{
        NONE("Ninguno", 0, Material.AIR),
        COLD("Nieve", 0, Material.AIR),
        LAVA("Lava", 0, Material.AIR),
        VORTEX("Vortex", 0, Material.AIR),
        CLOUD("Nubes", 0, Material.AIR),
        HEART("Corazones", 0, Material.AIR),
        COOKIE("Cookies", 0, Material.AIR),
        COIN("Monedas", 0, Material.AIR),
        MEAT("Comida", 0, Material.AIR),
        RAINBOW("Arcoiris", 0, Material.AIR),
        RAYO("Rayo", 0, Material.AIR);

        private String name;
        private Location location;
        private int price;
        private Material material;

        KillEffect(String name, int price, Material material) {
            this.name = name;
            this.location = null;
            this.price = price;
            this.material = material;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public KillEffect setLocation(Location location){
            this.location = location;
            return this;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public Location getLocation() {
            return location.add(0, 1, 0);
        }

        public void play(){
            if (this == COLD){
                ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.LIGHT_BLUE_STAINED_GLASS, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 5, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.BLUE_STAINED_GLASS, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 5, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.ICE, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 5, getLocation(), 20.0D);

                ParticleEffect.CLOUD.display(0.0F, 0.0F, 0.0F, 0.1F, 30, getLocation(), 20.0D);

                return;
            }

            if (this == COLD){
                ParticleEffect.LAVA.display(0.5F, 0.5F, 0.5F, 0.1F, 12, getLocation(), 20.0D);

                return;
            }

            if (this == VORTEX){
                VortexEffect effect = new VortexEffect(new EffectManager(Main.getInstance()));
                effect.setLocation(getLocation());
                effect.start();
            }

            if (this == CLOUD){
                ParticleEffect.CLOUD.display(0.0F, 0.0F, 0.0F, 0.1F, 100, getLocation(), 20.0D);
            }

            if (this == HEART){
                ParticleEffect.HEART.display(0.4F, 0.4F, 0.4F, 0.1F, 10, getLocation(), 20.0D);
            }

            if (this == COOKIE){
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.COOKIE, (byte)0), 0.7F, 0.7F, 0.7F, 0.1F, 35, getLocation(), 20.0D);
            }

            if (this == COIN){
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.GOLD_NUGGET, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LEGACY_DOUBLE_PLANT, (byte)0), 0.3F, 0.3F, 0.3F, 0.1F, 2, getLocation(), 20.0D);
            }

            if (this == MEAT){
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.BEEF, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 6, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.CHICKEN, (byte)0), 0.5F, 0.5F, 0.5F, 0.05F, 6, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.PORKCHOP, (byte)0), 0.5F, 0.5F, 0.5F, 0.01F, 6, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.MUTTON, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 6, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.RABBIT, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 6, getLocation(), 20.0D);
            }

            if (this == RAINBOW){
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.RED_WOOL, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 20, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.ORANGE_WOOL, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 20, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.YELLOW_WOOL, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 20, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LIME_WOOL, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 20, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.LIGHT_BLUE_WOOL, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 20, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.BLUE_WOOL, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 20, getLocation(), 20.0D);
                ParticleEffect.ITEM_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.ItemData(Material.PURPLE_WOOL, (byte)0), 0.5F, 0.5F, 0.5F, 0.1F, 20, getLocation(), 20.0D);
            }

            if (this == RAYO){
                ParticleEffect.CLOUD.display(0.3F, 0.3F, 0.3F, 0.1F, 20, getLocation(), 20.0D);
                ParticleEffect.WATER_SPLASH.display(0.3F, 0.4F, 0.3F, 0.1F, 20, getLocation(), 20.0D);
                location.getWorld().strikeLightningEffect(location);
            }
        }
    }
}
