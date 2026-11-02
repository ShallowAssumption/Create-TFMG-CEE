package com.drmangotea.tfmg.content.electricity.lights;

import com.drmangotea.tfmg.content.electricity.cee_base.CEEElectricBlockEntity;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.drmangotea.tfmg.content.electricity.lights.LightBulbBlock.LIGHT;

public class LightBulbBlockEntity extends CEEElectricBlockEntity {
    public LerpedFloat glow = LerpedFloat.linear();
	public DyeColor color = DyeColor.WHITE;

    public LightBulbBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
	
	@Override
	public boolean isOperational() {
		return true;
	}

    @Override
    public void tick() {
        super.tick();
		glow.tickChaser();
    }
	
	public void setLight(int light) {
		if (level == null) return;
		if (light != getBlockState().getValue(LIGHT)) {
			level.setBlock(getBlockPos(), getBlockState().setValue(LIGHT, light), 2);
		}
		glow.chase(light, 0.4, LerpedFloat.Chaser.EXP);
		if (!level.isClientSide) { this.sendData(); }
	}
	
    public void setColor(DyeColor color) {
        if (color == DyeColor.BLACK || color == DyeColor.LIGHT_GRAY || color == DyeColor.GRAY)
            return;

        this.color = color;
        notifyUpdate();
    }

    public float resistance() {
        return 400;
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound,registries , clientPacket);
        NBTHelper.writeEnum(compound,"color",color);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound,registries , clientPacket);
        color = NBTHelper.readEnum(compound,"color",DyeColor.class);
    }
}