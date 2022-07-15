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
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

/**
 * @author SamSt
 *
 */
public abstract class ConsumerIndustryBlockEntity extends IndustryBlockEntity {

	public ConsumerIndustryBlockEntity(BlockEntityType<?> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canInsertInvStack(int slot, ItemStack itemStack, Direction direction) {
		return itemStack.isItemEqual(_getConsumption());
	}

	@Override
	public boolean canExtractInvStack(int slot, ItemStack itemStack, Direction direction) {
		return false;
	}

	@Override
	public boolean isInvEmpty() {
		return true;
	}

	@Override
	public ItemStack getInvStack(int slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack takeInvStack(int slot, int count) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeInvStack(int slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setInvStack(int slot, ItemStack itemStack) {
		System.out.println(itemStack.getCount());
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	protected abstract ItemStack _getConsumption();
}