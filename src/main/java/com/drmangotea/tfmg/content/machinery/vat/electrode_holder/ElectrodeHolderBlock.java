package com.drmangotea.tfmg.content.machinery.vat.electrode_holder;

import com.drmangotea.tfmg.base.TFMGUtils;
import com.drmangotea.tfmg.base.annotation.NothingNullByDefault;
import com.drmangotea.tfmg.content.electricity.cee_base.blocks.TFMGDeviceBlock;
import com.drmangotea.tfmg.content.machinery.vat.electrode_holder.electrode.Electrode;
import com.drmangotea.tfmg.registry.TFMGBlockEntities;
import com.drmangotea.tfmg.registry.TFMGDataComponents;
import com.drmangotea.tfmg.registry.TFMGSimulatedDevices;
import com.george_vi.electroenergetics.devices.device.SimulatedDeviceType;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.drmangotea.tfmg.content.electricity.cee_base.TFMGNodeConfigurations.TWO_NODES_ON_FACE;

@NothingNullByDefault
public class ElectrodeHolderBlock extends TFMGDeviceBlock<ElectrodeHolderDevice, ElectrodeHolderBlockEntity> {
    public ElectrodeHolderBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		AtomicBoolean success = new AtomicBoolean(false);
        if (stack.isEmpty()) {
            withBlockEntityDo(level, pos, (electrodeHolder) -> {
                if (!electrodeHolder.inventory.isEmpty()) {
                    TFMGUtils.returnItemToInventory(electrodeHolder.inventory, 0, player, hand);
                    electrodeHolder.onInventoryChanged(0);
                    success.set(true);
                }
            });
        } else {
			withBlockEntityDo(level, pos, (electrodeHolder) -> {
				var electrode = stack.getOrDefault(TFMGDataComponents.ELECTRODE, Electrode.Stored.NONE).electrode().value();
				if (electrode.isValid()) {
					boolean doInsert = true;
					if (!electrodeHolder.inventory.isEmpty()) {
						if (ItemStack.isSameItemSameComponents(stack, electrodeHolder.inventory.getStackInSlot(0))) {
							doInsert = false;
						} else {
							TFMGUtils.returnItemToInventory(electrodeHolder.inventory, 0, player, hand);
							electrodeHolder.onInventoryChanged(0);
						}
					}
					if (doInsert) {
						electrodeHolder.inventory.insertItem(0, stack.copyWithCount(1), false);
						electrodeHolder.onInventoryChanged(0);
						stack.shrink(1);
						success.set(true);
					}
				}
			});
		}
        if (success.get()) {
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
	
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state, level, pos, newState);
    }
    @Override
    public Class<ElectrodeHolderBlockEntity> getBlockEntityClass() {
        return ElectrodeHolderBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ElectrodeHolderBlockEntity> getBlockEntityType() {
        return TFMGBlockEntities.ELECTRODE_HOLDER.get();
    }
	
	//CEE stuff
	public SimulatedDeviceType<ElectrodeHolderDevice> getDevice() {return TFMGSimulatedDevices.ELECTRODE_HOLDER.get();}
	@Override
	public Vec3 getNodePosition(Level level, BlockPos pos, BlockState state, int id) { return TWO_NODES_ON_FACE.getNodePos(Direction.UP, id); }
	@Override
	public Map<Integer, Vec3> getNodePositions(Level level, BlockPos pos, BlockState state) { return TWO_NODES_ON_FACE.getNodes(Direction.UP);}
}
