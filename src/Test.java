import Entity.building.House;
import Entity.building.Keep;
import Entity.pathFinder.PathFinder;
import Entity.unity.Soldier;
import Entity.unity.Unit;
import Game.World;
import automaton.Automaton;
import city.City;

public class Test {
	public static void main(String[] args) {
		System.out.println("start test");
		World world = new World();
		City city = new City(world,new Automaton(),0,0);

		new House(world,city,0,1);
		new House(world,city,0,2);
		new House(world,city,0,3);
		new House(world,city,41,40);
		City city1 = new City(world,new Automaton(),0,0);
		Unit u=new Soldier(world,city1,0,0);
		System.out.println(PathFinder.pathToNearestEnemy(city1,0,0));
		while(world.getEntities().size()>1) {
			u.turn();
			System.out.println(world.getEntities());
		}

	}
}
