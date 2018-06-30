package Entity;

import Game.World;
import city.City;
import graphics.Showable;
import graphics.Tile;

public abstract class Entity implements Showable{

	protected Tile tile;
	protected City owner;
	private World world;
	protected int positionX;
	protected int positionY;

 	public Entity(World world, City owner,Tile tile, int positionX, int positionY) {
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

	public void destroy(){
		world.removeEntity(this);
	}
}
