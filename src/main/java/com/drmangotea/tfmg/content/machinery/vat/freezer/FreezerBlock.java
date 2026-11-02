package com.drmangotea.tfmg.content.machinery.vat.freezer;

import com.drmangotea.tfmg.base.annotation.NothingNullByDefault;
import com.drmangotea.tfmg.content.electricity.cee_base.blocks.TFMGDeviceBlock;
import com.drmangotea.tfmg.registry.TFMGBlockEntities;
import com.drmangotea.tfmg.registry.TFMGSimulatedDevices;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

import static com.drmangotea.tfmg.content.electricity.cee_base.TFMGNodeConfigurations.TWO_NODES_ON_FACE;

@NothingNullByDefault
public class FreezerBlock extends TFMGDeviceBlock<FreezerDevice, FreezerBlockEntity> {
    public FreezerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state, level, pos, newState);
    }
    @Override
    public Class<FreezerBlockEntity> getBlockEntityClass() {
        return FreezerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends FreezerBlockEntity> getBlockEntityType() {
        return TFMGBlockEntities.FREEZER.get();
    }
	
	//CEE Stuff
	@Override
	public Vec3 getNodePosition(Level l, BlockPos p, BlockState s, int i) { return TWO_NODES_ON_FACE.getNodePos(Direction.DOWN, i); }
	@Override
	public Map<Integer, Vec3> getNodePositions(Level l, BlockPos p, BlockState s) { return TWO_NODES_ON_FACE.getNodes(Direction.DOWN); }
	@Override
	public SimulatedDeviceType<FreezerDevice> getDevice() { return TFMGSimulatedDevices.FREEZER.get(); }
}
