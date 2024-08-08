package io.itch.leftsock.apelsin;

public class Cell {
    private int x;
    private int y;
    private boolean visible;
    private boolean orange;
    private int oranges = 10;
    private boolean vVoid;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    public static Cell voidCell(int x, int y) {
        Cell cell = new Cell(x,y);
        cell.setVoid(true);
        return cell;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isOrange() {
        return orange;
    }

    public void setOrange(boolean orange) {
        this.orange = orange;
    }

    public boolean isVoid() {
        return vVoid;
    }

    public void setVoid(boolean vVoid) {
        this.vVoid = vVoid;
    }

    public int getOranges() {
        return oranges;
    }

    public void setOranges(int oranges) {
        this.oranges = oranges;
    }
}
