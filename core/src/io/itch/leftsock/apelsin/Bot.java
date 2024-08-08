package io.itch.leftsock.apelsin;

import java.util.ArrayList;

public class Bot {
    public static void move(Tribe tribe){
        for (Unit unit:
             tribe.getUnits()) {
            for (Cell cell:
                    tribe.getLevel().getCells()) {
                if(cell.isOrange() && !(unit.getX() - cell.getX() > 10 && unit.getX() - cell.getX() < 25 && unit.getY() - cell.getY() > 10&& unit.getY() - cell.getY() < 25 )) {
                    unit.moveTo(cell.getX() + 25, cell.getY() + 25);
                    break;
                }
            }
        }
    }
}
