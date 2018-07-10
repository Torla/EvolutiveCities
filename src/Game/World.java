package Game;

import Entity.Entity;
import Entity.building.Building;
import graphics.Showable;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

public class World {

	private HashSet<Entity> entities= new HashSet<Entity>();

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
		HashSet ret = null;
		while(ret==null) {
			try {
				ret = new HashSet<>(entities);
			} catch (ConcurrentModificationException e) { //todo mutex
				//e.printStackTrace();
			}
		}
		return ret;
	}

	public void reset(){
		entities.clear();
	}

	public int traverseCost(int x, int y){
		int ret=1;
		ret+=entities.stream()
				.filter(b->b instanceof Building).map(c->(Building) c)
				.filter(a->a.getPositionY()==y && a.getPositionX()==x)
				.mapToInt(d->d.getTraverseCost()).sum();
		return ret*10;
	}
}
