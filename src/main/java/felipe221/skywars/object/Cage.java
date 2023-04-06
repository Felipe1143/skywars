package felipe221.skywars.object;

import org.bukkit.Material;

public class Cage {
    public enum TypeCage{
        COMUN,
        ESFERA,
        ARBOL,
    }

    private Material material_cage;
    private TypeCage type;

    public Cage(Material material_cage, TypeCage type) {
        this.material_cage = material_cage;
        this.type = type;
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
}
