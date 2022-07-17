/**
 * This file is part of OpenTTDCraft. 
 * Copyright (c) 2022, Stone, All rights reserved.
 * 
 * OpenTTDCraft is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * OpenTTDCraft is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OpenTTDCraft. If not, see <https://www.gnu.org/licenses/>.
 */
package stone.ottdmc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import stone.ottdmc.block.CoalIndustryBlock;
import stone.ottdmc.block.PowerIndustryBlock;
import stone.ottdmc.block.entity.CoalIndustryBlockEntity;
import stone.ottdmc.block.entity.IndustryBlockEntity;
import stone.ottdmc.block.entity.PowerIndustryBlockEntity;

public class Main implements ModInitializer {

	public static Item COAL_INDUSTRY_ITEM;
	public static Block COAL_INDUSTRY_BLOCK;
	public static BlockEntityType<?> COAL_INDUSTRY_TYPE;

	public static Item POWER_INDUSTRY_ITEM;
	public static Block POWER_INDUSTRY_BLOCK;
	public static BlockEntityType<?> POWER_INDUSTRY_TYPE;

	public static final String MOD_ID = "ottdmc";

	@Override
	public void onInitialize() {
		COAL_INDUSTRY_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "coal_industry"),
				new CoalIndustryBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f)));
		COAL_INDUSTRY_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "coal_industry"),
				new BlockItem(COAL_INDUSTRY_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
		COAL_INDUSTRY_TYPE = Registry.register(Registry.BLOCK_ENTITY, new Identifier(MOD_ID, "coal_industry"),
				BlockEntityType.Builder.create(CoalIndustryBlockEntity::new,
						COAL_INDUSTRY_BLOCK).build(null));

		POWER_INDUSTRY_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "power_industry"),
				new PowerIndustryBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f)));
		POWER_INDUSTRY_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "power_industry"),
				new BlockItem(POWER_INDUSTRY_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
		POWER_INDUSTRY_TYPE = Registry.register(Registry.BLOCK_ENTITY, new Identifier(MOD_ID, "power_industry"),
				BlockEntityType.Builder.create(PowerIndustryBlockEntity::new,
						POWER_INDUSTRY_BLOCK).build(null));

		ServerTickEvents.START_WORLD_TICK.register((world) ->
		{
			IndustryBlockEntity.tickTime++;
		});
	}

}
