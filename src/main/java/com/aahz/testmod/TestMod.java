package com.aahz.testmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TestMod implements ModInitializer{
	
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier("testmod", "general"), 
			() -> new ItemStack(Blocks.COBBLESTONE));
	
	public static final TestItem TEST_ITEM = new TestItem(new Item.Settings().group(ITEM_GROUP).maxCount(16));
	
	public static final Block TEST_BLOCK = new Block(Block.Settings.of(Material.METAL).strength(4.0f));
	
	public static final TestTileEntityBlock TEST_TILEENTITY_BLOCK = new TestTileEntityBlock(Block.Settings.of(Material.WOOD).strength(3.0f));
	public static BlockEntityType<TestTileEntity> TEST_TILEENTITY;
	
	public static final Identifier TEST_TILEENTITY_PACKET = new Identifier("testmod", "tileentity");
	
	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("testmod", "test_item"), TEST_ITEM);
		
		Registry.register(Registry.BLOCK, new Identifier("testmod", "test_block"), TEST_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("testmod", "test_block"), new BlockItem(TEST_BLOCK, 
				new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier("testmod", "test_tileentity_block"), TEST_TILEENTITY_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("testmod", "test_tileentity_block"), new BlockItem(TEST_TILEENTITY_BLOCK, 
				new Item.Settings().group(ITEM_GROUP)));
		
		TEST_TILEENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("testmod", "test_tileentity"), 
				BlockEntityType.Builder.create(TestTileEntity::new, TEST_TILEENTITY_BLOCK).build(null));
	}

}
