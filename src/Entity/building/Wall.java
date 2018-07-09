package Entity.building;

import Game.World;
import city.City;
import graphics.Tile;

public class Wall extends Building {

	public Wall(World world, City owner, int positionX, int positionY) {
		super(world, owner, Tile.GATE, positionX, positionY, 10, 1000);
	}
}
