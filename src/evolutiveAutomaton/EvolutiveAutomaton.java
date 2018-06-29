package evolutiveAutomaton;

import automaton.*;

import java.util.*;

public class EvolutiveAutomaton extends Automaton{
	private String name;
	private AutomatonOutputValues[] outputValues;
	private static final Random rng= new Random();
	public EvolutiveAutomaton(AutomatonOutputValues values[]) {
		outputValues = values;
		int numState = rng.nextInt(Options.IntialStateMax) + 1;
		List<State> states = new ArrayList<>();
		for (int i = 0; i < numState; i++) {
			states.add(new State());
		}
		for (State state : states) {
			for (int stackValue = 0; stackValue < Options.stackValuesNum; stackValue++) {
				state.addEdge(stackValue,
						new Edge(states.get(rng.nextInt(states.size())),
								StackAction.values()[rng.nextInt(StackAction.values().length)],
								rng.nextInt(Options.stackValuesNum),
								values[rng.nextInt(values.length)])
				);

			}
			this.setInitialState(states.get(0));
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
