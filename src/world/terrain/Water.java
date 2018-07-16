package world.terrain;

import graphics.Tile;
import world.World;


public class Water extends TerrainFeature {

	private int frame=0;
	private Tile frames[] = {Tile.WATER0,Tile.WATER1,Tile.WATER2,Tile.WATER3};

	public Water(World world, int x, int y) {
		super(world, x, y, null,10000);
	}

	public Tile getTile() {
		return frames[frame++%frames.length];
	}
}
