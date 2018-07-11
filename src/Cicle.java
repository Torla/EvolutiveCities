import Game.Match;
import Game.World;
import automaton.Automaton;
import city.City;
import evolutiveAutomaton.EvolutiveAutomaton;
import graphics.Graphics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

class Cicle implements Runnable {
	World world;
	Random rng = new Random();

	public Cicle(World world) {
		this.world = world;
	}

	@Override
	public void run() {
		cicle(world);
	}

	private void cicle(World world){
		while (true) {
			int playerNum=rng.nextInt(2)*2+2;
			world.reset();
			LinkedList<City> c = new LinkedList<>();
			int i = 0;
			final Collection<EvolutiveAutomaton> pop;
			try {
				Main.poolLock.lock();
				pop = Main.pool.getPop(playerNum);
			}finally {
				Main.poolLock.unlock();
			}
			for (Automaton automaton : pop) {
				automaton.reset();
				c.add(new City(world, automaton, 20 * (i % 2) + 10+(rng.nextInt(10)-5), 20 * (i / 2) + 10+(rng.nextInt(10)-5)));
				i++;
			}
			Match match = new Match(c);
			match.run();

			System.out.println(match.rank().get(0));
			System.out.println(match.rank().stream().mapToInt(x -> ((EvolutiveAutomaton) x.getAutomaton()).getGeneration()).max().getAsInt());
			System.out.println(match.rank().stream().map(City::getAutomaton).mapToDouble(x->((EvolutiveAutomaton)x).getMutation()).average());
			try {
				Main.poolLock.lock();
				Main.pool.generation(match.rank().stream().map(x -> (EvolutiveAutomaton) x.getAutomaton()).collect(Collectors.toList()), playerNum/2, 1);
			} finally {
				Main.poolLock.unlock();
			}
		}
	}
}