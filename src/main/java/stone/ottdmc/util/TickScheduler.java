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
	private boolean isEmpty = true;
	private PriorityQueue<ScheduledTask> tasks = new PriorityQueue<>();

	private static Collection<TickScheduler> schedulers = new ArrayList<>(1);

	/**
	 * 
	 */
	public TickScheduler() {
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
		if (isEmpty) return; // no task
		while (!tasks.isEmpty() && tasks.peek().getTime() < tickTime) // task is in the past and tasks has a task
			tasks.poll().run();
		isEmpty = tasks.isEmpty();
	}

	private void reschedule(ScheduledTask task, int ticks) {
		tasks.remove(task);
		task.setTime(ticks);
		tasks.add(task);
	}

	public ScheduledTask schedule(int ticks, Runnable run) {
		isEmpty = false;
		ScheduledTask temp = new ScheduledTask(ticks, run);
		tasks.add(temp);
		return temp;
	}

	public class ScheduledTask implements Comparable<ScheduledTask> {

		private int time;
		private Runnable r;
		private boolean isClosed = false;

		public ScheduledTask(int time, Runnable run) {
			this.time = time + tickTime;
			this.r = run;
		}

		/**
		 * @param ticks
		 */
		private void setTime(int ticks) {
			this.time = ticks + tickTime;
		}

		@Override
		public int compareTo(ScheduledTask task) {
			return this.getTime() - task.getTime();
		}

		public void run() {
			r.run();
			isClosed = true;
		}

		public boolean isClosed() {
			return isClosed;
		}

		protected int getTime() {
			return time;
		}

		public void reschedule(int ticks) {
			TickScheduler.this.reschedule(this, ticks);
		}
	}

}
