package world.terrain;

import graphics.Tile;
import world.World;

import java.util.Random;

public class Forest extends TerrainFeature {
	private static Tile[] tiles = {Tile.TREE1,Tile.TREE2,Tile.TREE3};
	public Forest(World world, int x, int y) {
		super(world, x, y,tiles[new Random().nextInt(3)] , 3);
	}
}
