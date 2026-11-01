package com.drmangotea.tfmg.content.electricity.cee_base;

import com.drmangotea.tfmg.content.electricity.base.IHaveMultimeterInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class CEEElectricBlockEntity extends SmartBlockEntity implements IHaveMultimeterInformation {
	public double voltage, amperage;
	
	public CEEElectricBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }
	
	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) { }
	
	public void setVoltage(double voltage) { this.voltage = voltage; }
	public void setAmperage(double amperage) { this.amperage = amperage; }
	public double getPowerUsage() { return voltage*amperage; }
	public abstract boolean isOperational();
	
	@Override
	protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
		if (!clientPacket) {
			compound.putDouble("Voltage", voltage);
			compound.putDouble("Amperage", amperage);
		}
		super.write(compound,registries , clientPacket);
	}
	
	@Override
	protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
		super.read(compound,registries , clientPacket);
		if (!clientPacket) {
			voltage = compound.getDouble("Voltage");
			amperage = compound.getDouble("Amperage");
		}
	}
	
	@Override
	public boolean makeMultimeterTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		return false;
	}
}