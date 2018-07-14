package world.terrain;

import Entity.pathFinder.PathFinder;
import Entity.pathFinder.Position;
import graphics.Tile;
import world.World;

public class Road extends TerrainFeature {
	public Road(World world, int x, int y) {
		super(world, x, y, Tile.ROAD_CROSS,0);
	}

	public static void roadBetween(World world,int x,int y,int x1,int y1){
		for(Position position: PathFinder.shortestPath(world,x,y,x1,y1)){

			new Road(world,position.x,position.y);
		}
	}
}
