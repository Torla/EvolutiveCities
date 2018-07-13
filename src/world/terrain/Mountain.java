package world.terrain;

import graphics.Tile;
import world.World;

import java.util.Random;

public class Mountain extends TerrainFeature {

	private static Tile[] tiles = {Tile.MOUNTAIN1,Tile.MOUNTAIN2,Tile.MOUNTAIN3};

	public Mountain(World world, int x, int y) {
		super(world, x, y, tiles[new Random().nextInt(tiles.length)], 50);
	}
}
