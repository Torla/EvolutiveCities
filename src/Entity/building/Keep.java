package Entity.building;

import Game.World;
import city.City;
import graphics.Tile;

public class Keep extends Building {
	public Keep(World world, City owner, int positionX, int positionY) {
		super(world, owner,Tile.KEEP, positionX, positionY);
	}
}
