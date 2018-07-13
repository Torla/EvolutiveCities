import Entity.Entity;
import Entity.pathFinder.PathFinder;
import Entity.unity.Soldier;
import Entity.unity.Unit;
import world.World;
import automaton.Automaton;
import city.City;
import graphics.Graphics;
import world.terrain.*;

public class Test {
	public static void main(String[] args) {
		System.out.println("start test");
		World world = new World();
		City city = new City(world,new Automaton(),0,0);

		world.reset();
		Graphics.setWorld(world);
		Graphics.start();

	}

}
