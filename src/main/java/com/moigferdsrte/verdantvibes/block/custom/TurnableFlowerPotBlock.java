package com.moigferdsrte.verdantvibes.block.custom;

import com.moigferdsrte.verdantvibes.VerdantVibes;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;

public class TurnableFlowerPotBlock extends FlowerPotBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final Identifier ADVANCEMENT_ID = VerdantVibes.id("potters_pivot");

    public TurnableFlowerPotBlock(Block content, BlockBehaviour.Properties properties) {
        super(content, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NonNull InteractionResult useItemOn(
            @NonNull ItemStack stack,
            @NonNull BlockState state,
            @NonNull Level level,
            @NonNull BlockPos pos,
            Player player,
            @NonNull InteractionHand hand,
            @NonNull BlockHitResult hit
    ) {
        return player.isShiftKeyDown()
                ? this.rotatePot(state, level, pos, player)
                : super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(
            @NonNull BlockState state,
            @NonNull Level level,
            @NonNull BlockPos pos,
            Player player,
            @NonNull BlockHitResult hit
    ) {
        if (!player.isShiftKeyDown()) {
            return super.useWithoutItem(state, level, pos, player, hit);
        }

        return this.rotatePot(state, level, pos, player);
    }

    private InteractionResult rotatePot(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.getServer() != null && player instanceof ServerPlayer serverPlayer) {
            AdvancementHolder advancement = level.getServer().getAdvancements().get(ADVANCEMENT_ID);
            if (advancement != null) {
                serverPlayer.getAdvancements().award(advancement, "planted_pot");
            }
        }
        level.setBlock(pos, state.rotate(Rotation.CLOCKWISE_90), 3);
        level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 0.25F, 1.5F);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onPlace(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if ((oldState.getBlock() instanceof TurnableFlowerPotBlock || oldState.getBlock() instanceof FlowerPotBlock)
                && state.getBlock() != oldState.getBlock()) {
            level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS);
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}
