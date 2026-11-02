package com.drmangotea.tfmg.content.electricity.lights;

import com.george_vi.electroenergetics.devices.device.DevicesSavedData;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;
import com.george_vi.electroenergetics.foundation.device.SimpleElectricalDevice;
import com.george_vi.electroenergetics.foundation.device.SpawnPreventingDevice;
import com.george_vi.electroenergetics.simulation.BridgeCollector;
import com.george_vi.electroenergetics.simulation.SimulationResults;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class LightBulbDevice extends SimpleElectricalDevice implements SpawnPreventingDevice {
	LightBulbBlockEntity be;
	
	public LightBulbDevice(Level level, BlockPos pos, DevicesSavedData deviceSD, SimulatedDeviceType<?> type)
	{ super(level, pos, deviceSD, type); }
	
	//public int getMaxCurrent() { return 1; } // 1A
	
	@Override
	public void preTick(BridgeCollector bridges) { bridges.builder(pos).resistor(0, 1, 400); }
	
	@Override
	public void postTick(SimulationResults results) {
		if (be == null && level.isLoaded(pos) && level.getBlockEntity(pos) instanceof LightBulbBlockEntity lbbe)
			be = lbbe;
		if (be == null) return;
		if (be.isRemoved()) be = null;
		else {
			double v = Math.abs(results.getVoltageAt(pos, 0, 1));
			be.setVoltage(v);
			be.setAmperage(Math.abs(results.getCurrentThrough(pos, 0, 1)));
			be.setLight((int) Math.min(v / 2, 15)); //max voltage = 30V
		}
	}
}