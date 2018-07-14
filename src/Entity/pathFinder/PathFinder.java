package Entity.pathFinder;

import Entity.Entity;
import city.City;
import javafx.geometry.Pos;
import world.World;
import world.terrain.Lake;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class PathFinder {

	private static final int maxDistance = 5;
	private static final double heuristicRate=30;
	private static final Random rng = ThreadLocalRandom.current();

	static private LinkedList<Position> naiveDirectionToNearest(City owner, int x, int y, Class<? extends Entity> classToFind){
		LinkedList<Position> ret = new LinkedList<>();
		Optional<Position> toOp = owner.getWorld().getEntities().stream()
				.filter(classToFind::isInstance)
				.filter(c->c.getOwner()!=owner)
				.map(a->new Position(a.getPositionX(),a.getPositionY()))
				.min(Comparator.comparingInt(b->manhattanDistance(b.x,b.y,x,y)));
		if(!toOp.isPresent()) return null;
		Position to = toOp.get();

		return shortestPath(owner.getWorld(),x,y,to.x,to.y);
	}

	static public LinkedList<Position> pathToNearestEnemy(City owner, int x, int y,Class<? extends Entity> classToFind){

		//check if target in zone exist
		if(owner.getWorld().getEntities().stream()
				.filter(a -> manhattanDistance(a.getPositionX(), a.getPositionY(), x, y) < maxDistance)
				.filter(classToFind::isInstance)
				.noneMatch(c->c.getOwner()!=owner)
				) {
		 return naiveDirectionToNearest(owner,x,y,classToFind);
		}

		//init
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

		//compute distance
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
				if(!distanceMap.containsKey(to)){
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

		//compute path
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

	public static LinkedList<Position> shortestPath(World world, int x, int y, int x1, int y1){

		Position pos=null;
		LinkedList<Position> path = new LinkedList<>();
		Map<Position,Integer> distanceMap = new HashMap<>();
		PriorityQueue<Position> toCheck = new PriorityQueue<Position>(100,Comparator.comparingDouble(a->(manhattanDistance(((Position)a).x,((Position)a).y,x1,y1)*heuristicRate+distanceMap.get(a))));

		toCheck.add(new Position(x,y));
		distanceMap.put(new Position(x,y),0);

		while(toCheck.size()!=0){
			pos = toCheck.poll();


			List<Position> toPos = new ArrayList<>();
			toPos.add(new Position(pos.x+1,pos.y));
			toPos.add(new Position(pos.x-1,pos.y));
			toPos.add(new Position(pos.x,pos.y+1));
			toPos.add(new Position(pos.x,pos.y-1));
			Collections.shuffle(toPos);
			int costTo=0;
			for(Position to:toPos){
				costTo =world.traverseCost(to.x,to.y);
				if(!distanceMap.containsKey(to)){
					distanceMap.put(to,distanceMap.get(pos)+costTo);
					toCheck.add(to);
					if(to.y==y1 && to.x==x1){
						pos=to;
						toCheck.clear();
						break;
					}
				}
			}
		}

		while(pos.x!=x || pos.y!=y){
			path.addFirst(pos);
			Position[] toPos = new Position[4];
			toPos[0]= new Position(pos.x+1,pos.y);
			toPos[1]= new Position(pos.x-1,pos.y);
			toPos[2]= new Position(pos.x,pos.y+1);
			toPos[3]= new Position(pos.x,pos.y-1);
			try {
				pos = Arrays.stream(toPos).min(Comparator.comparingInt(a-> distanceMap.getOrDefault(a, Integer.MAX_VALUE))).get();
			}catch (NullPointerException e){
				e.printStackTrace();
			}

		}

		return path;


	}

}

