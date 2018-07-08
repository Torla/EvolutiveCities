package Entity.unity;

import Entity.Entity;
import Entity.building.Building;
import Entity.pathFinder.PathFinder;
import Entity.pathFinder.Position;
import Game.World;
import city.City;
import graphics.Tile;

import java.util.LinkedList;
import java.util.Optional;

public class Unit extends Entity{

	private int power;
	private int movVelocity;
	private int movPoint=0;
	private LinkedList<Position> path=null;

	public Unit(World world, City owner, Tile tile, int positionX, int positionY,int life,int power,int movVelocity) {
		super(world, owner, tile, positionX, positionY, life);
		this.life=life;
		this.movVelocity=movVelocity;
		this.power=power;
	}

	public void turn(){
		Optional<Entity> op= owner.getWorld().getEntities().stream()
				.filter(c->c.getOwner()!=owner)
				.filter(x->x.getPositionX()==positionX && x.getPositionY()==positionY).findAny();
		if(op.isPresent()){
			op.get().attack(power);
			path=null;
		}
		else{
			movPoint+=movVelocity;
			if(path==null) {
				path = PathFinder.pathToNearestEnemy(this.owner, positionX, positionY);
			}
			if(path==null) return;
			if(movPoint>=owner.getWorld().traverseCost(path.peek().x,path.peek().y)){
				movPoint-=owner.getWorld().traverseCost(path.peek().x,path.peek().y);
				positionX=path.peek().x;
				positionY=path.peek().y;
				path.pop();
				if(path.size()==0) path=null;
			}

		}
	}
}
