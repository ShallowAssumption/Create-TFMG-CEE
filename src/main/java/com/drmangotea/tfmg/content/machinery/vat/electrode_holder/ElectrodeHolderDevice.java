package com.drmangotea.tfmg.content.machinery.vat.electrode_holder;

import com.george_vi.electroenergetics.devices.device.DevicesSavedData;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;
import com.george_vi.electroenergetics.foundation.device.SimpleElectricalDevice;
import com.george_vi.electroenergetics.simulation.BridgeCollector;
import com.george_vi.electroenergetics.simulation.SimulationResults;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ElectrodeHolderDevice extends SimpleElectricalDevice {
	public ElectrodeHolderBlockEntity be;
	public double resistance;
	
	//@Override public int getMaxVoltage() { return 20000; }
	//@Override public int getMaxCurrent() { return 400; }
	
	public ElectrodeHolderDevice(Level level, BlockPos pos, DevicesSavedData deviceSD, SimulatedDeviceType<?> type) { super(level, pos, deviceSD, type); }
	
	@Override
	public void preTick(BridgeCollector bridges) {
		if (be != null && level.isLoaded(pos)) {resistance = be.resistance();}
		bridges.builder(pos).resistor(0, 1, resistance);
	}
	
	@Override
	public void postTick(SimulationResults results) {
		if (be == null && level.isLoaded(pos) && level.getBlockEntity(pos) instanceof ElectrodeHolderBlockEntity ehbe)
			be = ehbe;
		if (be == null) return;
		if (be.isRemoved()) {
			be = null;
		} else {
			be.setVoltage(Math.abs(results.getVoltageAt(pos, 0, 1)));
			be.setAmperage(Math.abs(results.getCurrentThrough(pos, 0, 1)));
		}
	}
}