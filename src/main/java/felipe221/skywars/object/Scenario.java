package felipe221.skywars.object;

import java.util.Random;

public class Scenario {
    public enum TypeScenario{
        NORMAL, ANTIKB, LUCKY, SPEED, SCAFFOLD, TORMENTA
    }

    public static TypeScenario getRandomScenario(){
        Random rand = new Random();

        int scenario = rand.nextInt(TypeScenario.values().length);
        return TypeScenario.values()[scenario];
    }

    public static void start(TypeScenario scenario){

    }
}
