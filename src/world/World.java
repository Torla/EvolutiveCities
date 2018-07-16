package world;

import Entity.Entity;
import Entity.building.BanditCamp;
import Entity.building.Building;
import automaton.Automaton;
import city.City;
import graphics.Showable;
import world.terrain.Road;
import world.terrain.TerrainBuilder;
import world.terrain.TerrainFeature;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class World {

	Random rng = new Random();

	private HashSet<Entity> entities= new HashSet<>();
	private HashSet<TerrainFeature> terrain = new HashSet<>();

	City noOwner = new City(this,new Automaton(),0,0);


	public World() {
		TerrainBuilder.worldBuild(this);

	}

	public void addEntity(Entity entity){
		entities.add(entity);
	}
	public void removeEntity(Entity entity){
		entities.remove(entity);
	}

	public HashSet<Entity> getEntities() {
		return entities;
	}

	public HashSet<Showable> getShowable(){
		HashSet ret = new HashSet();
		while(ret.size()==0) {
			try {
				ret.addAll(terrain);
				ret.addAll(entities);
			} catch (ConcurrentModificationException e) { //todo mutex
				//e.printStackTrace();
			}
		}

		return ret;
	}




	public void reset(){
		entities.clear();
		int bandCampNum = rng.nextInt(Options.bandCampNumMax);
		for(int i=0;i<bandCampNum;i++){
			new BanditCamp(this,rng.nextInt(Options.boundary),rng.nextInt(Options.boundary));
		}
	}

	public void putFeature(TerrainFeature terrainFeature){
		terrain.add(terrainFeature);
	}

	public  Set<TerrainFeature> getTerrain() {
		return terrain;
	}

	public int traverseCost(int x, int y){
		int ret=1;
		if(terrain.stream()
				.filter(a->a.getPositionY()==y && a.getPositionX()==x)
				.anyMatch(a->a instanceof Road)) return 1;
		ret+=entities.stream()
				.filter(b->b instanceof Building).map(c->(Building) c)
				.filter(a->a.getPositionY()==y && a.getPositionX()==x)
				.mapToInt(d->d.getTraverseCost()).sum();
		ret+=terrain.stream()
				.filter(a->a.getPositionY()==y && a.getPositionX()==x)
				.mapToInt(TerrainFeature::getTraverseCost).sum();
		return ret*10;
	}
}
