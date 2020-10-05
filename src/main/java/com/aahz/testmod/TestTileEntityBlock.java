package com.aahz.testmod;

import java.util.Random;
import java.util.stream.Stream;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TestTileEntityBlock extends Block implements BlockEntityProvider {

	public TestTileEntityBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new TestTileEntity();
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		if(world.isClient) return ActionResult.SUCCESS;
		Inventory blockEntity = (Inventory) world.getBlockEntity(pos);
		
		if(!player.getStackInHand(hand).isEmpty()) {
			if(blockEntity.getStack(0).isEmpty()) {
				blockEntity.setStack(0, player.getStackInHand(hand).copy());
				player.getStackInHand(hand).setCount(0);
			} else if(blockEntity.getStack(0).isItemEqual(player.getStackInHand(hand))) {
				ItemStack temp = blockEntity.getStack(0);
				int tempCount = temp.getCount() + player.getStackInHand(hand).getCount();
				if (tempCount <= temp.getMaxCount()) {
					temp.setCount(tempCount);
					blockEntity.setStack(0, temp);
					player.getStackInHand(hand).setCount(0);
				} else {
					tempCount = player.getStackInHand(hand).getCount() - (temp.getMaxCount() - temp.getCount());
					temp.setCount(temp.getMaxCount());
					blockEntity.setStack(0, temp);
					player.getStackInHand(hand).setCount(tempCount);
				}
			}
			else if(blockEntity.getStack(1).isEmpty()) {
				blockEntity.setStack(1, player.getStackInHand(hand).copy());
				player.getStackInHand(hand).setCount(0);
			} else if(blockEntity.getStack(1).isItemEqual(player.getStackInHand(hand))) {
				ItemStack temp = blockEntity.getStack(1);
				int tempCount = temp.getCount() + player.getStackInHand(hand).getCount();
				if (tempCount <= temp.getMaxCount() ) {
					temp.setCount(tempCount);
					blockEntity.setStack(1, temp);
					player.getStackInHand(hand).setCount(0);
				} else {
					tempCount = player.getStackInHand(hand).getCount() - (temp.getMaxCount() - temp.getCount());
					temp.setCount(temp.getMaxCount());
					blockEntity.setStack(1, temp);
					player.getStackInHand(hand).setCount(tempCount);
				}
			}
			else {
				System.out.println("The first slot holds "
                        + blockEntity.getStack(0) + " and the second slot holds " + blockEntity.getStack(1));
			}
		} else {
			if(!blockEntity.getStack(1).isEmpty()) {
				player.inventory.offerOrDrop(world, blockEntity.getStack(1));
				blockEntity.removeStack(1);
			}
			else if (!blockEntity.getStack(0).isEmpty()) {
				player.inventory.offerOrDrop(world, blockEntity.getStack(0));
				blockEntity.removeStack(0);
			}
		}
		return ActionResult.SUCCESS;
	}
}
