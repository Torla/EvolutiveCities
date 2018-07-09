package city;

import Entity.Entity;
import Entity.building.*;
import Entity.pathFinder.PathFinder;
import Entity.unity.Soldier;
import Entity.unity.Unit;
import Game.World;
import automaton.Automaton;
import graphics.Showable;
import graphics.Tile;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class City {

	private World world;

	private Automaton automaton;


	private int population;
	private int food;

	private int cursorX;
	private int cursorY;
	private int centerX;
	private int centerY;

	public City(World world,Automaton automaton,int cursorX, int cursorY) {
		this.world = world;
		this.automaton=automaton;
		automaton.reset();
		food=Options.startingFood;
		this.cursorX=cursorX;
		this.cursorY=cursorY;
		centerX=cursorX;
		centerY=cursorY;
	}


	private void production(){
		population=0;
		for(Entity building:world.getEntities().stream().filter(x->x.getOwner()==this).filter(x->x instanceof Building).collect(Collectors.toList())){
			if(building instanceof House){
				population+=Options.peoplePerHouse;
			}
			else if(building instanceof Field){
				food+=Options.foodFromField;
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
			}else if(entity instanceof Soldier){
				freeWorkers-=Options.workerPerSoldier;
				food-=Options.foodPerSoldier;
			}else if(entity instanceof Wall){
				freeWorkers-=Options.workerPerWall;
			}
			if (food < 0 || freeWorkers < 0) {
				entity.destroy();
				food=(food<0)?0:food;
			}

		}
	}

	private void perform(Action action){
		switch (action){
			case NULL:
				break;
			case CURSOR_NORD:
				if(tooDistantFromCenter(cursorX,cursorY-1)) {
					automaton.stackPush(StackValue.EXIT_NORTH.ordinal());
					break;
				}
				cursorY--;

				break;
			case CURSOR_SUD:
				if(tooDistantFromCenter(cursorX,cursorY+1)){
					automaton.stackPush(StackValue.EXIT_SUD.ordinal());
					break;
				}
				cursorY++;

				break;
			case CURSOR_EST:
				if(tooDistantFromCenter(cursorX+1,cursorY)){
					automaton.stackPush(StackValue.EXIT_EST.ordinal());
					break;
				}
				cursorX++;

				break;
			case CURSOR_WEST:
				if(tooDistantFromCenter(cursorX+1,cursorY)){
					automaton.stackPush(StackValue.EXIT_WEST.ordinal());
					break;
				}
				cursorX--;

				break;
			case BUILD_HOUSE:
				new House(world,this,cursorX,cursorY);
				break;
			case BUILD_FIELD:
				new Field(world,this,cursorX,cursorY);
				break;
			case BUILD_KEEP:
				new Keep(world,this,cursorX,cursorY);
				break;
			case BUILD_WALL:
				new Wall(world,this,cursorX,cursorY);
				break;
			case PRODUCE_SOLDIER:
				Optional<Keep> op = world.getEntities().stream()
					.filter(x->x.getOwner()==this)
					.filter(x->x instanceof Keep)
					.map(x->(Keep)x)
					.min(Comparator.comparingInt(x->PathFinder.manhattanDistance(x.getPositionX(),x.getPositionY(),cursorX,centerY)));
				if(op.isPresent()) {
					new Soldier(world, this, op.get().getPositionX(), op.get().getPositionY());
				}
				break;
		}
	}


	public void turn(){
		production();
		upkeep();
		Action action;
		for(Unit unit:world.getEntities().stream().filter(x-> x instanceof Unit).map(x->(Unit) x).collect(Collectors.toList())){
			unit.turn();
		};
		action=(Action) automaton.next();
		perform(action);
	};


	public World getWorld() {
		return world;
	}

	public Automaton getAutomaton() {
		return automaton;
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

	public boolean tooDistantFromCenter(int x, int y){
		return Math.abs(x-centerX)> Options.maxDistanceFromCenter ||  Math.abs(y-centerY) > Options.maxDistanceFromCenter;
	}
	@Override
	public String toString() {
		return "p:"
				+ population
				+ " f:" + food
				+ " c:" + cursorX + "," + cursorY
				+ " b:" + world.getEntities().stream().filter(x->x.getOwner()==this).filter(x->x instanceof Building).count()
				+ " u:" + world.getEntities().stream().filter(x->x.getOwner()==this).filter(x->x instanceof Unit).count();
	}
}
