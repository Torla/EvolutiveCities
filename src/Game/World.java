package Game;

import Entity.Entity;
import graphics.Showable;

import java.util.HashSet;

public class World {

	private HashSet<Entity> entities= new HashSet<Entity>();

	public void addEntity(Entity entity){
		entities.add(entity);
	}

	public HashSet<Entity> getEntities() {
		return entities;
	}

	public HashSet<Showable> getShowable(){
		return new HashSet<>(entities);
	}
}
