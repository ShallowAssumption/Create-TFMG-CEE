package com.drmangotea.tfmg.registry;

import com.drmangotea.tfmg.TFMG;
/*import com.drmangotea.tfmg.content.electricity.lights.LightBulbDevice;
import com.drmangotea.tfmg.content.electricity.network.large_switch.LargeSwitchDevice;
import com.drmangotea.tfmg.content.electricity.storage.AccumulatorDevice;
import com.drmangotea.tfmg.content.electricity.utilities.polarizer.PolarizerDevice;*/
import com.drmangotea.tfmg.content.electricity.utilities.traffic_light.TrafficLightDevice;
import com.drmangotea.tfmg.content.machinery.vat.electrode_holder.ElectrodeHolderDevice;
import com.drmangotea.tfmg.content.machinery.vat.freezer.FreezerDevice;

import com.george_vi.electroenergetics.devices.device.SimulatedDevice;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceFactory;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.drmangotea.tfmg.TFMGRegistries.SIMULATED_DEVICE_TYPE_REGISTRY;

public class TFMGSimulatedDevices {
	private static final DeferredRegister<SimulatedDeviceType<?>>
		DEVICES = DeferredRegister.create(SIMULATED_DEVICE_TYPE_REGISTRY, TFMG.MOD_ID);
	
	public static final DeferredHolder<SimulatedDeviceType<?>, SimulatedDeviceType<FreezerDevice>>
		FREEZER = device("freezer", (t,l,p,sd) -> new FreezerDevice(l,p,sd,t));
	
	public static final DeferredHolder<SimulatedDeviceType<?>, SimulatedDeviceType<ElectrodeHolderDevice>>
		ELECTRODE_HOLDER = device("electrode_holder", (t,l,p,sd) -> new ElectrodeHolderDevice(l,p,sd,t));
	
	public static final DeferredHolder<SimulatedDeviceType<?>, SimulatedDeviceType<TrafficLightDevice>>
		TRAFFIC_LIGHT = device("traffic_light", (t,l,p,sd) -> new TrafficLightDevice(l,p,sd,t));
	
	/*public static final DeferredHolder<SimulatedDeviceType<?>, SimulatedDeviceType<AccumulatorDevice>>
		ACCUMULATOR = device("accumulator", AccumulatorDevice::new);
		
	public static final DeferredHolder<SimulatedDeviceType<?>, SimulatedDeviceType<LightBulbDevice>>
		LIGHT = device("light_bulb", LightBulbDevice::new);
	
	public static final DeferredHolder<SimulatedDeviceType<?>, SimulatedDeviceType<PolarizerDevice>>
		POLARIZER = device("polarizer", PolarizerDevice::new);
	
	public static final DeferredHolder<SimulatedDeviceType<?>, SimulatedDeviceType<LargeSwitchDevice>>
		LARGE_SWITCH = device("large_switch", LargeSwitchDevice::new);*/
	
	static <T extends SimulatedDevice> DeferredHolder<SimulatedDeviceType<?>, SimulatedDeviceType<T>> device (
		String name, SimulatedDeviceFactory<T> factory
	) { return DEVICES.register(name, () -> new SimulatedDeviceType<>(TFMG.asResource(name), factory)); }
	
	public static void register(IEventBus bus) { DEVICES.register(bus); }
}