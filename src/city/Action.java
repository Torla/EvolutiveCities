package city;

import automaton.AutomatonOutputValues;

public enum Action implements AutomatonOutputValues{
	NULL,
	CURSOR_NORD,
	CURSOR_SUD,
	CURSOR_EST,
	CURSOR_WEST,
	BUILD_HOUSE,
	BUILD_FIELD,
	BUILD_KEEP,
	BUILD_WALL,
	PRODUCE_SOLDIER,
	PRODUCE_KNIGHT;

	@Override
	public AutomatonOutputValues getValue(int n) {
		return Action.values()[n];
	}

	@Override
	public int valuesNumber() {
		return Action.values().length;
	}

}
