package world.terrain;

import Entity.pathFinder.PathFinder;

import Entity.pathFinder.Position;
import graphics.Tile;
import world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Road extends TerrainFeature {

	public Road(World world, int x, int y) {
		super(world, x, y,null,0);
	}

	public static void roadBetween(World world,int x,int y,int x1,int y1){
		for(Position position: PathFinder.shortestPath(world,x,y,x1,y1)){
			new Road(world,position.x,position.y);
		}
	}

	@Override
	public Tile getTile() {
		String string = "";
		Set<Position> near = world.getTerrain().stream()
				.filter(Road.class::isInstance)
				.map(x->new Position(x.getPositionX(),x.getPositionY()))
				.filter(x->PathFinder.manhattanDistance(x.x,x.y,getPositionX(),getPositionY())<=1)
				.collect(Collectors.toSet());
		if(near.contains(new Position(getPositionX()-1,getPositionY()))) string+="w";
		if(near.contains(new Position(getPositionX()+1,getPositionY()))) string+="e";
		if(near.contains(new Position(getPositionX(),getPositionY()+1))) string+="s";
		if(near.contains(new Position(getPositionX(),getPositionY()-1))) string+="n";
		switch (string){

			case "n":
			case "s":
				return Tile.ROAD_NS;
			case "w":
			case "e":
				return Tile.ROAD_WE;
			case "":
			case "wesn":
				return Tile.ROAD_CROSS;
			case "we":
				return Tile.ROAD_WE;
			case "ws":
				return Tile.ROAD_WS;
			case "wn":
				return Tile.ROAD_NW;
			case "es":
				return Tile.ROAD_SE;
			case "en":
				return Tile.ROAD_NE;
			case "sn":
				return Tile.ROAD_NS;
			case "wes":
				return Tile.ROAD_WSE;
			case "wen":
				return Tile.ROAD_WNE;
			case "wsn":
				return Tile.ROAD_NWS;
			case "esn":
				return Tile.ROAD_NES;
			default:
				return Tile.a25;
		}
	}
}
