package items;

import items.Item;
import movingEntity.Player;

public class SpeedUp extends Item
{

    public SpeedUp(int rowIndex, int colIndex) {
        super( colIndex, rowIndex);
    }

    public void addToPlayer(Player player) {
        int speed = (int) player.getPLAYER_PIXELS_BY_STEP();
        player.setPlayerPixelsByStep(speed+2);
    }

    public String getName() {
        final String name = "SpeedUp";
        return name;
    }
}