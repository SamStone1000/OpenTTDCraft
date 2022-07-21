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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author SamSt
 *
 */
public class TickScheduler {

	public static int tickTime;

	/**
	 * @author SamSt
	 *
	 */

	private LinkedList<ScheduledTask> list;
	private int nextTask;
	private boolean hasTask;
	private PriorityQueue<ScheduledTask> tasks = new PriorityQueue<>();

	private static Collection<TickScheduler> schedulers = new ArrayList<>(1);

	/**
	 * 
	 */
	public TickScheduler() {
		hasTask = false;
		schedulers.add(this);
	}

	public static void onTick() {
		// check for an existing tick time in world save
		tickTime++;
		for (TickScheduler scheduler : schedulers)
		{
			scheduler.tick();
		}
	}

	public void tick() {
		if (!hasTask) return; // no task
		if (nextTask <= tickTime) return; // task is still in the
											// future
	}

	private class ScheduledTask implements Comparable {

		private int time;

		public ScheduledTask(int time) {
			this.time = time;
		}

		@Override
		public int compareTo(Object task) {
			return this.getTime() - task.getTime();
		}

		public int getTime() {
			return time;
		}
	}

}
