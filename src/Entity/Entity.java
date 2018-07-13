package Entity;

import Entity.unity.Unit;
import world.World;
import city.City;
import graphics.Showable;
import graphics.Tile;

public abstract class Entity implements Showable{

	protected Tile tile;
	protected City owner;
	protected World world;
	protected int positionX;
	protected int positionY;

	protected int life;

 	public Entity(World world, City owner, Tile tile, int positionX, int positionY, int life) {
		this.tile = tile;
		this.owner=owner;
		this.world=world;
		this.positionX = positionX;
		this.positionY = positionY;
		world.addEntity(this);
	}

	@Override
	public Tile getTile() {
		return tile;
	}

	@Override
	public int getPositionX() {
		return positionX;
	}

	@Override
	public int getPositionY() {
		return positionY;
	}

	public int distance(int x, int y){
		return Math.abs(positionX-x)+Math.abs(positionY-y);
	}

	public City getOwner() {
		return owner;
	}

	public void attack(Unit attacker,int power){
		life-=power;
		if(life<=0) destroy();
	}

	public void destroy(){
		world.removeEntity(this);
	}

	public void turn(){}
}
