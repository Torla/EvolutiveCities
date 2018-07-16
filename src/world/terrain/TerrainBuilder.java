package world.terrain;


import world.Options;
import world.World;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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


	private static int[][] heightMap = new int[Options.boundary][Options.boundary];

	static public void worldBuild(World world) {


		generateHeightMap();
		createMountain(world);

		//createForest(world);

		int numRoad = rng.nextInt(Options.roadNumMax);
		for(int i=0;i<numRoad;i++){
			//Road.roadBetween(world,rng.nextInt(Options.boundary),rng.nextInt(Options.boundary),rng.nextInt(Options.boundary),rng.nextInt(Options.boundary));
		}

		int numRiver = rng.nextInt(Options.riverNumMax);
		System.out.println(numRiver);
		final List<Position> collect = world.getTerrain().stream().filter(Mountain.class::isInstance).map(a -> new Position(a.getPositionX(), a.getPositionY())).collect(Collectors.toList());
		for(int i=0;i<numRiver;i++){
			createRiver(world, collect.get(rng.nextInt(collect.size())));

		}



	}

	private static boolean fate(int n) {
		return rng.nextInt(1000) < n;
	}
	private static boolean fate(int n,int m) {
		return rng.nextInt(m) < n;
	}


	private static HashSet<Position> used = new HashSet<>(); //position yet processed


	static private void generateHeightMap(){
		OpenSimplexNoise noise = new OpenSimplexNoise(rng.nextLong());
		double alfa= rng.nextDouble();
		double beta= rng.nextDouble();
		for(double i=0;i<Options.boundary;i++){
			for(double j=0;j<Options.boundary;j++){
				heightMap[(int)i][(int)j]= (int) (Math.pow(noise.eval((i*Math.sin(alfa)+j*Math.cos(alfa))/10.,(j*Math.sin(alfa)+i*Math.cos(alfa))/15.),3)*100.);

				alfa+=beta;

				heightMap[(int)i][(int)j]+= (int) (Math.pow(noise.eval((i*Math.sin(alfa)+j*Math.cos(alfa))/10.,(j*Math.sin(alfa)+i*Math.cos(alfa))/15.),3)*100.);

				alfa-=beta;

				heightMap[(int) i][(int) j]/=2;

			}
		}


		for(int count=0;count<1;count++){
			int[][] convMap = new int[Options.boundary][Options.boundary];
			for (int i = 0; i < Options.boundary; i++) {
				for (int j = 0; j < Options.boundary; j++) {
					for (int x = i - 1; x <= i + 1; x++) {
						for (int y = j - 1; y <= j + 1; y++) {
							try {
								convMap[i][j] += heightMap[x][y];
							}catch (ArrayIndexOutOfBoundsException e){}
						}
					}
					convMap[i][j] /= 9;
				}
			}
			heightMap = convMap;
		}

	}
	static private void createForest(World world){
		OpenSimplexNoise noise = new OpenSimplexNoise(rng.nextLong());
		for(int i=0;i<Options.boundary;i++){
			for(int j=0;j<Options.boundary;j++){
				if(Math.pow(noise.eval(i/20.,j/20.),0.5)>0){
					new Forest(world,i,j);
				}
			}
		}
	}
	static private void createMountain(World world) {

		for(int i=0;i<Options.boundary;i++){
			for(int j=0;j<Options.boundary;j++){
				if(heightMap[i][j]>10) new Mountain(world,i,j);
				else if(heightMap[i][j]>6) new Hills(world,i,j);
				else if(heightMap[i][j]<-10) new Water(world,i,j);
			}
		}

	}
	static private void createRiver(World world,Position start){

		int dir=rng.nextInt(4);

		Set<Position> thisRiver = new HashSet<>();

		Position pos=start;
		while(pos.x<Options.boundary && pos.x>=0 && pos.y<Options.boundary && pos.y>=0){

			new River(world,pos.x,pos.y);

			thisRiver.add(pos);

			Position[] toPos = new Position[4];
			toPos[0]= new Position(pos.x+1,pos.y);
			toPos[1]= new Position(pos.x,pos.y+1);
			toPos[2]= new Position(pos.x-1,pos.y);
			toPos[3]= new Position(pos.x,pos.y-1);


			Position tempPos=Arrays.stream(toPos).min(Comparator.comparingInt(a -> {
				try {
					return heightMap[a.x][a.y];
				}catch (ArrayIndexOutOfBoundsException e){
					return 0;
				}
			})).get();

			if(thisRiver.contains(tempPos)){
				thisRiver.remove(pos);
				new Lake(world,pos.x,pos.y);
				break;
			}


			pos=tempPos;

			Position finalPos = pos;

			final Optional<TerrainFeature> terrainFeature = world.getTerrain().stream()
					.filter(a -> a instanceof Water || a instanceof River)
					.filter(a->a.getPositionX()==finalPos.x && a.getPositionY()==finalPos.y).findAny();
			if(terrainFeature.isPresent()){
				break;
			}

			world.getTerrain().removeAll(world.getTerrain().stream()
					.filter(a->!(a instanceof River))
					.filter(a->thisRiver.contains(new Position(a.getPositionX(),a.getPositionY())))
					.collect(Collectors.toList()));

		}

	}
}
