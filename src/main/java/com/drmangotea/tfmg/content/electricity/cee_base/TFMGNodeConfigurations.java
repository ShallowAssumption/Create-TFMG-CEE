package com.drmangotea.tfmg.content.electricity.cee_base;

import com.george_vi.electroenergetics.foundation.nodes.NodeConfigurator;

public class TFMGNodeConfigurations {
	public static final NodeConfigurator
		TWO_NODES_ON_FACE = new NodeConfigurator.Builder()
			.add( 0, 16, 8)
			.add(16, 16, 8)
			.simple(),
		ACCUMULATOR = new NodeConfigurator.Builder()
			.add( 4, 16, 8) // +
			.add(12, 16, 8) // -
			.simple(),
		LIGHT = new NodeConfigurator.Builder()
			.add( 5, 0, 8)
			.add(11, 0, 8)
			.simple(),
		POLARIZER = new NodeConfigurator.Builder()
			.add( 4, 8, 8)
			.add(12, 8, 8)
			.simple(),
		LARGE_SWITCH = new NodeConfigurator.Builder()
			.add(8, 8, 0)
			.add(8, 8, 32)
			.simple();
}
