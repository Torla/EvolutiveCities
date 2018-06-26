package Entity;

import Game.World;
import graphics.Showable;
import graphics.Tile;

public abstract class Entity implements Showable{

	protected Tile tile;
	protected int positionX;
	protected int positionY;

	public Entity(World world, Tile tile, int positionX, int positionY) {
		this.tile = tile;
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
}
