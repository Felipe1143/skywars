package felipe221.skywars.object;

import java.util.ArrayList;
import java.util.List;

public class Kills {
    public enum TypeKill{
        BOW("Arco"), SWORD("Espada"), NONE("Ninguno"), VOID("Vacío");

        private String name;

        TypeKill(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private List<String> msgList;
    private TypeKill typeKill;

    public Kills(TypeKill typeKill, List<String> msgList){
        this.msgList = new ArrayList<>();
        this.typeKill = typeKill;

        for (String msg : msgList){
            this.msgList.add(msg);
        }
    }

    public List<String> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<String> msgList) {
        this.msgList = msgList;
    }

    public TypeKill getTypeKill() {
        return typeKill;
    }

    public void setTypeKill(TypeKill typeKill) {
        this.typeKill = typeKill;
    }
}
