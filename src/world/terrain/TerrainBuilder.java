package world.terrain;

import javafx.geometry.Pos;
import world.Options;
import world.World;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class TerrainBuilder {

	private static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}


	static Random rng = new Random();

	static public void worldBuild(World world) {
		for(int i=0;i<Options.boundary;i++){
			for(int j=0;j<Options.boundary;j++){
				if(fate(Options.forestOccur)) createForest(world,new Position(i,j));
			}
		}
	}

	private static boolean fate(int n) {
		return rng.nextInt(1000) < n;
	}

	static private void createForest(World world, Position start) {
		LinkedList<Position> list = new LinkedList<>();
		list.push(start);
		Position pos;
		int count=Options.forestDim;
		while (list.size() > 0 && count>0) {
			if (!fate(count--)) continue;
			Collections.shuffle(list);
			pos = list.pop();
			if (fate(Options.LakeOccur)) new Lake(world, pos.x, pos.y);
			else new Forest(world, pos.x, pos.y);
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					list.addLast(new Position(pos.x + i, pos.y + j));
				}
			}
		}
	}
}
