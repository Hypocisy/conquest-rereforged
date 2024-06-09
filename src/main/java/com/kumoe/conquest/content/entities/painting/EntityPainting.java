package com.kumoe.conquest.content.entities.painting;

import com.kumoe.conquest.content.entities.EntityTypes;
import com.kumoe.conquest.content.entities.painting.art.ArtType;
import com.kumoe.conquest.content.entities.painting.art.ModArt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.List;

public class EntityPainting extends HangingEntity implements IEntityAdditionalSpawnData {
    private ModPainting type;
    private ArtType art;

    public EntityPainting(EntityType<EntityPainting> type, Level world) {
        super(type, world);
        this.art = ArtType.A1x1_0;
    }

    public EntityPainting(Level world, BlockPos pos, Direction direction, ModPainting type, ArtType art) {
        super(EntityTypes.PAINTING, world, pos);
        this.art = ArtType.A1x1_0;
        this.direction = direction;
        this.type = type;
        this.art = art;
        this.setDirection(direction);
    }

    public ModPainting getPaintingType() {
        return this.type;
    }

    public ArtType getArt() {
        return this.art;
    }

    public void setType(ModPainting type) {
        this.type = type;
    }

    public void setArt(ArtType art) {
        this.art = art;
        if (this.direction != null) {
            this.setDirection(this.direction);
        }

    }

    public @Nullable ItemStack getPickBlockStack() {
        return this.type.createStack(ModArt.of(this.art));
    }

    public void onBreak(@Nullable Entity brokenEntity) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
            if (brokenEntity instanceof Player player) {
                if (player.getAbilities().instabuild) {
                    return;
                }
            }

            ItemStack drop = this.type.createStack(ModArt.of(this.art));
            if (drop != ItemStack.EMPTY) {
                this.spawnAtLocation(drop, 0.0F);
            }
        }

    }

    public boolean canStayAttached() {
        return true;
    }

    public int getWidthPixels() {
        return this.art.sizeX;
    }

    public int getHeightPixels() {
        return this.art.sizeY;
    }

    public void onPlace() {
        this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putByte("Facing", (byte)this.facing.getHorizontal());
        super.addAdditionalSaveData(compound);
        compound.putString("TypeID", this.type.getName());
        compound.putInt("ArtID", this.art.index());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        String type = compound.getString("TypeID");
        int id = compound.getInt("ArtID");
        this.type = ModPainting.fromId(type);
        this.art = ArtType.fromId(id);
        this.direction = Direction.from2DDataValue(compound.getByte("Facing"));
        super.readAdditionalSaveData(compound);
        this.setDirection(this.direction);
    }

    public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        this.setPos(x, y, z);
    }

    public void updateTrackedPositionAndAngles(double x, double y, double z, float a, float b, int c, boolean bl) {
        BlockPos pos = this.pos;
        this.setPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        Packet<ClientPlayPacketListener> packet = super.createSpawnPacket();
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(this.getId());
        this.writeSpawnData(buf);
        Packet<ClientPlayPacketListener> extraPacket = ServerPlayNetworking.createS2CPacket(IEntityAdditionalSpawnData.EXTRA_DATA_PACKET, buf);
        return new BundleS2CPacket(List.of(packet, extraPacket));
    }

    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(this.pos.getX());
        buffer.writeInt(this.pos.getY());
        buffer.writeInt(this.pos.getZ());
        buffer.writeInt(this.direction.get2DDataValue());
        buffer.writeUtf(this.getPaintingType().getName());
        buffer.writeUtf(this.getArt().shapeId);
    }

    public void readSpawnData(FriendlyByteBuf additionalData) {
        int x = additionalData.readInt();
        int y = additionalData.readInt();
        int z = additionalData.readInt();
        int index = additionalData.readInt();
        String type = additionalData.readUtf();
        String art = additionalData.readUtf();
        this.type = ModPainting.fromId(type);
        this.art = ArtType.fromName(art);
        this.direction = Direction.from2DDataValue(index);
        this.setDirection(Direction.from2DDataValue(index));
        this.setPos(x, y, z);
    }
}