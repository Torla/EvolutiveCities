package Entity.building;

import Entity.Entity;
import Game.World;
import city.City;
import graphics.Tile;
import sun.nio.cs.CharsetMapping;

import java.util.Optional;

public class Building extends Entity {

	public Building(World world, City owner, Tile tile, int positionX, int positionY) {
		super(world,owner,tile, positionX, positionY);
		if(owner.tooDistantFromCenter(this)){
			destroy();
			return;
		}
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
}
