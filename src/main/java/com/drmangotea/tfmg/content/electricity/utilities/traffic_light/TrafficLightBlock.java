package com.drmangotea.tfmg.content.electricity.utilities.traffic_light;

import com.drmangotea.tfmg.base.TFMGShapes;
import com.drmangotea.tfmg.base.annotation.NothingNullByDefault;
import com.drmangotea.tfmg.content.electricity.cee_base.blocks.TFMGHorizontalDirectionalDeviceBlock;
import com.drmangotea.tfmg.registry.TFMGBlockEntities;
import com.drmangotea.tfmg.registry.TFMGSimulatedDevices;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

import static com.drmangotea.tfmg.content.electricity.cee_base.TFMGNodeConfigurations.LIGHT;

@NothingNullByDefault
public class TrafficLightBlock extends TFMGHorizontalDirectionalDeviceBlock<TrafficLightDevice, TrafficLightBlockEntity> {
	public TrafficLightBlock(Properties p) {
		super(p);
	}

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state, level, pos, newState);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return TFMGShapes.TRAFFIC_LIGHT;
    }

    @Override
    public Class<TrafficLightBlockEntity> getBlockEntityClass() {
        return TrafficLightBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TrafficLightBlockEntity> getBlockEntityType() {
        return TFMGBlockEntities.TRAFFIC_LIGHT.get();
    }

	//CEE stuff
	@Override
	public SimulatedDeviceType<TrafficLightDevice> getDevice() { return TFMGSimulatedDevices.TRAFFIC_LIGHT.get(); }
	
	@Override
	public Vec3 getNodePosition(Level level, BlockPos pos, BlockState state, int id) { return LIGHT.rotate(facingToRot(state)).getNodePos(Direction.DOWN, id); }
	
	@Override
	public Map<Integer, Vec3> getNodePositions(Level level, BlockPos pos, BlockState state) { return LIGHT.rotate(facingToRot(state)).getNodes(Direction.DOWN);}
}
