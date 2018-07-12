package Entity.building;

import world.World;
import city.City;
import graphics.Tile;

public class Field extends Building {
	public Field(World world, City owner, int positionX, int positionY) {
		super(world,owner,Tile.FIELD, positionX, positionY, 0, 50);
	}
}
