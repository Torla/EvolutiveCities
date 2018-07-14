package Entity.building;

import Entity.Entity;
import Entity.pathFinder.PathFinder;
import Entity.unity.Unit;
import automaton.Automaton;
import city.City;
import graphics.Tile;
import world.World;

public class BanditCamp extends Building{

	private int turn=0;

	public BanditCamp(World world, int positionX, int positionY) {
		super(world, new City(world,new Automaton(),positionX,positionY), Tile.CAMP0, positionX, positionY,0, 100);
		new Building(world,owner,Tile.CAMP1,positionX+1,positionY,0,100);
		new Building(world,owner,Tile.CAMP2,positionX,positionY+1,0,100);
		new Building(world,owner,Tile.CAMP3,positionX+1,positionY+1,0,100);
	}

	@Override
	public void turn() {
		super.turn();
		if(++turn%1000==0) new Unit(world,owner, Tile.ROGUE,positionX,positionY,1,1,3,Entity.class){
			@Override
			public void turn() {
				if(world.getEntities().stream()
						.filter(x->x.getOwner()!=owner)
						.anyMatch(x->PathFinder.manhattanDistance(owner.getCursorX(),owner.getCursorY(),x.getPositionX(),x.getPositionY())<40)) {
					super.turn();
				}
			}
		};

	}
}
