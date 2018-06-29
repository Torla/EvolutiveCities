package automaton;

import java.io.Serializable;

public class Edge implements Serializable{
	private State toState;
	private StackAction stackAction;
	private int stackValue;
	private AutomatonOutputValues output;

	public Edge(State toState, StackAction stackAction, int stackValue, AutomatonOutputValues output) {
		this.toState = toState;
		this.stackAction = stackAction;
		this.stackValue = stackValue;
		this.output = output;
	}

	public State getToState() {
		return toState;
	}

	public StackAction getStackAction() {
		return stackAction;
	}

	public int getStackValue() {
		return stackValue;
	}

	public AutomatonOutputValues getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return "(" + toState.getID() + "," + stackAction + "," + stackValue + "," + output  + ")";
	}
}

