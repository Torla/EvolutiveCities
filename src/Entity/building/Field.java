package Entity.building;

import Game.World;
import graphics.Tile;

public class Field extends Building {
	public Field(World world, int positionX, int positionY) {
		super(world,Tile.FIELD, positionX, positionY);
	}
}
