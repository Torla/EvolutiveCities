package Entity.building;

import Game.World;
import graphics.Tile;

public class Keep extends Building {
	public Keep(World world,int positionX, int positionY) {
		super(world, Tile.KEEP, positionX, positionY);
	}
}
