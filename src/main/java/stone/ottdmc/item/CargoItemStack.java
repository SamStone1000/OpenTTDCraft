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

package stone.ottdmc.item;

import java.util.Deque;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import stone.ottdmc.block.entity.IndustryBlockEntity;

public class CargoItemStack extends ItemStack {

	Deque<Integer> creationTimes;

	public CargoItemStack(ItemConvertible item) {
		this(item, 1);
	}

	public CargoItemStack(ItemConvertible item, int count) {
		super(item, count);
		creationTimes.push(Integer.valueOf(IndustryBlockEntity.tickTime));
	}

	/**
	 * takes count Integers from creationTimes and adds the
	 * integers to its own creationTimes
	 * 
	 * @param item
	 * @param count
	 * @param creationTimes
	 */
	public CargoItemStack(ItemConvertible item, int count, Deque<Integer> creationTimes) {
		super(item, count);
		int size = creationTimes.size();
		if (size < count)
			count = size;
		for (int i = 0; i < count; i++)
		{
			this.creationTimes.push(creationTimes.pop());
		}
	}

	@Override
	public ItemStack split(int amount) {
		CargoItemStack newStack = new CargoItemStack(this.getItem(), this.getCount(), creationTimes);
		newStack.setCooldown(this.getCooldown());
		if (this.getTag() != null)
		{
			newStack.setTag(getTag().copy());
		}
		return newStack;
	}

	@Override
	public CompoundTag getTag() {
		int time = (int)creationTimes.stream().mapToInt(i -> i).average().orElse(0d);
		super.getTag().putInt(Industry, time);t
	}

	@Override
	public void increment(int amount) {
		// TODO Auto-generated method stub
		super.increment(amount);
	}

	public void setCreationTimes(Deque<Integer> times) {
		this.creationTimes = times;
	}
}
