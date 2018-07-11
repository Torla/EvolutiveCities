package Entity.unity;

import Entity.Entity;
import Game.World;
import city.City;
import graphics.Tile;

public class Knight extends Unit{
	public Knight(World world, City owner, Tile tile, int positionX, int positionY) {
		super(world, owner, tile, positionX, positionY, 1, 10, 5, Unit.class);
	}
}
