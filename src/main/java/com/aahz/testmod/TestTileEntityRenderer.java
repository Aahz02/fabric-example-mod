package com.aahz.testmod;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TestTileEntityRenderer extends BlockEntityRenderer<TestTileEntity> {
	
	public TestTileEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(TestTileEntity entity, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light, int overlay) {
		ItemStack stack1 = entity.getStack(0);
		ItemStack stack2 = entity.getStack(1);
		int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
		
		if (!stack1.isEmpty() && stack2.isEmpty()) {
			matrices.push();
			
			double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 10.0;
			matrices.translate(0.5, 1.15 + offset, 0.5);
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2));
			MinecraftClient.getInstance().getItemRenderer().renderItem(stack1, ModelTransformation.Mode.GROUND, 
					lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
			
			matrices.pop();
		} else if (!stack1.isEmpty() && !stack2.isEmpty()) {
			matrices.push();
			
			double offset1 = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 10.0;
			double xOffset1 = Math.sin((entity.getWorld().getTime() + tickDelta) / 20.0) * 0.2;
			double zOffset1 = Math.cos((entity.getWorld().getTime() + tickDelta) / 20.0) * 0.2;
			matrices.translate(0.5 + xOffset1, 1.15 + offset1, 0.5 + zOffset1);
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2));
			MinecraftClient.getInstance().getItemRenderer().renderItem(stack1, ModelTransformation.Mode.GROUND, 
					lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
			
			matrices.pop();
			
			matrices.push();
			
			double offset2 = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 10.0;
			double xOffset2 = Math.sin((entity.getWorld().getTime() + tickDelta) / 20.0) * 0.2;
			double zOffset2 = Math.cos((entity.getWorld().getTime() + tickDelta) / 20.0) * 0.2;
			matrices.translate(0.5 - xOffset2, 1.15 - offset2, 0.5 - zOffset2);
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2));
			MinecraftClient.getInstance().getItemRenderer().renderItem(stack2, ModelTransformation.Mode.GROUND, 
					lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
			
			matrices.pop();
		}
	}
	
}
