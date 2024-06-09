package com.kumoe.conquest.content.entities.seat;

import com.kumoe.conquest.content.entities.EntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SeatEntity extends Entity {
    private BlockState seat;

    public SeatEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public SeatEntity(Level world) {
        this(EntityTypes.SEAT, world);
    }

    public boolean collidesWith(Entity entity) {
        return false;
    }

    public double getMountedHeightOffset() {
        if (this.getSeat().getBlock() instanceof ChairHalfSmall) {
            return 0.175;
        } else {
            return this.getSeat().getBlock() instanceof ChairHalf ? 0.3 : 0.45;
        }
    }

    public float getTargetingMargin() {
        return 0.0F;
    }

    public int hashCode() {
        return super.hashCode() + this.getSeat().hashCode();
    }

    public boolean canHit() {
        return false;
    }

    public void move(MoverType type, Vec3 pos) {
    }

    public void onPlayerCollision(Player player) {
    }

    public boolean shouldRiderSit() {
        return true;
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.getSeat().isAir() || !this.hasPassengers()) {
                this.kill();
            }

        }
    }

    protected boolean canStartRiding(Entity entity) {
        return entity instanceof Player;
    }

    protected void checkBlockCollision() {
    }

    protected void initDataTracker() {
    }

    protected void readCustomDataFromNbt(CompoundTag tag) {
    }

    protected void writeCustomDataToNbt(CompoundTag tag) {
    }

    public ClientboundAddEntityPacket createSpawnPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public BlockState getSeat() {
        if (this.seat == null) {
            this.seat = this.level().getBlockState(this.blockPosition());
        }

        return this.seat;
    }
}