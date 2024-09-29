package net.mokai.quicksandrehydrated.block.quicksands.core;

import net.mokai.quicksandrehydrated.util.DepthCurve;
import org.joml.Vector2d;

import java.util.ArrayList;

public class SinkableBehavior {

    /**
     * This class defines a variety of common behaviors of the various  substances.
     * Construct an instance of this class by defining a new SinkableBehavior, chaining .set() methods, and then
     * feeding the result to the constructor of SinkableBase during registration in ModBlocks.
     */


    public DepthCurve bubbleChance = new DepthCurve(1d);
    public DepthCurve sinkSpeed = new DepthCurve(.01d);
    public DepthCurve walkSpeed = new DepthCurve(.5, 0);
    public DepthCurve vertSpeed = new DepthCurve(.1d);
    public DepthCurve tugStrengthHorizontal = new DepthCurve(0d);
    public DepthCurve tugStrengthVertical = new DepthCurve(0d);
    public DepthCurve tugPointSpeed = new DepthCurve(1d);

    public String coverageTexture = "quicksand_coverage";
    public String secretDeathMessage = "quicksand";
    public double secretDeathMessageChance = 0;
    public double buoyancyPoint = 2d;
    public double offset = 0;
    public double stepOutHeight = .1d;


    public SinkableBehavior setBubbleChance(DepthCurve curve) {bubbleChance = curve; return this;}
    public SinkableBehavior setBubbleChance(double chance) {bubbleChance = new DepthCurve(chance); return this;}
    public SinkableBehavior setBubbleChance(double[] chance) {bubbleChance = new DepthCurve(chance); return this;}
    public SinkableBehavior setBubbleChance(ArrayList<Vector2d> chance) {bubbleChance = new DepthCurve(chance); return this;}
    public double            getBubbleChance(double depth) {return bubbleChance.getAt(depth);}

    public SinkableBehavior setSinkSpeed(DepthCurve sinkCurve) {sinkSpeed = sinkCurve; return this;}
    public SinkableBehavior setSinkSpeed(double speed) {sinkSpeed = new DepthCurve(speed); return this;}
    public SinkableBehavior setSinkSpeed(double[] speed) {sinkSpeed = new DepthCurve(speed); return this;}
    public SinkableBehavior setSinkSpeed(ArrayList<Vector2d> speed) {sinkSpeed = new DepthCurve(speed); return this;}
    public double            getSinkSpeed(double depth) {return sinkSpeed.getAt(depth);}

    public SinkableBehavior setWalkSpeed(DepthCurve curve) {walkSpeed = curve; return this;}
    public SinkableBehavior setWalkSpeed(double speed) {walkSpeed = new DepthCurve(speed); return this;}
    public SinkableBehavior setWalkSpeed(double[] speed) {walkSpeed = new DepthCurve(speed); return this;}
    public SinkableBehavior setWalkSpeed(ArrayList<Vector2d> speed) {walkSpeed = new DepthCurve(speed); return this;}
    public double            getWalkSpeed(double depth) {return walkSpeed.getAt(depth);}

    public SinkableBehavior setVertSpeed(DepthCurve curve) {vertSpeed = curve; return this;}
    public SinkableBehavior setVertSpeed(double speed) {vertSpeed = new DepthCurve(speed); return this;}
    public SinkableBehavior setVertSpeed(double[] speed) {vertSpeed = new DepthCurve(speed); return this;}
    public SinkableBehavior setVertSpeed(ArrayList<Vector2d> speed) {vertSpeed = new DepthCurve(speed); return this;}
    public double            getVertSpeed(double depth) {return vertSpeed.getAt(depth);}

    public SinkableBehavior setTugStrengthHorizontal(DepthCurve curve) {tugStrengthHorizontal = curve; return this;}
    public SinkableBehavior setTugStrengthHorizontal(double tug) {tugStrengthHorizontal = new DepthCurve(tug); return this;}
    public SinkableBehavior setTugStrengthHorizontal(double[] tug) {tugStrengthHorizontal = new DepthCurve(tug); return this;}
    public SinkableBehavior setTugStrengthHorizontal(ArrayList<Vector2d> tug) {tugStrengthHorizontal = new DepthCurve(tug); return this;}
    public double            getTugStrengthHorizontal(double depth) {return tugStrengthHorizontal.getAt(depth);}

    public SinkableBehavior setTugStrengthVertical(DepthCurve curve) {tugStrengthVertical = curve; return this;}
    public SinkableBehavior setTugStrengthVertical(double tug) {tugStrengthVertical = new DepthCurve(tug); return this;}
    public SinkableBehavior setTugStrengthVertical(double[] tug) {tugStrengthVertical = new DepthCurve(tug); return this;}
    public SinkableBehavior setTugStrengthVertical(ArrayList<Vector2d> tug) {tugStrengthVertical = new DepthCurve(tug); return this;}
    public double            getTugStrengthVertical(double depth) {return tugStrengthVertical.getAt(depth);}

    public SinkableBehavior setTugPointSpeed(DepthCurve curve) {tugPointSpeed = curve; return this;}
    public SinkableBehavior setTugPointSpeed(double depth) {tugPointSpeed = new DepthCurve(depth); return this;}
    public SinkableBehavior setTugPointSpeed(double[] depth) {tugPointSpeed = new DepthCurve(depth); return this;}
    public SinkableBehavior setTugPointSpeed(ArrayList<Vector2d> depth) {tugPointSpeed = new DepthCurve(depth); return this;}
    public double            getTugPointSpeed(double depth) {return tugPointSpeed.getAt(depth);}


    public SinkableBehavior setCoverageTexture(String coverageText) {this.coverageTexture = coverageText; return this;}
    public String            getCoverageTexture() {return "qsrehydrated:textures/entity/coverage/" + coverageTexture + ".png";}

    public SinkableBehavior setSecretDeathMessage(String deathmessage) {secretDeathMessage = deathmessage; return this;}
    public String            getSecretDeathMessage(){ return secretDeathMessage;}

    public SinkableBehavior setSecretDeathMessageChance(double chance) {secretDeathMessageChance = chance; return this;}
    public double            getSecretDeathMessageChance() {return secretDeathMessageChance;}

    public SinkableBehavior setBuoyancyPoint(double point) {buoyancyPoint = point; return this;}
    public double            getBuoyancyPoint() {return buoyancyPoint;}

    public SinkableBehavior setOffset(double point) {offset = point; return this;}
    public double            getOffset() {return offset;}

    public SinkableBehavior setStepOutHeight(double point) {stepOutHeight = point; return this;}
    public double            getStepOutHeight() {return stepOutHeight;}
    public boolean           canStepOut(double height) {return height>=stepOutHeight;}
}
