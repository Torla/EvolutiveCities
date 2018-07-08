import Entity.building.House;
import Entity.building.Keep;
import Entity.pathFinder.PathFinder;
import Game.World;
import automaton.Automaton;
import city.City;

public class Test {
	public static void main(String[] args) {
		System.out.println("start test");
		World world = new World();
		City city = new City(world,new Automaton(),0,0);

		new Keep(world,city,0,1);
		new House(world,city,0,2);
		new House(world,city,0,3);
		new House(world,city,41,40);
		System.out.println(PathFinder.pathToNearestEnemy(new City(world,new Automaton(),0,0),0,0));
	}
}
