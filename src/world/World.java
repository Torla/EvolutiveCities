package world;

import Entity.Entity;
import Entity.building.Building;
import automaton.Automaton;
import city.City;
import graphics.Showable;
import world.terrain.TerrainBuilder;
import world.terrain.TerrainFeature;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

public class World {

	private HashSet<Entity> entities= new HashSet<>();
	private HashSet<TerrainFeature> terrain = new HashSet<>();

	City noOwner = new City(this,new Automaton(),0,0);


	public World() {

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
		terrain.clear();
		TerrainBuilder.worldBuild(this);
	}

	public void putFeature(TerrainFeature terrainFeature){
		terrain.add(terrainFeature);
	}

	public int traverseCost(int x, int y){
		int ret=1;
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
