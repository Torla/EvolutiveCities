package world.terrain;

import graphics.Showable;
import graphics.Tile;
import world.World;

public class TerrainFeature implements Showable{

	private int x;
	private int y;
	Tile tile;
	World world;

	private int traverseCost;


	public TerrainFeature(World world,int x, int y, Tile tile, int traverseCost) {
		this.x = x;
		this.y = y;
		this.tile = tile;
		this.traverseCost = traverseCost;
		this.world=world;
		world.putFeature(this);
	}


	@Override
	public Tile getTile() {
		return tile;
	}

	@Override
	public int getPositionX() {
		return x;
	}

	@Override
	public int getPositionY() {
		return y;
	}

	public int getTraverseCost() {
		return traverseCost;
	}

	@Override
	public int hashCode() {
		return getPositionX()+getPositionY();
	}
}
