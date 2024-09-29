package net.mokai.quicksandrehydrated.block.quicksands;

import net.mokai.quicksandrehydrated.block.quicksands.core.SinkableBase;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mokai.quicksandrehydrated.block.quicksands.core.SinkableBehavior;
import net.mokai.quicksandrehydrated.entity.SinkModules.SinkDataBase;
import net.mokai.quicksandrehydrated.entity.entityQuicksandVar;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static org.joml.Math.abs;
import static org.joml.Math.clamp;

public class Sinkable extends SinkableBase {
    public Sinkable(Properties pProperties, SinkableBehavior QSB) {super(pProperties, QSB);}

    //TODO: generalize this

    @Override
    public void firstTouch(Entity pEntity, Level pLevel) {
        trySetCoverage(pEntity);

        entityQuicksandVar pQSEnt = (entityQuicksandVar)pEntity;

        if (!pQSEnt.hasSinkModule(SinkDataBase.class)) {
            SinkDataBase qs_module = new SinkDataBase();
            pQSEnt.addSinkModule(qs_module);
        }

        if (pEntity.getDeltaMovement().y <= -0.333) {
            double mvt = pEntity.getDeltaMovement().y;
            mvt = clamp(mvt, -0.666, 0);
            pLevel.playSound(pEntity, pEntity.blockPosition(), SoundEvents.MUD_FALL, SoundSource.BLOCKS, abs(0.3F+(float) mvt), (pLevel.getRandom().nextFloat() * 0.1F) + 0.45F);
            pLevel.playSound(pEntity, pEntity.blockPosition(), SoundEvents.SOUL_SOIL_STEP, SoundSource.BLOCKS, abs(0.3F+(float) mvt), (pLevel.getRandom().nextFloat() * 0.1F) + 0.45F);
        }
    }


    @Override
    public void applyQuicksandEffects(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {

        super.applyQuicksandEffects(pState, pLevel, pPos, pEntity);

        Random rand = new Random();
        if (rand.nextInt(20*8) < 1) { // every 6 seconds
            pLevel.playSound(pEntity, pEntity.blockPosition(), SoundEvents.SILVERFISH_STEP, SoundSource.BLOCKS, 0.25F, (pEntity.level().getRandom().nextFloat() * 0.1F) + 0.5F);
        }

    }

}
