package items;

import movingEntity.Player;

public class Item
{
    // Constants are static by definition.
    private final static int POWERUP_SIZE = 30;
    private final int x;
    private final int y;
    private String name = null;

    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addToPlayer(Player player) {
    }

    public int getPowerupSize() {
        return POWERUP_SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }
}

