package city;

import Entity.Entity;
import Entity.building.Building;
import Entity.building.Field;
import Entity.building.House;
import Entity.building.Keep;
import Game.World;

import java.io.File;
import java.util.HashSet;
import java.util.stream.Collectors;

public class City {

	World world;



	int population;
	int food;

	public City(World world) {
		this.world = world;
		food=Options.startingFood;
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

	@Override
	public String toString() {
		return population + " " + food;
	}
}
