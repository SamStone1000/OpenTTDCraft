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

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.Direction;

public abstract class IndustryBlockEntity extends BlockEntity implements SidedInventory {

	private static final int[] slots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };

	protected static final String TIME_TAG = "createdAt";
	protected static final String LOCATION_TAG_X = "CreatedAtX";
	protected static final String LOCATION_TAG_Z = "CreatedAtZ";

	public IndustryBlockEntity(BlockEntityType<?> type) {
		super(type);
	}

	@Override
	public int[] getInvAvailableSlots(Direction direction) {
		return slots;
	}

	@Override
	public int getInvSize() {
		return 8;
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity var1) {
		return false;
	}

	@Override
	public void clear() {
	};

}
