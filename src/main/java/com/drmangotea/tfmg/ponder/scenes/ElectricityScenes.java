package com.drmangotea.tfmg.ponder.scenes;

import com.drmangotea.tfmg.content.machinery.misc.winding_machine.WindingMachineBlockEntity;
import com.drmangotea.tfmg.registry.TFMGBlocks;
import com.drmangotea.tfmg.registry.TFMGDataComponents;
import com.drmangotea.tfmg.registry.TFMGItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ElectricityScenes {

    public static void windingMachine(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("winding_machine", "Winding Machine");
        scene.showBasePlate();
        scene.configureBasePlate(0, 0, 5);

        Selection windingMachine = util.select().position(2, 2, 2);

        Selection largeCog = util.select().position(1, 0, 5);
        Selection smallerKinetics = util.select().fromTo(2, 1, 3, 2, 1, 5);

        var windingMachineElement = scene.world().showIndependentSection(windingMachine, Direction.UP);
        scene.world().moveSection(windingMachineElement, new Vec3(0d, -1d, 0d), 0);
        scene.overlay().showText(40)
                .attachKeyFrame()
                .text("Winding Machines are used to turn a spool around an item");
        scene.idle(50);
        scene.overlay().showText(40)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(windingMachine.getCenter().subtract(0.5, 0.5, 0))
                .text("Spools can be inserted by right clicking.");
        scene.idle(20);
        scene.overlay().showControls(windingMachine.getCenter().subtract(0.5, 0.5, 0), Pointing.DOWN, 5).rightClick()
                .withItem(new ItemStack(TFMGItems.ALUMINUM_SPOOL.get()));
        scene.world().modifyBlockEntity(new BlockPos(2, 2, 2), WindingMachineBlockEntity.class, (wm) -> wm.spoolInventory.setStackInSlot(0, new ItemStack(TFMGItems.ALUMINUM_SPOOL.get())));
        scene.idle(10);
        scene.overlay().showControls(windingMachine.getCenter().subtract(0.5, 0.5, 0), Pointing.DOWN, 5).rightClick()
                .withItem(new ItemStack(TFMGItems.CONSTANTAN_SPOOL.get()));
        scene.world().modifyBlockEntity(new BlockPos(2, 2, 2), WindingMachineBlockEntity.class, (wm) -> wm.spoolInventory.setStackInSlot(0, new ItemStack(TFMGItems.CONSTANTAN_SPOOL.get())));
        scene.idle(10);
        scene.overlay().showControls(windingMachine.getCenter().subtract(0.5, 0.5, 0), Pointing.DOWN, 5).rightClick()
                .withItem(new ItemStack(TFMGItems.COPPER_SPOOL.get()));
        scene.world().modifyBlockEntity(new BlockPos(2, 2, 2), WindingMachineBlockEntity.class, (wm) -> wm.spoolInventory.setStackInSlot(0, new ItemStack(TFMGItems.COPPER_SPOOL.get())));
        scene.idle(5);
        scene.addKeyframe();
        scene.idle(15);
        scene.rotateCameraY(180);
        scene.idle(20);
        scene.overlay().showText(40)
                .placeNearTarget()
                .pointAt(windingMachine.getCenter().subtract(-0.5, 0.6, 0))
                .text("Right clicking with an input places it in the correct position");
        scene.idle(20);
        scene.overlay().showControls(windingMachine.getCenter().subtract(-0.5, 0.6, 0), Pointing.DOWN, 5).rightClick()
                .withItem(new ItemStack(TFMGBlocks.LAMINATED_MAGNETIC_ALLOY_BLOCK.get()));
        scene.world().modifyBlockEntity(new BlockPos(2, 2, 2), WindingMachineBlockEntity.class, (wm) -> wm.inventory.setStackInSlot(0, new ItemStack(TFMGBlocks.LAMINATED_MAGNETIC_ALLOY_BLOCK.get())));
        scene.idle(25);
        scene.rotateCameraY(-180);
        scene.idle(20);
        var kineticPowerElement = scene.world().showIndependentSection(largeCog.add(smallerKinetics), Direction.DOWN);
        scene.world().setBlock(new BlockPos(2, 1, 3), AllBlocks.SHAFT.getDefaultState().setValue(ShaftBlock.AXIS, Direction.Axis.Z), false);
        scene.overlay().showText(40)
                .attachKeyFrame()
                .placeNearTarget()
                .text("When rotation is provided it will begin winding...");
        scene.world().setKineticSpeed(largeCog, -16);
        scene.world().setKineticSpeed(smallerKinetics, 32);
        //TODO: Spool doesn't rotate
        scene.world().setKineticSpeed(windingMachine, 32);
        scene.idle(120);
        scene.overlay().showText(30)
                .placeNearTarget()
                .text("...and will stop on completion!");
    }

	//TODO: Fix
    public static void windingMachineAutomation(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("winding_machine_automation", "Winding Machine Automation");
        scene.showBasePlate();
        scene.configureBasePlate(0, 0, 5);

        Selection casing = util.select().position(2, 1, 2);
        Selection windingMachine = util.select().position(2, 2, 2);
        Selection central = casing.add(windingMachine);

        Selection largeCog = util.select().position(1, 0, 5);
        Selection smallerKinetics = util.select().fromTo(2, 1, 3, 2, 1, 5);

        Selection inputBelt = util.select().fromTo(3, 1, 2, 4, 1, 2);
        Selection inputFunnel = util.select().position(3, 2, 2);
        Selection input = inputBelt.add(inputFunnel);

        Selection deployer = util.select().position(2, 4, 2);

        Selection output = util.select().fromTo(1, 1, 2, 1, 2, 2);

        var centralElement = scene.world().showIndependentSection(central, Direction.UP);
        scene.world().moveSection(centralElement, new Vec3(0d, -1d, 0d), 0);
        scene.overlay().showText(40)
                .attachKeyFrame()
                .text("Winding Machines can be automated through various means...");
        scene.idle(20);
        scene.world().moveSection(centralElement, new Vec3(0d, 1d, 0d), 10);
        scene.idle(10);
        scene.addKeyframe();
        var inputElement = scene.world().showIndependentSection(input, Direction.DOWN);
        scene.idle(10);
        scene.overlay().showOutlineWithText(inputFunnel, 50)
                .colored(PonderPalette.GREEN)
                .attachKeyFrame()
                .text("Items can be inserted into the input slot with a funnel.")
                .placeNearTarget();
        scene.world().setKineticSpeed(inputBelt, 16);
        scene.idle(40);
        scene.idle(13);
        BlockPos beltEnd = util.grid().at(4, 1, 2);
        scene.idle(35);
        scene.idle(35);
        scene.addKeyframe();
        scene.world().hideIndependentSection(inputElement, Direction.UP);
        var deployerElement = scene.world().showIndependentSection(deployer, Direction.DOWN);
        scene.world().moveSection(centralElement, new Vec3(0d, -1d, 0d), 10);
        scene.world().moveSection(deployerElement, new Vec3(0d, -1d, 0d), 0);
        scene.idle(10);
        ItemStack spool = TFMGItems.CONSTANTAN_SPOOL.asStack();
        spool.set(TFMGDataComponents.SPOOL_AMOUNT, 50);
        scene.overlay().showText(50)
                .pointAt(deployer.getCenter())
                .text("Spools can be inserted with a Deployer")
                .placeNearTarget();
        scene.overlay().showControls(util.vector().blockSurface(new BlockPos(2, 4, 2), Direction.EAST).add(0, 0.15, 0), Pointing.RIGHT, 30)
                .withItem(spool);
        scene.idle(17);
        scene.world().modifyBlockEntityNBT(deployer, DeployerBlockEntity.class,
                nbt -> nbt.put("HeldItem", spool.saveOptional(scene.world().getHolderLookupProvider())));
        scene.idle(25);
        scene.world().moveDeployer(new BlockPos(2, 4, 2), 1, 20);
        scene.idle(20);
        scene.world().modifyBlockEntityNBT(deployer, DeployerBlockEntity.class,
                nbt -> nbt.put("HeldItem", ItemStack.EMPTY.saveOptional(scene.world().getHolderLookupProvider())));
        scene.world().modifyBlockEntity(new BlockPos(2, 2, 2), WindingMachineBlockEntity.class, (wm) -> wm.spoolInventory.setStackInSlot(0, spool));
        scene.world().moveDeployer(new BlockPos(2, 4, 2), -1, 20);
        scene.idle(20);
        scene.addKeyframe();
        scene.world().hideIndependentSection(deployerElement, Direction.UP);
        var kineticPowerElement = scene.world().showIndependentSection(largeCog.add(smallerKinetics), Direction.DOWN);
        scene.world().setBlock(new BlockPos(2, 1, 3), AllBlocks.SHAFT.getDefaultState().setValue(ShaftBlock.AXIS, Direction.Axis.Z), false);
        scene.world().setKineticSpeed(largeCog, -32);
        scene.world().setKineticSpeed(smallerKinetics, 64);
        //TODO: Spool doesn't rotate
        scene.world().setKineticSpeed(windingMachine, 64);
        scene.idle(60);
        scene.addKeyframe();
        scene.world().hideIndependentSection(kineticPowerElement, Direction.UP);
        scene.world().moveSection(centralElement, new Vec3(0d, 1d, 0d), 10);
        scene.idle(10);
        var outputElement = scene.world().showIndependentSection(output, Direction.DOWN);
        scene.idle(10);
        scene.overlay().showOutlineWithText(util.select().position(1, 2, 2), 50)
                .colored(PonderPalette.GREEN)
                .attachKeyFrame()
                .text("Funnels will only extract outputs.")
                .placeNearTarget();
        scene.idle(20);
        scene.world().flapFunnel(new BlockPos(1, 2, 2), true);
        scene.world().modifyBlockEntity(new BlockPos(2, 2, 2), WindingMachineBlockEntity.class, (wm) -> wm.outputInventory.setStackInSlot(0, ItemStack.EMPTY));
    }
}
