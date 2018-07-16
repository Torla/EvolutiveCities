package world.terrain;

import Entity.Entity;
import Entity.unity.Unit;
import city.City;
import graphics.Tile;
import world.World;

public class River extends TerrainFeature {

	public River(World world, int x, int y) {
		super(world, x, y, Tile.RIVER, 10);
	}
}
