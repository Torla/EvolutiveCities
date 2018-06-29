package Entity.building;

import Entity.Entity;
import Game.World;
import city.City;
import graphics.Tile;
import sun.nio.cs.CharsetMapping;

public class Building extends Entity {

	public Building(World world, City owner, Tile tile, int positionX, int positionY) {
		super(world,owner,tile, positionX, positionY);
	}
}
