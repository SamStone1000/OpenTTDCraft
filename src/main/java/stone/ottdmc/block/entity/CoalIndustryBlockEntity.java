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

package stone.ottdmc.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import stone.ottdmc.OpenTTDCraft;

public class CoalIndustryBlockEntity extends ProducerIndustryBlockEntity {

	public CoalIndustryBlockEntity(BlockEntityType<?> type) {
		super(type);
	}

	public CoalIndustryBlockEntity() {
		super(OpenTTDCraft.COAL_INDUSTRY_TYPE);
	}

	@Override
	protected Item _getProduct() {
		return Items.COAL;
	}

}
