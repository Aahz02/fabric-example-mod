package com.aahz.testmod;

import java.util.stream.Stream;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;

public class TestTileEntity extends BlockEntity implements ImplementedInventory, Tickable {

	public TestTileEntity() {
		super(TestMod.TEST_TILEENTITY);
	}
	
	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}
	
	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, items);
	}
	
	@Override
	public CompoundTag toTag(CompoundTag tag) {
		Inventories.toTag(tag, items);
		return super.toTag(tag);
	}

	@Override
	public void tick() {
		if(this.world.isClient) return;
		Inventory entity = (Inventory) this;
		ItemStack serverStack1 = entity.getStack(0);
		ItemStack serverStack2 = entity.getStack(1);
		Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(this);
		
		PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(this.pos);
		passedData.writeItemStack(serverStack1);
		passedData.writeItemStack(serverStack2);
		
		watchingPlayers.forEach(player ->
				ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, TestMod.TEST_TILEENTITY_PACKET, passedData));
	}
	
}
