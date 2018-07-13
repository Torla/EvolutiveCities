package world.terrain;

import javafx.geometry.Pos;
import world.Options;
import world.World;

import java.awt.*;
import java.util.*;

public class TerrainBuilder {

	private static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return x*2+y*7;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof Position && x == ((Position) obj).x && y == ((Position) obj).y;
		}
	}


	static Random rng = new Random();

	static public void worldBuild(World world) {
		used.clear();
		for(int i=0;i<Options.boundary;i++){
			for(int j=0;j<Options.boundary;j++){
				if(fate(Options.forestOccur,100000)) createForest(world,new Position(i,j),rng.nextInt(Options.forestDim));
				if(fate(Options.mountainOccur,100000)) createMountain(world,new Position(i,j),rng.nextInt(Options.mountainDim));
			}
		}
	}

	private static boolean fate(int n) {
		return rng.nextInt(1000) < n;
	}
	private static boolean fate(int n,int m) {
		return rng.nextInt(m) < n;
	}


	private static HashSet<Position> used = new HashSet<>(); //position yet processed

	static private void createForest(World world, Position start,int dim) {
		LinkedList<Position> list = new LinkedList<>();
		list.push(start);
		Position pos;
		int count=dim;
		while (list.size() > 0 && count>0) {
			if (!fate(count--,dim)) continue;
			Collections.shuffle(list);
			pos = list.pop();
			if (fate(Options.LakeOccur)) new Lake(world, pos.x, pos.y);
			else new Forest(world, pos.x, pos.y);
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Position newPos = new Position(pos.x + i, pos.y + j);
					if(used.contains(newPos)) continue;
					used.add(newPos);
					list.addLast(newPos);
				}
			}
		}
	}
	static private void createMountain(World world, Position start,int dim) {
		LinkedList<Position> list = new LinkedList<>();
		Position pos=start;
		list.addLast(start);
		int count=dim;
		while (count!=0 && fate(count--,dim)){
			list.addLast(new Position(pos.x+rng.nextInt(2)-1,pos.y+rng.nextInt(2)-1));
		}
		count=dim;
		boolean hill=false;
		while (list.size() > 0 && count>0) {
			if (!fate(count--,dim)) continue;
			Collections.shuffle(list);
			pos = list.pop();
			if(fate(Options.hillZone,100000)) hill=true;
			if (fate(Options.LakeOccur)) new Lake(world, pos.x, pos.y);
			else if(hill){new Hills(world,pos.x,pos.y);}
			else new Mountain(world, pos.x, pos.y);
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Position newPos = new Position(pos.x + i, pos.y + j);
					if(used.contains(newPos)) continue;
					used.add(newPos);
					list.addLast(newPos);
				}
			}
		}
	}
}
