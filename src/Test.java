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
import org.w3c.dom.ranges.Range;

import java.util.LinkedList;
import java.util.stream.IntStream;

public class Test {

	static public EvolutiveAutomaton auto() {
		return new EvolutiveAutomaton(Action.values());
	}


	public static void main(String[] args) throws InterruptedException {
		World world=new World();

		LinkedList<City> c = new LinkedList<>();
		for(int i=0;i<10;i++){ c.add(new City(world,auto(),10,10));}
		Match match= new Match(c);

		Graphics.setWorld(world);
		Graphics.setMatch(match);
		Graphics.start();



		Thread t = new Thread(match);
		t.start();
		t.join();


		System.out.println(match.rank());
		System.out.println(match.rank().get(0).getAutomaton());
	}

}
