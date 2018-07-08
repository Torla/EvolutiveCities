package Entity.unity;

import Game.World;
import city.City;
import graphics.Tile;

public class Soldier extends Unit {
	public Soldier(World world, City owner, int positionX, int positionY) {
		super(world, owner, Tile.TREE1, positionX, positionY, 20, 10, 1);
	}
}
