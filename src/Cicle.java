import Game.Match;
import Game.World;
import automaton.Automaton;
import city.City;
import evolutiveAutomaton.EvolutiveAutomaton;
import graphics.Graphics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

class Cicle implements Runnable {
	World world;

	public Cicle(World world) {
		this.world = world;
	}

	@Override
	public void run() {
		cicle(world);
	}

	private void cicle(World world){
		while (true) {
			world.reset();
			LinkedList<City> c = new LinkedList<>();
			int i = 0;
			final Collection<EvolutiveAutomaton> pop;
			try {
				Main.poolLock.lock();
				pop = Main.pool.getPop(2);
			}finally {
				Main.poolLock.unlock();
			}
			for (Automaton automaton : pop) {
				automaton.reset();
				c.add(new City(world, automaton, 20 * (i % 2) + 10, 20 * (i / 2) + 10));
				i++;
			}
			Match match = new Match(c);
			Graphics.setMatch(match);

			match.run();

			//System.out.println(match.rank().get(0));
			//System.out.println(match.rank().stream().mapToInt(x -> ((EvolutiveAutomaton) x.getAutomaton()).getGeneration()).max().getAsInt());
			System.out.println(Main.pool.getPop().stream().mapToDouble(x->(double) x.getUsedStates().size()).average());
			try {
				Main.poolLock.lock();
				Main.pool.generation(match.rank().stream().map(x -> (EvolutiveAutomaton) x.getAutomaton()).collect(Collectors.toList()), 1, 1);
			} finally {
				Main.poolLock.unlock();
			}
		}
	}
}