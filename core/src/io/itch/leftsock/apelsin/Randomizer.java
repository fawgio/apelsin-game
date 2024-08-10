package io.itch.leftsock.apelsin;

import java.util.ArrayList;
import java.util.Random;

public class Randomizer {
    public static Level generate(int seed){
        Level level = new Level();
        Random random = new Random(seed);
        level.setCells(new ArrayList<>());
        for (int x = 0; x <= 12; x++) {
            for (int y = 0; y <= 8; y++) {
                Cell cell = new Cell(x*50,y*50);
                level.getCells().add(cell);
                if(y == 0 || x == 0 || x == 12 || y == 8)
                    cell.setVoid(true);
                else if(random.nextInt(100)<10)
                    cell.setOrange(true);
            }
        }
        level.setTribes(new ArrayList<>());
        int tribes = 2 + random.nextInt(2);
        for (int i = 0; i < tribes; i++) {
            level.getTribes().add(new Tribe("tr"+i,i,level));
        }
        level.userTribe = level.getTribes().get(0);
        level.setUnits(new ArrayList<>());
        int units = (5 + random.nextInt(5)) * 3;
        for (int i = 0; i < tribes; i++)
            for (int x = 0; x < units / 3; x++) {
                level.getUnits().add(new Unit(100 * (1+ i), 100 * (1+ i), level.getTribes().get(i)));
            }
        return level;
    }
}
