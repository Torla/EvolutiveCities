package Entity.unity;

import world.World;
import city.City;
import graphics.Tile;

public class Knight extends Unit{
	public Knight(World world, City owner, int positionX, int positionY) {
		super(world, owner, Tile.KNIGHT, positionX, positionY, 20, 19, 5, Soldier.class);
	}
}
