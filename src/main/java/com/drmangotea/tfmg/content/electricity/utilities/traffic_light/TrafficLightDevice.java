package com.drmangotea.tfmg.content.electricity.utilities.traffic_light;

import com.george_vi.electroenergetics.devices.device.DevicesSavedData;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;
import com.george_vi.electroenergetics.foundation.device.SimpleElectricalDevice;
import com.george_vi.electroenergetics.simulation.BridgeCollector;
import com.george_vi.electroenergetics.simulation.SimulationResults;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class TrafficLightDevice extends SimpleElectricalDevice {
	public TrafficLightBlockEntity be;
	
	public TrafficLightDevice(Level level, BlockPos pos, DevicesSavedData deviceSD, SimulatedDeviceType<?> type) {
		super(level, pos, deviceSD, type);
	}
	
	@Override
	public void preTick(BridgeCollector bridges) { bridges.builder(pos).resistor(0, 1, 175); }
	
	@Override
	public void postTick(SimulationResults results) {
		if (be == null && level.isLoaded(pos) && level.getBlockEntity(pos) instanceof TrafficLightBlockEntity tlbe)
			be = tlbe;
		if (be == null) return;
		if (be.isRemoved()) {
			be = null;
		} else {
			double v = Math.abs(results.getVoltageAt(pos, 0, 1));
			double a = Math.abs(results.getCurrentThrough(pos, 0, 1));
			be.setVoltage(v);
			be.setAmperage(a);
			be.glow.chase(v*a,0.4, LerpedFloat.Chaser.EXP);
		}
	}
}