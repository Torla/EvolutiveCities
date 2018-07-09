package automaton;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Automaton implements Serializable {


	private Set<State> states= new HashSet<>();
	private State initialState;
	private State currentState;
	private Stack<Integer> stack = new Stack<>();

	transient private Set<State> usedStates = new HashSet<>();

	public void addState(State state){
		state.setID(states.size());
		states.add(state);
	}

	public void setInitialState(State initialState) {
			addState(initialState);
			this.initialState=initialState;
	}

	public Set<State> getStates() {
		return states;
	}

	public State getInitialState() {
		return initialState;
	}

	public State getCurrentState() {
		return currentState;
	}

	public Stack<Integer> getStack() {
		return stack;
	}

	public AutomatonOutputValues next(){
		Edge edge;

		usedStates.add(currentState);

		try {
			edge = currentState.getEdge(stack.peek());
		}catch (EmptyStackException e){
			edge = currentState.getEdge(0);
		}
		if (edge.getStackAction() == StackAction.POP) {
			try {
				stack.pop();
			} catch (EmptyStackException e) {
			}
		}
		else if (edge.getStackAction()==StackAction.PUSH) stack.push(edge.getStackValue());
		currentState=edge.getToState();
		return edge.getOutput();
	}

	public void stackPush(Integer x){
		stack.push(x);
	}


	public void reset(){
		currentState=initialState;
		stack.clear();

		usedStates = new HashSet<>();
	}

	public Set<State> getUsedStates() {
		return usedStates;
	}

	@Override
	public String toString() {
		return "Stack:" + stack + "\n Init:" + initialState + " Curr:" + currentState + "\n" + states;
	}


}
