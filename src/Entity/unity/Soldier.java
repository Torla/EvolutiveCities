package Entity.unity;

import Entity.Entity;
import Entity.building.Building;
import Game.World;
import city.City;
import graphics.Tile;

public class Soldier extends Unit {
	public Soldier(World world, City owner, int positionX, int positionY) {
		super(world, owner, Tile.SOLDIER, positionX, positionY, 20, 10, 1, Entity.class);
	}
}
