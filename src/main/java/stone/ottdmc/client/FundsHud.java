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

package stone.ottdmc.client;

import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import stone.ottdmc.util.RollingMeter;
import stone.ottdmc.util.TickScheduler;
import stone.ottdmc.util.TickScheduler.ScheduledTask;

/**
 * @author SamSt
 *
 */
@Environment(value = EnvType.CLIENT)
public class FundsHud {

	private final RollingMeter funds = new RollingMeter(20);
	private long realFunds;

	private static final TickScheduler scheduler = new TickScheduler();
	private ScheduledTask task;

	private static final Random random = new Random();

	/**
	 * 
	 */
	public FundsHud() {

	}

	public void drawFunds(TextRenderer textRenderer, double delta) {
		GlStateManager.pushMatrix();
		GlStateManager.scaled(.75, .75, 1.0);
		String fundString = getFundString(delta);
		textRenderer.draw(fundString, 5, 5, 0xFFFFFF);
		GlStateManager.popMatrix();
	}

	public void render(TextRenderer textRenderer, double delta) {
		drawFunds(textRenderer, delta);
	}

	/**
	 * @return
	 */
	private String getFundString(double delta) {
		String str = "Funds: $";
		str += funds.getLerp(delta);
		return str;
	}

	/**
	 * @param readLong
	 */
	public void setFunds(long readLong) {
		realFunds = readLong;
		scheduleUpdate();
	}

	/**
	 * Sets up necessary things for the rolling meter to be updated whenever the implementation decides
	 */
	private void scheduleUpdate() {
		if (task == null || task.isClosed())
			task = scheduler.schedule(random.nextInt(1, 60), () ->
			{
				funds.setValue(realFunds);
			});
		else
			task.reschedule(random.nextInt(1, 60));
	}

}
