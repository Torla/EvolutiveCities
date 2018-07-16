package world.terrain;

import Entity.Entity;
import Entity.pathFinder.PathFinder;
import Entity.pathFinder.Position;
import Entity.unity.Unit;
import city.City;
import graphics.Tile;
import world.World;

import java.util.Set;
import java.util.stream.Collectors;

public class River extends TerrainFeature {

	public River(World world, int x, int y) {
		super(world, x, y, null, 10);
	}

	public Tile getTile() {
		String string = "";
		Set<Position> near = world.getTerrain().stream()
				.filter(a-> a instanceof River || a instanceof Water || a instanceof Lake)
				.map(x->new Position(x.getPositionX(),x.getPositionY()))
				.filter(x-> PathFinder.manhattanDistance(x.x,x.y,getPositionX(),getPositionY())<=1)
				.collect(Collectors.toSet());
		if(near.contains(new Position(getPositionX()-1,getPositionY()))) string+="w";
		if(near.contains(new Position(getPositionX()+1,getPositionY()))) string+="e";
		if(near.contains(new Position(getPositionX(),getPositionY()+1))) string+="s";
		if(near.contains(new Position(getPositionX(),getPositionY()-1))) string+="n";
		switch (string){

			case "n":
			case "s":
				return Tile.RIVER_NS;
			case "w":
			case "e":
				return Tile.RIVER_WE;
			case "":
			case "wesn":
				return Tile.RIVER_CROSS;
			case "we":
				return Tile.RIVER_WE;
			case "ws":
				return Tile.RIVER_WS;
			case "wn":
				return Tile.RIVER_NW;
			case "es":
				return Tile.RIVER_SE;
			case "en":
				return Tile.RIVER_NE;
			case "sn":
				return Tile.RIVER_NS;
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
