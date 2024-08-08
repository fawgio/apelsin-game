package io.itch.leftsock.apelsin;

import java.util.ArrayList;
import java.util.List;

public class Tribe {
    private final String name;
    private final int id;
    private final List<Unit> units = new ArrayList<Unit>();
    private final Level level;
    private int oranges;

    public Tribe(String name, int id, Level level) {
        this.name = name;
        this.id = id;
        this.level = level;
        this.oranges = 10;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public Level getLevel() {
        return level;
    }

    public int getOranges() {
        return oranges;
    }

    public void setOranges(int oranges) {
        this.oranges = oranges;
    }

    public void eatOrange(Unit unit) {
        if (oranges <= 0){
            units.remove(unit);
        } else {
            oranges--;
        }
    }
}
