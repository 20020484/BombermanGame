package items;

import items.Item;
import movingEntity.Player;

public class BombCounter extends Item
{

    public BombCounter(int rowIndex, int colIndex) {
        super(colIndex, rowIndex);
    }

    public void addToPlayer(Player player) {
        int currentBombCount = player.getBombCount();
        player.setBombCount(currentBombCount + 1);
    }

    public String getName() {
        final String name = "items.BombCounter";
        return name;
    }
}

