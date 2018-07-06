package evolutiveAutomaton;

import automaton.*;
import genetic.Evolutive;

import java.util.*;

public class EvolutiveAutomaton extends Automaton implements Evolutive<EvolutiveAutomaton>{
	private String name;
	private AutomatonOutputValues[] outputValues;
	private static final Random rng= new Random();
	public EvolutiveAutomaton(AutomatonOutputValues values[]) {
		super();
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
			this.addState(state);
		}
		this.setInitialState(states.get(0));
	}

	public EvolutiveAutomaton(EvolutiveAutomaton evolutiveAutomaton){
		this.setInitialState(evolutiveAutomaton.getInitialState());
		this.outputValues=evolutiveAutomaton.outputValues;
	}

	private boolean fate(int n){
		return rng.nextInt(1000)<n;
	}
	@Override
	public EvolutiveAutomaton copyMutated() {
		EvolutiveAutomaton auto = new EvolutiveAutomaton(this);
		for(State state:this.getStates()){
			State newState = new State();
			for(int i=0;i<Options.stackValuesNum;i++){
				if(fate(Options.mutation)){
					LinkedList<State> l = new LinkedList<>(getStates());
					newState.addEdge(i, new Edge(l.get(rng.nextInt(l.size())),
							StackAction.values()[rng.nextInt(StackAction.values().length)],
							rng.nextInt(Options.stackValuesNum),
							outputValues[rng.nextInt(outputValues.length)]));
				}
				else newState.addEdge(i,new Edge(state.getEdge(i).getToState(),state.getEdge(i).getStackAction(),state.getEdge(i).getStackValue(),state.getEdge(i).getOutput()));
			}
			auto.addState(newState);
		}
		if(fate(Options.mutation)){ //todo fix
			State initialStat = new ArrayList<State>(auto.getStates()).get(rng.nextInt(auto.getStates().size()));
			auto.setInitialState(initialStat);
		}
		return auto;
	}


	@Override
	public String toString() {
		return super.toString();
	}
}
