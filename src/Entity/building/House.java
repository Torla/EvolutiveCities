package Entity.building;

import world.World;
import city.City;
import graphics.Tile;

public class House extends Building {

	public House(World world, City owner, int positionX, int positionY) {
		super(world,owner,Tile.HOUSE1, positionX, positionY, 1, 100);
	}
}
