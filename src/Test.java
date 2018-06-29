import Entity.building.Field;
import Entity.building.House;
import Game.World;
import automaton.Automaton;
import automaton.Edge;
import automaton.StackAction;
import automaton.State;
import city.Action;
import city.City;
import evolutiveAutomaton.EvolutiveAutomaton;
import graphics.Graphics;
import Game.Match;

import java.util.LinkedList;

public class Test {

	static public EvolutiveAutomaton auto() {
		return new EvolutiveAutomaton(Action.values());
	}


	public static void main(String[] args) throws InterruptedException {
		World world=new World();
		City city=new City(world,auto(),0,0);
		new House(world,city,0,0);
		new Field(world,city,1,0);
		new Field(world,city,1,1);
		new House(world,city,0,1);
		new Field(world,city,1,2);
		new House(world,city,2,2);
		new Field(world,city,3,3);
		new House(world,city,3,2);

		LinkedList<City> c = new LinkedList<>();
		c.add(city);
		Match match= new Match(c);

		Graphics.setWorld(world);
		Graphics.setMatch(match);
		Graphics.start();



		Thread t = new Thread(match);
		t.start();
		t.join();
		System.out.println(match.rank());
	}

}
