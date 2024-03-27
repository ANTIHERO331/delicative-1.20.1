package net.delicative.item;

import net.delicative.block.DelicativeBlocks;
import net.delicative.block.StreetlightBaseBlock;
import net.delicative.block.StreetlightBlock;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class StreetlightItem extends BlockItem {
    public StreetlightItem(Settings settings) {
        super(DelicativeBlocks.STREETLIGHT_BASE, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResult.FAIL;

        // other code here

        if (player.isSneaking() || !performSpecialPlace(new ItemPlacementContext(context), context.getBlockPos()).isAccepted()) {
            return this.place(new ItemPlacementContext(context));
        }
        return ActionResult.PASS;
    }

    protected ActionResult performSpecialPlace(ItemPlacementContext context, BlockPos hitBlockPos) {
        World world = context.getWorld();
        BlockState targetState = world.getBlockState(hitBlockPos);
        ItemStack blockStack = context.getStack();
        PlayerEntity player = context.getPlayer();

        if (targetState.getBlock() instanceof StreetlightBaseBlock streetlightBase) {
            int height = streetlightBase.getHeight(world, hitBlockPos);
            if (height > 8) return ActionResult.PASS;
            BlockPos placePos = hitBlockPos.up(height);
            BlockState placedState = DelicativeBlocks.STREETLIGHT_ROD.getPlacementState(context);
            if (placedState == null) return ActionResult.PASS;

            if (world.getBlockState(placePos).isAir()) {
                world.setBlockState(hitBlockPos, placedState);
                if (!targetState.isOf(placedState.getBlock())) {
                    world.setBlockState(hitBlockPos, Blocks.AIR.getDefaultState());
                    return ActionResult.PASS;
                }

                this.postPlacement(placePos, world, player, blockStack, placedState);
                placedState.getBlock().onPlaced(world, placePos, placedState, player, blockStack);
                if (player instanceof ServerPlayerEntity serverPlayer) {
                    Criteria.PLACED_BLOCK.trigger(serverPlayer, placePos, blockStack);
                }

                BlockSoundGroup blockSoundGroup = placedState.getSoundGroup();
                world.playSound(player, placePos, this.getPlaceSound(placedState), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 2.0f, blockSoundGroup.getPitch() * 0.8f);
                world.emitGameEvent(GameEvent.BLOCK_PLACE, placePos, GameEvent.Emitter.of(player, placedState));
                if (player == null || !player.getAbilities().creativeMode) {
                    blockStack.decrement(1);
                }
                return ActionResult.success(world.isClient);
            }
        }
        return ActionResult.PASS;
    }
}
