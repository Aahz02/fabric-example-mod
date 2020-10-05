package com.aahz.testmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class TestModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(TestMod.TEST_TILEENTITY, TestTileEntityRenderer::new);
		ClientSidePacketRegistry.INSTANCE.register(TestMod.TEST_TILEENTITY_PACKET, 
				(packetContext, attachedData) -> {
					BlockPos pos = attachedData.readBlockPos();
					ItemStack stack1 = attachedData.readItemStack();
					ItemStack stack2 = attachedData.readItemStack();
					packetContext.getTaskQueue().execute(() -> {
						TestTileEntity entity = (TestTileEntity) MinecraftClient.getInstance().world.getBlockEntity(pos);
						entity.setStack(0, stack1);
						entity.setStack(1, stack2);
					});
				});
	}

}
