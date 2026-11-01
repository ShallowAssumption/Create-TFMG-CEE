package com.drmangotea.tfmg.content.electricity.utilities.traffic_light;

import com.drmangotea.tfmg.base.lang.TFMGLang;
import com.drmangotea.tfmg.content.electricity.cee_base.CEEElectricBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TrafficLightBlockEntity extends CEEElectricBlockEntity {
	protected ScrollValueBehaviour timerLength;
	public LerpedFloat glow = LerpedFloat.linear();
	LightPos light = LightPos.RED;
	public int timer = 180;

	public TrafficLightBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        timerLength = new TimerScrollBehaviour(TFMGLang.translateDirect("traffic_light.timer"), this, new TrafficLightScrollSlot()).between(180, 60 * 20 * 60);
        timerLength.withFormatter(this::format);
        timerLength.withCallback(value -> timer = value);
        timerLength.setValue(2);
        behaviours.add(timerLength);
    }

    private String format(int value) {
        if (value < 60)
            return value + "t";
        if (value < 20 * 60)
            return (value / 20) + "s";
        return (value / 20 / 60) + "m";
    }

	@Override
	public boolean isOperational() { return voltage>0&&amperage>0; }
	//TODO: figure out how to make this work with the renderer.
	// Moving the logic to the device ain't enough.

    @Override
    public void tick() {
        if (level == null) return;
        super.tick();

		if(!level.isClientSide) return;
		if(timer>0) { timer--; }
		
        int halfTimer = timerLength.getValue() / 2;
		int eighth = Math.min(30, halfTimer / 4);

		if (timer < halfTimer - eighth && timer > Math.min(60, halfTimer / 4)) {
			light = LightPos.GREEN;
		} else if (timer > halfTimer + eighth) {
			light = LightPos.RED;
		} else {
			light = LightPos.AMBER;
		}

        if(timer == 0){
            glow.setValue(0);
            timer = timerLength.getValue();
        }
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound,registries , clientPacket);
        timer = compound.getInt("Timer");
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound,registries , clientPacket);
        compound.putInt("Timer", timer);
    }
	
	public enum LightPos {
		GREEN(0x4CFF00, 0f),
		AMBER(0xF78000, 0.3125f),
		RED(0xE22B16, 0.625f),
		;
		
		public final int color;
		public final float offset;
		
		LightPos(int color, float offset) {
			this.color = color;
			this.offset = offset;
		}
	}
}
