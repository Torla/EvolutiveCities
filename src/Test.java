import Entity.Entity;
import Entity.building.House;
import Entity.building.Keep;
import Entity.pathFinder.PathFinder;
import Entity.unity.Knight;
import Entity.unity.Soldier;
import Entity.unity.Unit;
import Game.World;
import automaton.Automaton;
import city.City;
import graphics.Graphics;

public class Test {
	public static void main(String[] args) {
		System.out.println("start test");
		World world = new World();
		City city = new City(world,new Automaton(),0,0);

		world.reset();
		Graphics.setWorld(world);
		Graphics.start();



		new Soldier(world,city,3,3);
		//new House(world,city,0,2);
		//new House(world,city,0,3);
		//new House(world,city,5,5);
		City city1 = new City(world,new Automaton(),0,0);
		Unit u=new Soldier(world,city1,0,0);
		System.out.println(PathFinder.pathToNearestEnemy(city1,0,0,Entity.class));
		while(world.getEntities().size()>1) {
			u.turn();
			//System.out.println(world.getEntities());
		}

	}
}
