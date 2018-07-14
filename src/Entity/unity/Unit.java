package Entity.unity;

import Entity.Entity;
import Entity.pathFinder.PathFinder;
import Entity.pathFinder.Position;
import world.World;
import city.City;
import graphics.Tile;

import java.util.LinkedList;
import java.util.Optional;

public class Unit extends Entity{

	private int power;
	private int movVelocity;
	private int movPoint=0;
	protected Class<? extends Entity> target;
	protected LinkedList<Position> path=null;
	protected boolean goingToTarget=false;

	public Unit(World world, City owner, Tile tile, int positionX, int positionY, int life, int power, int movVelocity, Class<? extends Entity> target) {
		super(world, owner, tile, positionX, positionY, life);
		this.life=life;
		this.movVelocity=movVelocity;
		this.target=target;
		this.power=power;
	}

	@Override
	public void attack(Unit attacker, int power) {
		super.attack(attacker, power);
	}

	@Override
	public void turn(){
		Optional<Entity> op= owner.getWorld().getEntities().stream()
				.filter(target::isInstance)
				.filter(c->c.getOwner()!=owner)
				.filter(x->x.getPositionX()==positionX && x.getPositionY()==positionY).findAny();
		if(op.isPresent()){
			op.get().attack(this,power);
			path=null;
		}
		else{
			movPoint+=movVelocity;
			//check if path no longer lead to target
			if(goingToTarget && path!=null && owner.getWorld().getEntities().stream().filter(target::isInstance).noneMatch(x->x.getPositionY()==path.getLast().y && x.getPositionX()==path.getLast().x)){
				path=null;
				goingToTarget=false;
			}

			//select new path
			if(path==null) {
				if(owner.getWorld().getEntities().stream().filter(x->x.getOwner()!=owner).anyMatch(target::isInstance)) {
					path = PathFinder.pathToNearestEnemy(this.owner, positionX, positionY, target);
					goingToTarget=owner.getWorld().getEntities().stream().filter(target::isInstance).anyMatch(x->x.getPositionY()==path.getLast().y && x.getPositionX()==path.getLast().x);
				}
				if(path==null) return;
			}
			//move
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
