package evolutiveAutomaton;

import city.StackValue;

public class Options {
	private static final int additionStackValues = 10;
	static final int stackValuesNum = StackValue.values().length + additionStackValues;
	static final int stateMaxNum = 100;
	static final int mutation = 500; //per mille
}