package world.terrain;

import graphics.Tile;
import world.World;

public class Lake extends TerrainFeature{


	private int frame=0;
	private Tile frames[] = {Tile.LAKE,Tile.LAKE1,Tile.LAK2};

	public Lake(World world, int x, int y) {
		super(world, x, y, null, 1000);
	}

	@Override
	public Tile getTile() {
		return frames[frame++%frames.length];
	}
}
