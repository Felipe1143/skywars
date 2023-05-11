package felipe221.skywars.object.cosmetics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.lang.reflect.Type;

public class Cage {
    public enum TypeCage {
        COMUN("Común", 0),
        ESFERA("Esfera", 0),
        PIRAMIDE("Piramide", 0),
        DOMO("Domo", 0);

        private String name;
        private int price;

        TypeCage(String name, int price) {
            this.price = price;
            this.name = name;
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
    }

    private Material material_cage;
    private TypeCage type;
    private Location location;

    public Cage(Material material_cage, TypeCage type, Location location) {
        this.material_cage = material_cage;
        this.type = type;
        this.location = location;
    }

    public Material getMaterialCage() {
        return material_cage;
    }

    public void setMaterialCage(Material material_cage) {
        this.material_cage = material_cage;
    }

    public TypeCage getType() {
        return type;
    }

    public void setType(TypeCage type) {
        this.type = type;
    }

    private void setTypeBlock(Block block, Material material){
        block.setType(material);
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public void create(){
        Block block = null;
        location.getChunk().setForceLoaded(true);

        if (this.type == TypeCage.COMUN) {
            Block blockUnder = location.getBlock().getLocation().add(0, -1, 0).getBlock();
            blockUnder.setType(this.material_cage);
            Block blockUp = location.getBlock().getLocation().add(0, 3, 0).getBlock();
            blockUp.setType(this.material_cage);

            for (int y = 0; y<3; y++){
                block = location.getBlock().getLocation().add(0, y, -1).getBlock();
                block.setType(this.material_cage);
                block = location.getBlock().getLocation().add(0, y, 1).getBlock();
                block.setType(this.material_cage);
                block = location.getBlock().getLocation().add(1, y, 0).getBlock();
                block.setType(this.material_cage);
                block = location.getBlock().getLocation().add(-1, y, 0).getBlock();
                block.setType(this.material_cage);
            }

            return;
        }

        if (this.type == TypeCage.PIRAMIDE){
            block = location.getBlock().getLocation().add(0, 0, 0).getBlock(); block.setType(this.material_cage); 
            block = location.getBlock().getLocation().add(1, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, -3).getBlock(); block.setType(this.material_cage);

            block = location.getBlock().getLocation().add(1, 1, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 1, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 1, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 1, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 1, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 1, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 1, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 1, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 1, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 1, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 1, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 1, -3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 2, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 2, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 2, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 2, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 2, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 2, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 2, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 2, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 2, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 2, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 2, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 2, -3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 3, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 3, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 3, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 3, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 3, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 3, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 3, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 3, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 4, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 4, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 4, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 4, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 5, 0).getBlock(); block.setType(this.material_cage);
        }
        
        if (this.type == TypeCage.ESFERA){
            block = location.getBlock().getLocation().add(0, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 1, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 1, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 1, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 1, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 1, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 1, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 1, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 1, -1).getBlock(); block.setType(this.material_cage);

            block = location.getBlock().getLocation().add(3, 2, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 2, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 2, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 2, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 2, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 2, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 2, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 2, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 2, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 2, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 2, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 2, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(4, 3, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(4, 3, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 3, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 3, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 3, 4).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 3, 4).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 3, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 3, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 3, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 3, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 3, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 3, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 3, -3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 3, -3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 3, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 3, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 4, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 4, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 4, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 4, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 4, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 4, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 4, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 4, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 4, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 4, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 4, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 4, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 5, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 5, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 5, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 5, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 5, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 5, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 5, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 5, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 6, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 6, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 6, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 6, 1).getBlock(); block.setType(this.material_cage);
        }
        
        if (this.type == TypeCage.DOMO){
            block = location.getBlock().getLocation().add(0, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(4, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(4, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, 4).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, 4).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 0, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 0, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 0, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 0, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 0, -3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 0, -3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 0, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 0, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(4, 1, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(4, 1, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 1, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 1, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 1, 4).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 1, 4).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 1, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 1, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 1, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-3, 1, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 1, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 1, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 1, -3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 1, -3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 1, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 1, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 2, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(3, 2, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 2, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 2, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 2, 3).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 2, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 2, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-2, 2, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 2, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 2, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 2, -2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 2, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 3, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(2, 3, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 3, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 3, 2).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 3, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(-1, 3, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 3, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 3, -1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 4, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 4, 0).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(1, 4, 1).getBlock(); block.setType(this.material_cage);
            block = location.getBlock().getLocation().add(0, 4, 1).getBlock(); block.setType(this.material_cage);
        }
    }

    public void remove(){
        Block block = null;

        if (this.type == TypeCage.COMUN) {
            location.getChunk().setForceLoaded(true);
            Block blockUnder = location.getBlock().getLocation().add(0, -1, 0).getBlock();
            blockUnder.setType(Material.AIR);
            Block blockUp = location.getBlock().getLocation().add(0, 3, 0).getBlock();
            blockUp.setType(Material.AIR);

            for (int y = 0; y<3; y++){
                block = location.getBlock().getLocation().add(0, y, -1).getBlock();
                block.setType(Material.AIR);
                block = location.getBlock().getLocation().add(0, y, 1).getBlock();
                block.setType(Material.AIR);
                block = location.getBlock().getLocation().add(1, y, 0).getBlock();
                block.setType(Material.AIR);
                block = location.getBlock().getLocation().add(-1, y, 0).getBlock();
                block.setType(Material.AIR);
            }
        }

        if (this.type == TypeCage.PIRAMIDE){
            block = location.getBlock().getLocation().add(0, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, -3).getBlock(); block.setType(Material.AIR);

            block = location.getBlock().getLocation().add(1, 1, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 1, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 1, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 1, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 1, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 1, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 1, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 1, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 1, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 1, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 1, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 1, -3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 2, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 2, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 2, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 2, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 2, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 2, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 2, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 2, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 2, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 2, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 2, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 2, -3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 3, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 3, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 3, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 3, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 3, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 3, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 3, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 3, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 4, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 4, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 4, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 4, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 5, 0).getBlock(); block.setType(Material.AIR);
        }

        if (this.type == TypeCage.ESFERA){
            block = location.getBlock().getLocation().add(0, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 1, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 1, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 1, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 1, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 1, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 1, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 1, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 1, -1).getBlock(); block.setType(Material.AIR);

            block = location.getBlock().getLocation().add(3, 2, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 2, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 2, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 2, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 2, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 2, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 2, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 2, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 2, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 2, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 2, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 2, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(4, 3, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(4, 3, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 3, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 3, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 3, 4).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 3, 4).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 3, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 3, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 3, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 3, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 3, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 3, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 3, -3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 3, -3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 3, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 3, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 4, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 4, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 4, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 4, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 4, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 4, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 4, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 4, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 4, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 4, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 4, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 4, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 5, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 5, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 5, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 5, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 5, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 5, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 5, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 5, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 6, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 6, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 6, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 6, 1).getBlock(); block.setType(Material.AIR);
        }

        if (this.type == TypeCage.DOMO){
            block = location.getBlock().getLocation().add(0, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(4, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(4, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, 4).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, 4).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 0, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 0, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 0, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 0, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 0, -3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 0, -3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 0, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 0, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(4, 1, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(4, 1, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 1, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 1, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 1, 4).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 1, 4).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 1, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 1, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 1, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-3, 1, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 1, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 1, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 1, -3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 1, -3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 1, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 1, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 2, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(3, 2, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 2, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 2, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 2, 3).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 2, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 2, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-2, 2, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 2, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 2, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 2, -2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 2, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 3, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(2, 3, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 3, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 3, 2).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 3, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(-1, 3, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 3, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 3, -1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 4, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 4, 0).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(1, 4, 1).getBlock(); block.setType(Material.AIR);
            block = location.getBlock().getLocation().add(0, 4, 1).getBlock(); block.setType(Material.AIR);
        }

    }
}
