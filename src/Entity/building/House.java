package Entity.building;

import Game.World;
import graphics.Tile;

public class House extends Building {

	public House(World world,int positionX, int positionY) {
		super(world,Tile.HOUSE1, positionX, positionY);
	}
}
