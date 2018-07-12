package Entity.pathFinder;

import Entity.Entity;
import city.City;
import javafx.geometry.Pos;

import java.util.*;
import java.util.stream.Collectors;


public class PathFinder {

	private static final int maxDistance = 5;

	static private LinkedList<Position> naiveDirectionToNearest(City owner, int x, int y, Class<? extends Entity> classToFind){
		LinkedList<Position> ret = new LinkedList<>();
		Optional<Position> toOp = owner.getWorld().getEntities().stream()
				.filter(classToFind::isInstance)
				.filter(c->c.getOwner()!=owner)
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

	static public LinkedList<Position> pathToNearestEnemy(City owner, int x, int y,Class<? extends Entity> classToFind){


		if(owner.getWorld().getEntities().stream()
				.filter(a -> manhattanDistance(a.getPositionX(), a.getPositionY(), x, y) < maxDistance)
				.filter(classToFind::isInstance)
				.noneMatch(c->c.getOwner()!=owner)
				) {
		 return naiveDirectionToNearest(owner,x,y,classToFind);
		}


		LinkedList<Position> path = new LinkedList<>();
		Map<Position,Integer> distanceMap = new HashMap<>();
		PriorityQueue<Position> toCheck = new PriorityQueue<>(1000,Comparator.comparingInt(distanceMap::get));
		Set<Position> targets = owner.getWorld().getEntities().stream()
				.filter(classToFind::isInstance)
				.filter(a->a.getOwner()!=owner)
				.filter(a -> manhattanDistance(a.getPositionX(), a.getPositionY(), x, y) <= maxDistance)
				.map(a-> new Position(a.getPositionX(),a.getPositionY()))
				.collect(Collectors.toSet());
		Position nearest=null;


		toCheck.add(new Position(x,y));
		distanceMap.put(new Position(x,y),0);


		while(toCheck.size()!=0){
			Position pos = toCheck.poll();

			if(manhattanDistance(x,y,pos.x,pos.y)>maxDistance+1) continue;

			Position[] toPos = new Position[4];
			toPos[0]= new Position(pos.x+1,pos.y);
			toPos[1]= new Position(pos.x-1,pos.y);
			toPos[2]= new Position(pos.x,pos.y+1);
			toPos[3]= new Position(pos.x,pos.y-1);
			int costTo=0;
			for(Position to:toPos){
				costTo =owner.getWorld().traverseCost(to.x,to.y);
				if(!distanceMap.containsKey(to) || distanceMap.get(to)>distanceMap.get(pos)+costTo){
					distanceMap.put(to,distanceMap.get(pos)+costTo);
					toCheck.add(to);
					if(targets.contains(to)){
						nearest=to;
						toCheck.clear();
						break;
					}
				}
			}
		}

		/*Position nearest = owner.getWorld().getEntities().stream()
				.filter(classToFind::isInstance)
				.filter(c->c.getOwner()!=owner)
				.filter(a -> manhattanDistance(a.getPositionX(), a.getPositionY(), x, y) <= maxDistance)
				.map(a -> new Position(a.getPositionX(), a.getPositionY())).min(Comparator.comparingInt(distanceMap::get)).get();
		*/

		Position pos = nearest;
		while(pos.x!=x || pos.y!=y){
			path.addFirst(pos);
			Position[] toPos = new Position[4];
			toPos[0]= new Position(pos.x+1,pos.y);
			toPos[1]= new Position(pos.x-1,pos.y);
			toPos[2]= new Position(pos.x,pos.y+1);
			toPos[3]= new Position(pos.x,pos.y-1);
			try {
				pos = Arrays.stream(toPos).min(Comparator.comparingInt(a-> distanceMap.getOrDefault(a, 10000))).get();
			}catch (NullPointerException e){
				e.printStackTrace();
			}

		}

		return path;
	}

	public static int manhattanDistance(int x1,int y1,int x2,int y2){
		return Math.abs(x1-x2)+Math.abs(y2-y1);
	}

}

