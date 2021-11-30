package items;

import items.Item;
import movingEntity.Player;

public class BombRadius extends Item
{

    public BombRadius(int rowIndex, int colIndex) {
        super(colIndex, rowIndex);
    }

    public void addToPlayer(Player player) {
        int currentExplosionRadius = player.getExplosionRadius();
        player.setExplosionRadius(currentExplosionRadius + 1);
    }

    public String getName() {
        final String name = "items.BombRadius";
        return name;
    }
}