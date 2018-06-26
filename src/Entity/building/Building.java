package Entity.building;

import Entity.Entity;
import Game.World;
import graphics.Tile;
import sun.nio.cs.CharsetMapping;

public class Building extends Entity {

	public Building(World world,Tile tile, int positionX, int positionY) {
		super(world,tile, positionX, positionY);
	}
}
