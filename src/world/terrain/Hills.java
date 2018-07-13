package world.terrain;

import graphics.Tile;
import world.World;

import java.util.Random;

public class Hills extends TerrainFeature{

	private static Tile[] tiles = {Tile.HILL,Tile.SMALLHILLS};

	public Hills(World world, int x, int y) {
		super(world, x, y,tiles[new Random().nextInt(tiles.length)], 10);
	}
}
