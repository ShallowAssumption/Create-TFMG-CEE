package com.drmangotea.tfmg.content.electricity.utilities.polarizer;

import com.drmangotea.tfmg.base.TFMGShapes;
import com.drmangotea.tfmg.base.TFMGUtils;
import com.drmangotea.tfmg.base.annotation.NothingNullByDefault;
import com.drmangotea.tfmg.content.electricity.cee_base.blocks.TFMGHorizontalDirectionalDeviceBlock;
import com.drmangotea.tfmg.registry.TFMGBlockEntities;
import com.drmangotea.tfmg.registry.TFMGSimulatedDevices;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

import static com.drmangotea.tfmg.content.electricity.cee_base.TFMGNodeConfigurations.POLARIZER;

@NothingNullByDefault
public class PolarizerBlock extends TFMGHorizontalDirectionalDeviceBlock<PolarizerDevice, PolarizerBlockEntity> {
    public PolarizerBlock(Properties p_54120_) {
        super(p_54120_);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof PolarizerBlockEntity be)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        ItemStack output = be.outputInventory.getStackInSlot(0);
        if (!output.isEmpty()) {
            if (!level.isClientSide) {
                ItemStack extracted = output.copy();
                be.outputInventory.setStackInSlot(0, ItemStack.EMPTY);
                if (!player.getInventory().add(extracted)) {
                    player.drop(extracted, false);
                }
                be.sendData();
                be.setChanged();
            }
            return ItemInteractionResult.SUCCESS;
        }

        if (be.capacitorPercentage > 0 || TFMGUtils.returnItemToInventory(be.inventory, 0, player, hand)) {
            return ItemInteractionResult.SUCCESS;
        }

        if (be.inventory.isEmpty()
                && !stack.isEmpty()
                && be.outputInventory.getStackInSlot(0).isEmpty()
                && PolarizerCommons.getRecipe(level, stack).isPresent()) {
            if (!level.isClientSide) {
                ItemStack held = player.getItemInHand(hand);
                ItemStack toInsert = held.copyWithCount(1);
                be.inventory.insertItem(0, toInsert, false);
                held.shrink(1);
                be.sendData();
                be.setChanged();
            }
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state, level, pos, newState);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return TFMGShapes.POLARIZER.get(state.getValue(FACING));
    }

    @Override
    public Class<PolarizerBlockEntity> getBlockEntityClass() {
        return PolarizerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PolarizerBlockEntity> getBlockEntityType() {
        return TFMGBlockEntities.POLARIZER.get();
    }
	
	//CEE stuff
	@Override
	public Vec3 getNodePosition(Level level, BlockPos pos, BlockState state, int id) { return POLARIZER.rotate(facingToRot(state)).getNodePos(Direction.DOWN, id); }
	@Override
	public Map<Integer, Vec3> getNodePositions(Level level, BlockPos pos, BlockState state) { return POLARIZER.rotate(facingToRot(state)).getNodes(Direction.DOWN);}
	@Override
	public SimulatedDeviceType<PolarizerDevice> getDevice() { return TFMGSimulatedDevices.POLARIZER.get(); }
}
