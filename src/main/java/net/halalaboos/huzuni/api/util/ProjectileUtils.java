package net.halalaboos.huzuni.api.util;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.item.ItemTypes;

/**
 * Created by Brandon Williams on 2/21/2017.
 */
public class ProjectileUtils {

    /**
     * @return The projectile type based upon the itemstack given.
     * */
    public ProjectileType getProjectileType(ItemStack itemStack) {
        if (itemStack == null)
            return ProjectileType.NONE;

        int id = itemStack.getItemType().getId();

        // A switch statement is not possible since each case must be a constant and it is the id of each item that must be checked.
        if (id == ItemTypes.BOW.getId()) {
            return ProjectileType.BOW;
        } else if (id == ItemTypes.EXPERIENCE_BOTTLE.getId()) {
            return ProjectileType.EXPERIENCE;
        } else if (id == ItemTypes.EGG.getId() || id == ItemTypes.SNOWBALL.getId() || id == ItemTypes.ENDER_PEARL.getId()) {
            return ProjectileType.DEFAULT;
        } else if (id == ItemTypes.SPLASH_POTION.getId()) {
            return ProjectileType.POTION;
        }  else if (id == ItemTypes.FISHING_ROD.getId()) {
            return ProjectileType.FISHING_ROD;
        }
        return ProjectileType.NONE;
    }

    /**
     * @return The angles required for the projectile type to reach the target entity's location from the initial entity's location.
     * */
    public float[] getAngles(ProjectileType projectileType, Entity initial, Entity target) {
        return getAngles(projectileType, initial.getX(), initial.getY(), initial.getZ(), target.getX(), target.getY(), target.getZ());
    }

    /**
     * @return The angles required for the projectile type to reach the target location from the initial entity's location.
     * */
    public float[] getAngles(ProjectileType projectileType, Entity initial, double targetX, double targetY, double targetZ) {
        return getAngles(projectileType, initial.getX(), initial.getY(), initial.getZ(), targetX, targetY, targetZ);
    }

    /**
     * @return The angles required for the projectile type to reach the target location from the initial location specified.
     * */
    public float[] getAngles(ProjectileType projectileType, double initialX, double initialY, double initialZ, double targetX, double targetY, double targetZ) {
        double yDif = (targetY - initialY);
        double xDif = (targetX - initialX);
        double zDif = (targetZ - initialZ);

        /**
         * Pythagorean theorem to merge x/z
         *           /|
         *          / |
         * xCoord  /  | zDif
         *        /   |
         *       /    |
         *      /_____|
         * (player) xDif
         */
        double xCoord = Math.sqrt((xDif * xDif) + (zDif * zDif));

        // theta produces an angle in radians, so it is necessary to convert this to degrees.
        double pitch = -Math.toDegrees(theta(projectileType.velocity, projectileType.gravity, xCoord, yDif));

        if (Double.isNaN(pitch))
            return null;

        // Calculate the yaw required to face the target location.
        float yaw = (float) (Math.atan2(zDif, xDif) * 180 / Math.PI) - 90;

        return new float[] { yaw, (float) pitch };
    }

    /**
     * Calculates launch angle to hit a specified point based on supplied parameters. <br/>
     * Authored by Harry Gallagher.
     *
     * @param v Projectile velocity
     * @param g World gravity
     * @param x x-coordinate
     * @param y y-coordinate
     * @return angle of launch required to hit point x,y
     * <p/>
     * Whoa there! You just supplied us with a method to hit a 2D point, but Minecraft is a 3D game!
     * <p/>
     * Yeah. Unfortunately this is 100x easier to do than write a method to find the 3D point,
     * so we can just merge the x/z axis of Minecraft into one (using the pythagorean theorem).
     * Have a look at getLaunchAngle to see how that's done
     */
    private float theta(double v, double g, double x, double y) {
        double yv = 2 * y * (v * v);
        double gx = g * (x * x);
        double g2 = g * (gx + yv);
        double insqrt = (v * v * v * v) - g2;
        double sqrt = Math.sqrt(insqrt);

        double numerator = (v * v) + sqrt;
        double numerator2 = (v * v) - sqrt;

        double atan1 = Math.atan2(numerator, g * x);
        double atan2 = Math.atan2(numerator2, g * x);

        /**
         * Ever heard of a quadratic equation? We're gonna have to have two different results
         * here, duh! It's probably best to launch at the smaller angle because that will
         * decrease the total flight time, thus leaving less room for error. If you're just
         * trying to impress your friends you could probably fire it at the maximum angle, but
         * for the sake of simplicity, we'll use the smaller one here.
         */
        return (float) Math.min(atan1, atan2);
    }

    /**
     * Provides gravity and velocity used for calculating the trajectories of projectiles within Minecraft.
     * */
    public enum ProjectileType {
        NONE(0F, 0F), BOW(1.5F),
        POTION(0.5F), EXPERIENCE(0.07F, 0.7F),
        FISHING_ROD(0.04F, 1.5F), DEFAULT(0.03F, 1.5F);

        public final float gravity, velocity;

        ProjectileType(float velocity) {
            this(0.05F, velocity);
        }

        ProjectileType(float gravity, float velocity) {
            this.gravity = gravity;
            this.velocity = velocity;
        }
    }

}
