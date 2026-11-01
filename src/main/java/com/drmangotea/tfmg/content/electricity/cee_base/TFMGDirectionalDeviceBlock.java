package com.drmangotea.tfmg.content.electricity.cee_base;

import com.drmangotea.tfmg.base.blocks.TFMGDirectionalBlock;
import com.george_vi.electroenergetics.devices.device.SimulatedDevice;
import com.george_vi.electroenergetics.foundation.device.ElectricalDeviceBlock;
import com.george_vi.electroenergetics.foundation.nodes.InWorldNodeConnection;
import com.george_vi.electroenergetics.simulation.infrastructure.InWorldNodeData;
import com.george_vi.electroenergetics.simulation.infrastructure.InfrastructureSavedData;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.LevelTickAccess;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public abstract class TFMGDirectionalDeviceBlock<T extends SimulatedDevice, R extends BlockEntity> extends TFMGDirectionalBlock implements ElectricalDeviceBlock<T>, IBE<R>, IWrenchable {
	public TFMGDirectionalDeviceBlock(Properties properties) { super(properties); }
	
	@ParametersAreNonnullByDefault
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		LevelTickAccess<Block> blockTicks = level.getBlockTicks();
		if (!blockTicks.hasScheduledTick(pos, this)) {
			level.scheduleTick(pos, this, 1);
		}
	}
	
	@ParametersAreNonnullByDefault
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		List<Integer> nodes = new ArrayList<>(this.getNodePositions(level, pos, state).keySet());
		InfrastructureSavedData sd = InfrastructureSavedData.load(level);
		sd.registerOrUpdateNodes(pos, nodes);
		super.tick(state, level, pos, random);
	}
	
	@ParametersAreNonnullByDefault
	public @NotNull BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (level instanceof ServerLevel sl && player.isCreative()) {
			InfrastructureSavedData sd = InfrastructureSavedData.load(sl);
			for(InWorldNodeData nodeData : sd.getNodesAt(pos)) {
				for(InWorldNodeConnection connection : sd.getConnections(nodeData)) {
					sd.removeConnection(connection);
				}
			}
		}
		return super.playerWillDestroy(level, pos, state, player);
	}
	
	public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
		this.removeWiresByPlayer(context.getPlayer(), context.getLevel(), context.getClickedPos());
		return IWrenchable.super.onSneakWrenched(state, context);
	}
}
