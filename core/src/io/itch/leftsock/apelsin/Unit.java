package io.itch.leftsock.apelsin;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Unit {
    private int x;
    private int y;
    private Tribe tribe;
    private Random random = new Random();
    private float nextOrange;
    private float speed = 1f;
    private int savedX;
    private int savedY;
    private State state = State.FREE;
    private Vector2 task;
    private float eatOrange;

    public Unit(int x, int y, Tribe tribe) {
        this.x = x;
        this.y = y;
        this.tribe = tribe;
        tribe.getUnits().add(this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tribe getTribe() {
        return tribe;
    }

    public void setTribe(Tribe tribe) {
        this.tribe = tribe;
    }

    public void update(float delta){
        if(nextOrange >= 0) {
            nextOrange -= delta;
            if (nextOrange <= 3 && state == State.ORANGES)
                state = State.FREE;
        }
        if(eatOrange >= 0){
            eatOrange -= delta;
            if (nextOrange >= 1 && state == State.EATING)
                state = State.FREE;
        }

        for (Unit unit :
                tribe.getLevel().getUnits()) {
            if(unit.equals(this)) continue;
            int r = unit.tribe.equals(this.tribe) ? 12 : 36;
            if (Math.abs(unit.getY() - y) < r && Math.abs(unit.getX() - x) < r) {
                if(state != State.AVOIDING)
                    instantMoveTo(x + (unit.getX() < x ? r : 0) - random.nextInt(r), y + (unit.getY() < y ? r : 0) - random.nextInt(r));
                break;
            }
        }

        for (Cell cell:
            tribe.getLevel().getCells()) {
            if (x - cell.getX() < 50 && x - cell.getX() > 0 && y - cell.getY() < 50 && y - cell.getY() > 0){
                cell.setVisible(true);

                if (cell.isOrange() && nextOrange <= 0) {
                    if(cell.getOranges() > 0) {
                        tribe.setOranges(tribe.getOranges() + 1);
                        nextOrange = 5 + random.nextInt(5);
                        state = State.ORANGES;
                        cell.setOranges(cell.getOranges()-1);
                    } else {
                        cell.setOrange(false);
                    }
                }
                break;
            }
        }


        if (eatOrange <= 0) {
            state = State.EATING;
            tribe.eatOrange(this);
            eatOrange = 10 + random.nextInt(5);
        }

        if (state != State.FREE || task == null) return;

        targetX = (int) task.x;
        targetY = (int) task.y;
        state = State.MOVING;
    }

    private void instantMoveTo(int x, int y) {
        targetX = x;
        targetY = y;
        state = State.AVOIDING;
    }

    int targetX;
    int targetY;

    public void moveTo(int x, int y) {
        task = new Vector2(x,y);
    }

    public void continueMove(){
        for (Cell cell:
                tribe.getLevel().getCells()) {
            if (cell.isVoid()&&((x + (int) (speed * (targetX - x < 0? -1 : 1))) - cell.getX() < 50 && (x + 12 + (int) (speed * (targetX - x < 0? -1 : 1))) - cell.getX() > 0 && (y + (int) (speed * (targetY - y < 0? -1 : 1))) - cell.getY() < 50 && (y + 12 + (int) (speed * (targetY - y < 0? -1 : 1))) - cell.getY() > 0)){
                stopMove();
                return;
            }
        }

        if (x!=targetX)
            x += (int) (speed * (targetX - x < 0? -1 : 1));
        if (y!=targetY)
            y += (int) (speed * (targetY - y < 0? -1 : 1));

        if(getX() == targetX &&
                getY() == targetY)
            stopMove();
    }

    public void stopMove(){
        //task = null;
        state = State.FREE;
    }

    public State getState() {
        return state;
    }
}
