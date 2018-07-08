package Entity.pathFinder;

import Entity.building.Building;
import city.City;
import javafx.geometry.Pos;
import org.omg.CORBA.INTERNAL;

import java.lang.reflect.Array;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathFinder {

	private static final int maxDistance = 3;

	static private LinkedList<Position> naiveDirectionToNearest(City owner, int x, int y){
		LinkedList<Position> ret = new LinkedList<>();
		Optional<Position> toOp = owner.getWorld().getEntities().stream()
				.map(a->new Position(a.getPositionX(),a.getPositionY()))
				.min(Comparator.comparingInt(b->manhattanDistance(b.x,b.y,x,y)));
		if(!toOp.isPresent()) return null;
		Position to = toOp.get();
		if(Math.abs(x-to.x)>Math.abs(y-to.y)){
			ret.add(new Position((x<to.x)?x+1:x-1,y));
		}
		else{
			ret.add(new Position(x,(y<to.y)?y+1:y-1));
		}
		return ret;
	}
	static public LinkedList<Position> pathToNearestEnemy(City owner, int x, int y){


		if(owner.getWorld().getEntities().stream()
				.filter(a -> manhattanDistance(a.getPositionX(), a.getPositionY(), x, y) <= maxDistance)
				.map(a -> new Position(a.getPositionX(), a.getPositionY())).count()==0) {
		 return naiveDirectionToNearest(owner,x,y);
		}

		Map<Position,Integer> cost = owner.getWorld().getEntities().stream()
				.filter(a->a instanceof Building)
				.filter(a->manhattanDistance(a.getPositionX(),a.getPositionY(),x,y)<=maxDistance)
				.map(d->(Building) d)
				.collect(Collectors.toMap(b->new Position(b.getPositionX(),b.getPositionY()), Building::getTraverseCost));


		LinkedList<Position> toCheck = new LinkedList<>();
		LinkedList<Position> path = new LinkedList<>();
		Map<Position,Integer> distanceMap = new HashMap<>();

		toCheck.add(new Position(x,y));
		distanceMap.put(new Position(x,y),0);


		while(toCheck.size()!=0){
			Position pos = toCheck.pop();

			if(manhattanDistance(x,y,pos.x,pos.y)>maxDistance+1) continue;

			Position[] toPos = new Position[4];
			toPos[0]= new Position(pos.x+1,pos.y);
			toPos[1]= new Position(pos.x-1,pos.y);
			toPos[2]= new Position(pos.x,pos.y+1);
			toPos[3]= new Position(pos.x,pos.y-1);
			int costTo;
			for(Position to:toPos){
				costTo=1;
				if(cost.containsKey(to)) costTo+=cost.get(to);
				if(!distanceMap.containsKey(to) || distanceMap.get(to)>distanceMap.get(pos)+costTo){
					toCheck.push(to);
					distanceMap.put(to,distanceMap.get(pos)+costTo);
				}
			}
		}

		Position nearest = owner.getWorld().getEntities().stream()
				.filter(a -> manhattanDistance(a.getPositionX(), a.getPositionY(), x, y) <= maxDistance)
				.map(a -> new Position(a.getPositionX(), a.getPositionY())).min(Comparator.comparingInt(distanceMap::get)).get();


		Position pos = nearest;
		while(pos.x!=x || pos.y!=y){
			path.addFirst(pos);
			Position[] toPos = new Position[4];
			toPos[0]= new Position(pos.x+1,pos.y);
			toPos[1]= new Position(pos.x-1,pos.y);
			toPos[2]= new Position(pos.x,pos.y+1);
			toPos[3]= new Position(pos.x,pos.y-1);
			try {
				pos = Arrays.stream(toPos).min(Comparator.comparingInt(distanceMap::get)).get();
			}catch (NullPointerException e){
				e.printStackTrace();
			}

		}

		return path;
	}

	private static int manhattanDistance(int x1,int y1,int x2,int y2){
		return Math.abs(x1-x2)+Math.abs(y2-y1);
	}

}

