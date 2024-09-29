package net.mokai.quicksandrehydrated.block.quicksands;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.mokai.quicksandrehydrated.block.quicksands.core.SinkableBase;
import net.mokai.quicksandrehydrated.block.quicksands.core.SinkableBehavior;
import net.mokai.quicksandrehydrated.entity.EntityBubble;
import net.mokai.quicksandrehydrated.entity.entityQuicksandVar;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Random;

import static org.joml.Math.*;

public class DeepMudBlock extends SinkableBase {

    public String coverageTexture() {
        return "qsrehydrated:textures/entity/coverage/mud_coverage.png";
    }

    //TODO: separate this out into two separate classes; one partially solid block that handles depths 1-3,
    //TODO: and use a fourth for bottomless, if even required


    public double maxDepth;
    public DeepMudBlock(Properties pProperties, SinkableBehavior QSB, double mudDepth) {
        super(pProperties, QSB);
        this.maxDepth = mudDepth;
    }


    public double getTugStrengthVertical(double depth) {
        return 0.0d;
    }


    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {

        super.entityInside(pState, pLevel, pPos, pEntity);
        tryApplyCoverage(pState, pLevel, pPos, pEntity);

    }

    @Override
    public void applyQuicksandEffects(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos,
                                      @NotNull Entity pEntity) {
    super.applyQuicksandEffects(pState, pLevel, pPos, pEntity);

        if (pEntity instanceof EntityBubble) {
            return;
        }

        double depth = getDepth(pLevel, pPos, pEntity);
        if (depth > 0) {

            double walk = getWalkSpeed(depth);
            entityQuicksandVar eqs = (entityQuicksandVar) pEntity;

            Random rng = new Random();

            // get entity momentum, to determine if moving.
            Vec3 momentumVec3 = pEntity.getDeltaMovement();
            double momentumValue = momentumVec3.length()*100;

            // if they're moving, turn some of their horizontal momentum into sinking
            if (momentumValue > 0.0001) {
                Vec3 sinkVec = pEntity.getDeltaMovement();
                sinkVec = new Vec3(sinkVec.x, 0.0, sinkVec.z);
                double sinkAmount = sinkVec.length() * 0.1;
                eqs.addQuicksandAdditive(new Vec3(0.0, -sinkAmount, 0.0));
            }

            // random slurping
            momentumVec3 = new Vec3(momentumVec3.x, 0, momentumVec3.z);
            double momentum = momentumVec3.length();
            double rngVal = rng.nextDouble();



            boolean canSink = depth < maxDepth;

            if (canSink) {

                // in the end, an RNG value has to be BELOW slurpChance to succeed.
                // so a large value = not likely at all
                // small value = very likely

                double slurpLerp = depth/maxDepth; // as you approach max depth, this approaches 1.0 ...
//                    slurpLerp = Math.pow(slurpLerp, 2); // curved down.
                // slurpLerp starts small, but gets large very quickly near 1.0.

                double movementChance = 0.0;
                if (walk != 0.0) {
                    movementChance = (momentum / walk) * 0.1; // doesn't have much effect
                }
                // attempts to correct for the thickness, but isn't quite right ... gets comparatively larger as walk increases.

                double slurpChance = 1.0;
                slurpChance -= movementChance;

                if (momentumValue*100 > 0.01) {
                    slurpChance -= 0.05;
                }

                slurpChance = lerp(slurpChance, 1.0, slurpLerp);

                if (rngVal > slurpChance) {
                    slurpEntity(pState, pEntity);
                }

            }



        }

    }

    @Override
    public void quicksandTugMove(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity, double depth) {

        entityQuicksandVar es = (entityQuicksandVar) pEntity;
        Vec3 currentPos = pEntity.getPosition(0);

        // Get the Previous Position variable
        Vec3 prevPos = es.getPreviousPosition();

        double lerpAmountHorizontal = getTugPointSpeed(depth);

        Vec3 newPrevPos = new Vec3(
            lerp(prevPos.x(), currentPos.x(), lerpAmountHorizontal),
            lerp(prevPos.y(), currentPos.y(), 1.0), // always go right to player Y level
            lerp(prevPos.z(), currentPos.z(), lerpAmountHorizontal)
        );

        // move previous pos towards player by set amount
        es.setPreviousPosition(newPrevPos);

    }

    public void slurpEntity(@NotNull BlockState pState, @NotNull Entity pEntity) {

        Random rng = new Random();

        double rngVal = rng.nextDouble();

        float gain = (float) ((rngVal * 0.75F) - 0.3F);
        gain = Math.max(gain, 0.0F);

        double pulldown = rngVal * -0.1;
        pulldown -= 0.025;

        pEntity.addDeltaMovement(new Vec3(0.0, pulldown, 0.0));
        pEntity.level().playSound(pEntity, pEntity.blockPosition(), SoundEvents.HONEY_BLOCK_SLIDE, SoundSource.BLOCKS, gain, (pEntity.level().getRandom().nextFloat() * 0.1F) + 0.45F);

        for (int i = 0; i < 5; i++) {
            spawnParticles(pEntity.level(), pEntity.blockPosition());
        }

    }

    @Override
    public void struggleAttempt(@NotNull BlockState pState, @NotNull Entity pEntity, double struggleAmount) {

        double min = 0.0;
        struggleAmount *= 0.05;
        double struggleForce = min+struggleAmount;

        Random rng = new Random();
        double rngVal = rng.nextDouble(0.5, 1.0);
        struggleForce *= rngVal;

        pEntity.addDeltaMovement(new Vec3(0.0, struggleForce, 0.0));
        pEntity.level().playSound(pEntity, pEntity.blockPosition(), SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 0.25F, (pEntity.level().getRandom().nextFloat() * 0.1F) + 0.45F);

        for (int i = 0; i < 5; i++) {
            spawnParticles(pEntity.level(), pEntity.blockPosition());
        }

    }



    public static final Vector3f MUD_COLOR = Vec3.fromRGB24(4666151).toVector3f();

    // normal block things v


    @Override
    public void firstTouch(Entity pEntity, Level pLevel) {
        trySetCoverage(pEntity);
        if (pEntity.getDeltaMovement().y <= -0.5) {
            pEntity.level().playSound(pEntity, pEntity.blockPosition(), SoundEvents.HONEY_BLOCK_FALL, SoundSource.BLOCKS, 0.4F, (pEntity.level().getRandom().nextFloat() * 0.1F) + 0.45F);
        }
    }



    private static void spawnParticles(Level pLevel, BlockPos pPos) {

        RandomSource randomsource = pLevel.random;

        Direction direction = Direction.UP;

        // taken from redstone ore block code
        BlockPos blockpos = pPos.relative(direction);

        Direction.Axis direction$axis = direction.getAxis();
        double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getStepX() : (double) randomsource.nextFloat();
        double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double) direction.getStepY() : (double) randomsource.nextFloat();
        double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getStepZ() : (double) randomsource.nextFloat();
//        pLevel.addParticle(???, d1, d2, d3, 0.0D, 0.0, 0.0D);

        Vector3f MUD_COLOOR = Vec3.fromRGB24(4666151).toVector3f();
        pLevel.addParticle(new DustParticleOptions(MUD_COLOOR, 1.0F), (double) pPos.getX() + d1, (double) pPos.getY() + d2, (double) pPos.getZ() + d3, 0.0D, 0.0D, 0.0D);


    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        //spawnParticles(pLevel, pPos);
    }


}
