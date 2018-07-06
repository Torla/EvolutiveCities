import Game.World;
import automaton.Automaton;
import city.Action;
import city.City;
import evolutiveAutomaton.EvolutiveAutomaton;
import genetic.Pool;
import graphics.Graphics;
import Game.Match;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	static Pool<EvolutiveAutomaton> pool = new Pool<>();


	static private void save() throws IOException {
		FileOutputStream fileOut=null;
		try {
			fileOut = new FileOutputStream("pool");
		}catch (FileNotFoundException e){
			e.printStackTrace();
		};

		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(pool);
		out.close();
		fileOut.close();

		try {
			fileOut = new FileOutputStream("poolbackup");
		}catch (FileNotFoundException e){
			e.printStackTrace();
		};

		out = new ObjectOutputStream(fileOut);
		out.writeObject(pool);
		out.close();
		fileOut.close();
	}

	static private void load() {
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		try {
			fileIn = new FileInputStream("pool");
			in = new ObjectInputStream(fileIn);
			pool = (Pool<EvolutiveAutomaton>) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			try {
				fileIn = new FileInputStream("poolbackup");
				in = new ObjectInputStream(fileIn);
				pool = (Pool<EvolutiveAutomaton>) in.readObject();
			} catch (Exception ex) {
			}
		}
	}
	public static void main(String[] args) throws InterruptedException, IOException {
		World world=new World();
		Graphics.setWorld(world);
		Graphics.start();

		for (int i = 0; i < 100; i++) pool.add(new EvolutiveAutomaton(Action.values()));

		load();
	while(true) {


		world.reset();


		LinkedList<City> c = new LinkedList<>();
		int i = 0;
 		for (Automaton automaton : pool.getPop(100)) {
			c.add(new City(world, automaton, 20 * (i%4), 20 * (i/4)));
			i++;
		}
		Match match = new Match(c);
		Graphics.setMatch(match);

		Thread t = new Thread(match);
		t.start();
		t.join();


		System.out.println(match.rank().get(0).getPopulation());
		System.out.println(match.rank().stream().mapToInt(City::getPopulation).average());
		System.out.println(match.rank().get(0));

		pool.generation(match.rank().stream().map(x -> (EvolutiveAutomaton) x.getAutomaton()).collect(Collectors.toList()), 10, 9);
		save();
	}
	}

}
