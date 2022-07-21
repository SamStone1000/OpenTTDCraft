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

package stone.ottdmc.util;

/**
 * @author SamSt
 *
 */
public class RollingMeter {

	private long realValue;
	private long lastValue;
	private int lastTick;
	private int delay;
	private long realLastValue = 0;

	/**
	 * 
	 */
	public RollingMeter(int delay) {
		this.delay = delay;
	}

	public long getValue() {
		return realValue;
	}

	public void setValue(long value) {
		this.lastTick = TickScheduler.tickTime;
		this.lastValue = realLastValue;
		this.realValue = value;
	}

	public long getLerp(double delta) {
		// if (IndustryBlockEntity.tickTime == lastTick) return
		// lastValue;
		int tickProgress = TickScheduler.tickTime - lastTick;
		if (tickProgress > delay) return realValue;
		long difference = realValue - lastValue;
		double perTick = (double) difference / delay;
		double lastProgress = lastValue + perTick * tickProgress;
		double currentProgress = lastValue + perTick * (tickProgress + 1);
		delta *= 20;
		long lerped = (long) (lastProgress + delta * (currentProgress - lastProgress));
		realLastValue = lerped;
		return lerped;
	}

	/**
	 * @param l
	 */
	public void incrementValue(long value) {
		this.setValue(getValue() + value);
	}

}
