package Entity.building;

import Entity.Entity;
import world.World;
import city.City;
import graphics.Tile;

import java.util.Optional;

public class Building extends Entity {

	private int traverseCost;


	Building(World world, City owner, Tile tile, int positionX, int positionY, int traverseCost, int life) {
		super(world,owner,tile, positionX, positionY, life);

		this.traverseCost=traverseCost;


		Optional<Entity> b =  world.getEntities().stream()
				.filter(x->x.getPositionX()==positionX && x.getPositionY()==positionY)
				.filter(x->x!=this)
				.filter(x->x instanceof Building
				).findAny();
		if(b.isPresent()){
			if(b.get().getOwner()==owner){

				b.get().destroy();
			}
			else {
				this.destroy();
			}
		}
	}

	public int getTraverseCost() {
		return traverseCost;
	}

	@Override
	public void turn(){
	}
}
