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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import stone.ottdmc.util.MathHelper;
import stone.ottdmc.util.RollingMeter;
import stone.ottdmc.util.TickScheduler;
import stone.ottdmc.util.TickScheduler.ScheduledTask;

/**
 * @author SamSt
 *
 */
@Environment(value = EnvType.CLIENT)
public class FundsHud {

	private final RollingMeter funds = new RollingMeter(40);
	private final Collection<TransientText> incomes = new ArrayList<>();
	private long realFunds;

	private static final TickScheduler scheduler = new TickScheduler();
	private ScheduledTask task;

	private static final Random random = new Random();
	private static final int xCOORD = 5;
	private static final int yCOORD = 5;
	private static final int COLOR = 0xFFFFFFFF;
	private static final double SIZE = 1.0d;

	/**
	 * 
	 */
	public FundsHud() {

	}

	private void drawFunds(TextRenderer textRenderer, double delta, int scaledWidth, int scaledHeight) {
		GlStateManager.pushMatrix();
		GlStateManager.scaled(SIZE, SIZE, 1.0);
		String fundString = getFundString(delta);
		int scaledY = scaledHeight - yCOORD - textRenderer.fontHeight;
		textRenderer.drawWithShadow(fundString, xCOORD, scaledY, COLOR);
		GlStateManager.popMatrix();
	}

	public void render(TextRenderer textRenderer, double delta, int scaledWidth, int scaledHeight) {
		drawFunds(textRenderer, delta, scaledWidth, scaledHeight);
		drawIncomes(textRenderer, delta, scaledWidth, scaledHeight);
	}

	/**
	 * @param textRenderer
	 * @param delta
	 * @param scaledHeight
	 * @param scaledWidth
	 */
	private void drawIncomes(TextRenderer textRenderer, double delta, int scaledWidth, int scaledHeight) {
		GlStateManager.pushMatrix();
		GlStateManager.scaled(SIZE, SIZE, 1.0);
		Iterator<TransientText> itr = incomes.iterator();
		while (itr.hasNext())
		{
			TransientText text = itr.next();
			text.render(textRenderer, delta, scaledWidth, scaledHeight);
			if (text.isClosed())
				itr.remove();
		}
		GlStateManager.popMatrix();
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
				this.setValue(realFunds);
			});
		else
			task.reschedule(random.nextInt(1, 60));
	}

	/**
	 * @param realFunds2
	 */
	private void setValue(long value) {
		long income = value - funds.getValue();
		incomes.add(makeTransientIncome(income));
		funds.setValue(value);
	}

	/**
	 * @param income
	 * @return
	 */
	private TransientText makeTransientIncome(long income) {
		int color;
		String incomeText = "";
		if (income > 0)
		{
			color = 0x0000FF00; // sets color to be green
			incomeText = "+";
		}
		else
		{
			color = 0x00FF0000; // sets color to be red
			incomeText = "-";
		}
		incomeText += "$";
		incomeText += Math.abs(income);
		return new TransientText(incomeText, color);

	}

	/**
	 * @param income2
	 * @return
	 */

	private class TransientText {

		private static final int STATIC_TIME = 100;
		private static final int FADE_TIME = 20;
		private static final int TIME = STATIC_TIME + FADE_TIME;

		private static final int START_OPACITY = 0xFF;
		private static final int END_OPACITY = 0x00;

		private static final int HORIZONTAL_BUFFER = 5;

		private static final int RISE_SPEED = 1;
		private static final int FINAL_HEIGHT = RISE_SPEED * TIME;

		private final String text;
		private final int color;
		private final int createdAt;

		public TransientText(String text, int color) {
			this.text = text;
			this.color = color;
			this.createdAt = TickScheduler.tickTime;
		}

		public void render(TextRenderer textRenderer, double delta, int scaledWidth, int scaledHeight) {
			int opacity = 0xFF;
			if (getAliveTime() >= STATIC_TIME)
			{
				opacity = (int) MathHelper.getRealLerp(getAliveTime() - STATIC_TIME, FADE_TIME, delta, START_OPACITY, END_OPACITY);
			}
			drawText(textRenderer, delta, opacity, scaledWidth, scaledHeight);
		}

		/**
		 * @param textRenderer
		 * @param opacity
		 * @param scaledHeight
		 * @param scaledWidth
		 */
		private void drawText(TextRenderer textRenderer, double delta, int opacity, int scaledWidth, int scaledHeight) {
			int width = textRenderer.getStringWidth(getFundString(delta));
			int color = Math.max(opacity, 0) << 24 | this.color;
			int realY = scaledHeight - yCOORD - textRenderer.fontHeight;
			double rise = MathHelper.getRealLerp(getAliveTime(), TIME, delta, 0, FINAL_HEIGHT);
			GlStateManager.pushMatrix();
			GlStateManager.translated(0.0, -rise, 0.0);
			textRenderer.draw(text, width + xCOORD + HORIZONTAL_BUFFER, realY, color);
			GlStateManager.popMatrix();
		}

		public boolean isClosed() {
			return TickScheduler.tickTime - createdAt >= TIME;
		}

		public int getAliveTime() {
			return TickScheduler.tickTime - createdAt;
		}
	}

}
