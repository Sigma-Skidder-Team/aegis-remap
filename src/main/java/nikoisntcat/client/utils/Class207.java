package nikoisntcat.client.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.events.impl.MoveInputEvent;
import nikoisntcat.client.utils.interfaces.IClientPlayerEntity;
import nikoisntcat.client.utils.interfaces.IEntity;
import org.joml.Vector2f;

public class Class207
extends MinecraftUtil {
    float field1939;
    Vec3d field1940;
    Box field1941;
    boolean field1942;
    Input field1943;
    ClientPlayerEntity field1944;
    KeyboardInput field1945;
    float field1946;
    Vec3d field1947;
    boolean field1948;
    boolean field1949;
    boolean field1950;
    boolean field1951;
    float field1952;
    float field1953;
    BlockPos field1954;
    Vec3d field1955;

    public Class207() {
        this.field1944 = Class207.mc.player;
        this.field1940 = new Vec3d(this.field1944.getX(), this.field1944.getY(), this.field1944.getZ());
        this.field1955 = new Vec3d(this.field1944.getVelocity().x, this.field1944.getVelocity().y, this.field1944.getVelocity().z);
        this.field1954 = Class228.method1489(this.field1944.getX(), this.field1944.getY(), this.field1944.getZ());
        this.field1945 = new KeyboardInput(Class207.mc.options);
        this.field1943 = Class207.mc.player.input;
        this.field1948 = Class207.mc.player.isSprinting();
        this.field1942 = Class207.mc.player.isSneaking();
        this.field1951 = Class207.mc.player.isOnGround();
        this.field1949 = Class207.mc.player.horizontalCollision;
        this.field1950 = Class207.mc.player.verticalCollision;
        this.field1939 = ((IClientPlayerEntity)Class207.mc.player).getJumpMovementFactor();
        this.field1946 = Class207.mc.player.fallDistance;
        this.field1947 = Class207.mc.player.movementMultiplier;
        this.field1941 = new Box(Class207.mc.player.getBoundingBox().minX, Class207.mc.player.getBoundingBox().minY, Class207.mc.player.getBoundingBox().minZ, Class207.mc.player.getBoundingBox().maxX, Class207.mc.player.getBoundingBox().maxY, Class207.mc.player.getBoundingBox().maxZ);
    }

    // another classic
    //@NativeObfuscation
    public Class279 method1396(int ticks, float yaw, boolean directionFix) {
        for (int i = 0; i < ticks; ++i) {
            BlockPos blockPos;
            BlockState blockState;
            int n;
            int n2;
            Vec3d vec3d;
            Vec3d vec3d2;
            float f;
            if (directionFix) {
                boolean bl = false;
                float f2 = RotationUtil.method1539().x;
                if (RotationUtil.method1520() != null) {
                    RotationUtil.method1520().x = yaw;
                } else {
                    bl = true;
                    RotationUtil.rotate(new Vector2f(yaw, 1.0f), 1, Priority.field1513);
                }
                MoveInputEvent.field1982 = true;
                this.field1945.tick();
                MoveInputEvent.field1982 = false;
                if (bl) {
                    RotationUtil.method1277();
                } else {
                    RotationUtil.method1520().x = f2;
                }
            } else {
                MoveInputEvent.field1982 = true;
                this.field1945.tick();
                MoveInputEvent.field1982 = false;
            }
            this.field1952 = this.field1945.movementForward;
            this.field1953 = this.field1945.movementSideways;
            if (this.field1948 && !this.field1945.playerInput.forward()) {
                this.field1948 = false;
            }
            double d = this.field1955.getX();
            double d2 = this.field1955.getY();
            double d3 = this.field1955.getZ();
            if (Math.abs(this.field1955.x) < 0.003) {
                d = 0.0;
            }
            if (Math.abs(this.field1955.y) < 0.003) {
                d2 = 0.0;
            }
            if (Math.abs(this.field1955.z) < 0.003) {
                d3 = 0.0;
            }
            this.field1955 = new Vec3d(d, d2, d3);
            if (this.field1945.playerInput.jump() && this.field1951) {
                float f3 = Class207.mc.world.getBlockState(this.field1954).getBlock().getJumpVelocityMultiplier();
                float f4 = Class207.mc.world.getBlockState(Class228.method1489(this.field1940.x, this.field1941.minY - 0.5000001, this.field1940.z)).getBlock().getJumpVelocityMultiplier();
                double d4 = 0.42 * (double)((double)f3 != 1.0 ? f3 : f4);
                this.field1955 = new Vec3d(this.field1955.getX(), d4, this.field1955.getZ());
                if (this.field1948) {
                    float f5 = yaw * ((float)Math.PI / 180);
                    this.field1955 = this.field1955.add((double)(-MathHelper.sin((float)f5) * 0.2f), 0.0, (double)(MathHelper.cos((float)f5) * 0.2f));
                }
            }
            this.field1953 *= 0.98f;
            this.field1952 *= 0.98f;
            Vec3d vec3d3 = new Vec3d((double)this.field1953, 0.0, (double)this.field1952);
            if (this.field1955.y <= 0.0) {
                // empty if block
            }
            BlockPos blockPos2 = Class228.method1489(this.field1940.x, this.field1941.minY - 0.5000001, this.field1940.z);
            float f6 = Class207.mc.world.getBlockState(blockPos2).getBlock().getSlipperiness();
            float f7 = f = this.field1951 ? f6 * 0.91f : 0.91f;
            float f8 = this.field1951 ? Class207.mc.player.getMovementSpeed() * (0.21600002f / (f6 * f6 * f6)) : (this.field1948 ? 0.025999999f : 0.02f);
            double d5 = vec3d3.lengthSquared();
            if (d5 < 1.0E-7) {
                vec3d2 = Vec3d.ZERO;
            } else {
                vec3d = (d5 > 1.0 ? vec3d3.normalize() : vec3d3).multiply((double)f8);
                float f9 = MathHelper.sin((float)(yaw * ((float)Math.PI / 180)));
                float f10 = MathHelper.cos((float)(yaw * ((float)Math.PI / 180)));
                vec3d2 = new Vec3d(vec3d.x * (double)f10 - vec3d.z * (double)f9, vec3d.y, vec3d.z * (double)f10 + vec3d.x * (double)f9);
            }
            this.field1955 = this.field1955.add(vec3d2);
            vec3d = new Vec3d(this.field1955.x, this.field1955.y, this.field1955.z);
            if (this.field1947.lengthSquared() > 1.0E-7) {
                vec3d = this.field1955.multiply(this.field1947);
                this.field1947 = Vec3d.ZERO;
                this.field1955 = Vec3d.ZERO;
            }
            Vec3d vec3d4 = new Vec3d(Class207.mc.player.getPos().x, Class207.mc.player.getPos().y, Class207.mc.player.getPos().z);
            Class207.mc.player.setPosition(this.field1940.x, this.field1940.y, this.field1940.z);
            Vec3d vec3d5 = ((IEntity)Class207.mc.player).getAllowedMovement(vec3d);
            Vec3d vec3d6 = new Vec3d(vec3d5.x, vec3d5.y, vec3d5.z);
            Class207.mc.player.setPosition(vec3d4.x, vec3d4.y, vec3d4.z);
            if (vec3d6.lengthSquared() > 1.0E-7) {
                this.field1941 = this.field1941.offset(vec3d6);
                this.field1940 = new Vec3d((this.field1941.minX + this.field1941.maxX) / 2.0, this.field1941.minY, (this.field1941.minZ + this.field1941.maxZ) / 2.0);
                this.field1954 = Class228.method1489(this.field1940.x, this.field1940.y, this.field1940.z);
            }
            this.field1949 = !MathHelper.approximatelyEquals((double)vec3d.x, (double)vec3d6.x) || !MathHelper.approximatelyEquals((double)vec3d.z, (double)vec3d6.z);
            this.field1950 = vec3d.y != vec3d6.y;
            this.field1951 = this.field1950 && vec3d.y < 0.0;
            int n3 = MathHelper.floor((double)this.field1940.x);
            BlockPos blockPos3 = Class228.method1489(n3, n2 = MathHelper.floor((double)(this.field1940.y - (double)0.2f)), n = MathHelper.floor((double)this.field1940.z));
            if (!Class207.mc.world.getBlockState(blockPos3).isAir() || (blockState = Class207.mc.world.getBlockState(blockPos = blockPos3.down())).isIn(BlockTags.FENCES) || blockState.isIn(BlockTags.WALLS) || blockState.getBlock() instanceof FenceGateBlock) {
                // empty if block
            }
            if (this.field1951) {
                this.field1946 = 0.0f;
            } else if (vec3d6.y < 0.0) {
                this.field1946 = (float)((double)this.field1946 - vec3d6.y);
            }
            blockPos = new BlockPos((int) this.field1955.x, (int) this.field1955.y, (int) this.field1955.z);
            if (vec3d.x != vec3d6.x) {
                this.field1955 = new Vec3d(0.0, blockPos.getY(), blockPos.getZ());
            }
            if (vec3d.z != vec3d6.z) {
                this.field1955 = new Vec3d(blockPos.getX(), blockPos.getY(), 0.0);
            }
            if (vec3d.y != vec3d6.y) {
                this.field1955 = this.field1955.multiply(1.0, 0.0, 1.0);
            }
            Class228.method1489(this.field1941.minX + 0.001, this.field1941.minY + 0.001, this.field1941.minZ + 0.001);
            Class228.method1489(this.field1941.maxX - 0.001, this.field1941.maxY - 0.001, this.field1941.maxZ - 0.001);
            new BlockPos.Mutable();
            Block block = Class207.mc.world.getBlockState(this.field1954).getBlock();
            float f11 = block.getVelocityMultiplier();
            float f12 = block != Blocks.WATER && block != Blocks.BUBBLE_COLUMN ? ((double)f11 == 1.0 ? Class207.mc.world.getBlockState(Class228.method1489(this.field1940.x, this.field1941.minY - 0.5000001, this.field1940.z)).getBlock().getVelocityMultiplier() : f11) : f11;
            Vec3d vec3d7 = this.field1955 = this.field1955.multiply((double)f12, 1.0, (double)f12);
            double d6 = vec3d7.y;
            d6 = !(!Class207.mc.world.isClient || Class207.mc.world.isChunkLoaded(blockPos2) && Class207.mc.world.isChunkLoaded(Class228.method1489(blockPos2.getX() >> 4, 0.0, blockPos2.getZ() >> 4))) ? (this.field1940.y > 0.0 ? -0.1 : 0.0) : (d6 -= 0.08);
            this.field1955 = new Vec3d(vec3d7.x * (double)f, d6 * (double)0.98f, vec3d7.z * (double)f);
            this.field1939 = 0.02f;
            if (!this.field1948) continue;
            this.field1939 = (float)((double)this.field1939 + 0.005999999865889549);
        }
        return new Class279(this.field1940, this.field1951, this.field1946, this.field1954);
    }
}
