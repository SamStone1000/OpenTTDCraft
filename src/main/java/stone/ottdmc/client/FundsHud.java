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

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import stone.ottdmc.util.RollingMeter;

/**
 * @author SamSt
 *
 */
@Environment(value = EnvType.CLIENT)
public class FundsHud {

	public final RollingMeter funds = new RollingMeter(100);

	/**
	 * 
	 */
	public FundsHud() {

	}

	public void render(TextRenderer textRenderer, double delta) {
		GlStateManager.pushMatrix();
		GlStateManager.scaled(.75, .75, 1.0);
		String fundString = getFundString(delta);
		textRenderer.draw(fundString, 5, 5, 0xFFFFFF);
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
		funds.setValue(readLong);
	}

}
