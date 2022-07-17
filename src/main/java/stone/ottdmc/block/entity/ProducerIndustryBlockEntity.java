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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;

public abstract class ProducerIndustryBlockEntity extends IndustryBlockEntity {

	private ItemStack tempProduct = _getProduct().copy();

	public ProducerIndustryBlockEntity(BlockEntityType<?> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canInsertInvStack(int slot, ItemStack stack, Direction direction) {
		return false;
	}

	@Override
	public boolean canExtractInvStack(int slot, ItemStack stack, Direction direction) {
		return true;
	}

	@Override
	public int getInvSize() {
		return 8;
	}

	@Override
	public boolean isInvEmpty() {
		return false;
	}

	@Override
	public ItemStack getInvStack(int slot) {
		return getProduct();
	}

	@Override
	public ItemStack takeInvStack(int slot, int count) {
		return getProduct().split(count);
	}

	@Override
	public ItemStack removeInvStack(int slot) {
		return getProduct();
	}

	@Override
	public void setInvStack(int slot, ItemStack itemStack) {
	}

	private ItemStack getProduct() {
		if (tempProduct.isEmpty())
		{
			tempProduct = _getProduct().copy();
			CompoundTag tag = new CompoundTag();
			tag.putLong("createdat", tickTime);
			tempProduct.setTag(tag);
		}
		return tempProduct;
	}

	protected abstract ItemStack _getProduct();

}
