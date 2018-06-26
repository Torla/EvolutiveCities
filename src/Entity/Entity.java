package Entity;

import graphics.Showable;
import graphics.Tile;

public abstract class Entity implements Showable{

	protected Tile tile;
	protected int positionX;
	protected int positionY;

	public Entity(Tile tile, int positionX, int positionY) {
		this.tile = tile;
		this.positionX = positionX;
		this.positionY = positionY;
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
		return 0;
	}
}
