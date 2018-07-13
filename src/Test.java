import Entity.building.BanditCamp;
import game.Match;
import world.World;
import automaton.Automaton;
import city.City;
import graphics.Graphics;

import java.util.Collection;
import java.util.LinkedList;

public class Test {
	public static void main(String[] args) {
		System.out.println("start test");
		World world = new World();
		City city = new City(world,new Automaton(),0,0);

		world.reset();
		Graphics.setWorld(world);
		Graphics.start();

		new BanditCamp(world,10,10);
		new BanditCamp(world,20,20);

		Collection<City> cities = new LinkedList<>();
		//cities.add(city);

		new Match(world,cities).run();

	}

}
