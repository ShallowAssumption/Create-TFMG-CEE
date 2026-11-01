package com.drmangotea.tfmg.content.machinery.vat.freezer;

import com.george_vi.electroenergetics.devices.device.DevicesSavedData;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;
import com.george_vi.electroenergetics.foundation.device.SimpleElectricalDevice;
import com.george_vi.electroenergetics.simulation.BridgeCollector;
import com.george_vi.electroenergetics.simulation.SimulationResults;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class FreezerDevice extends SimpleElectricalDevice {
	public FreezerBlockEntity be;
	
	// public int getMaxVoltage() { return 20000; }
	// public int getMaxCurrent() { return 400; }
	
	public FreezerDevice(Level level, BlockPos pos, DevicesSavedData deviceSD, SimulatedDeviceType<?> type)
	{ super(level, pos, deviceSD, type); }
	
	@Override
	public void preTick(BridgeCollector bridges) { bridges.builder(pos).resistor(0, 1, 75d); }
	
	@Override
	public void postTick(SimulationResults results) {
		if (be == null && level.isLoaded(pos) && level.getBlockEntity(pos) instanceof FreezerBlockEntity fbe)
			be = fbe;
		if (be == null) return;
		if (be.isRemoved()) {
			be = null;
		} else {
			be.setVoltage(Math.abs(results.getVoltageAt(pos, 0, 1)));
			be.setAmperage(Math.abs(results.getCurrentThrough(pos, 0, 1)));
		}
	}
}