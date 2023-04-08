package felipe221.skywars.object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Cage {
    public enum TypeCage{
        COMUN,
        ESFERA,
        ARBOL,
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

    public void create(){
        //comun
        if (this.type == TypeCage.COMUN) {
            try {
                for (int y = 0; y < 4; y++) {
                    if (y > 0) {
                        this.location.add(0.0D, 1.0D, 0.0D);
                    }
                    Block block = this.location.getBlock();
                    if (y == 0 || y == 3) {
                        setTypeBlock(block, this.material_cage);
                    }
                    this.location.add(1.0D, 0.0D, 0.0D);
                    block = this.location.getBlock();
                    setTypeBlock(block, this.material_cage);
                    this.location.add(-2.0D, 0.0D, 0.0D);
                    block = this.location.getBlock();
                    setTypeBlock(block, this.material_cage);
                    this.location.add(1.0D, 0.0D, 1.0D);
                    block = this.location.getBlock();
                    setTypeBlock(block, this.material_cage);
                    this.location.add(0.0D, 0.0D, -2.0D);
                    block = this.location.getBlock();
                    setTypeBlock(block, this.material_cage);
                    this.location.add(0.0D, 0.0D, 1.0D);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
