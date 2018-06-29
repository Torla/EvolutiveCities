package city;

import Entity.Entity;
import Entity.building.Building;
import Entity.building.Field;
import Entity.building.House;
import Entity.building.Keep;
import Game.World;
import graphics.Showable;
import graphics.Tile;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class City {

	private World world;


	private int population;
	private int food;

	private int cursorX;
	private int cursorY;

	public City(World world,int cursorX, int cursorY) {
		this.world = world;
		food=Options.startingFood;
		this.cursorX=cursorX;
		this.cursorY=cursorY;
	}


	private void production(){
		population=0;
		for(Entity building:world.getEntities().stream().filter(x->x.getOwner()==this).filter(x->x instanceof Building).collect(Collectors.toList())){
			if(building instanceof House){
				population+=Options.peoplePerHouse;
			}
			else if(building instanceof Field){
				food+=Options.foooFromField;
			}
		}
	}
	private void upkeep() {
		boolean flag = false;
		int freeWorkers = population;
		for (Entity entity : world.getEntities().stream().filter(x -> x.getOwner() == this).collect(Collectors.toList())) {
			if (entity instanceof House) {
				food -= Options.peoplePerHouse;
			} else if (entity instanceof Field) {
				freeWorkers -= Options.workerPerFiled;
			} else if (entity instanceof Keep) {
				freeWorkers -= Options.workerPerKepp;
			}
			if (food < 0 || freeWorkers < 0) {
				food = 0;
				entity.destroy();
			}

		}
	}


	public void turn(){
		production();
		upkeep();
	};


	public World getWorld() {
		return world;
	}

	public int getPopulation() {
		return population;
	}

	public int getFood() {
		return food;
	}

	public Set<Showable> getShowable(){
		HashSet<Showable> ret = new HashSet<>();
		ret.add(new Showable(){
			@Override
			public Tile getTile() {
				return Tile.Z8;
			}

			@Override
			public int getPositionX() {
				return cursorX;
			}

			@Override
			public int getPositionY() {
				return cursorY;
			}
		});
		return ret;
	}

	@Override
	public String toString() {
		return "p:" + population + " f:" + food + " c:" + cursorX + "," + cursorY ;
	}
}
