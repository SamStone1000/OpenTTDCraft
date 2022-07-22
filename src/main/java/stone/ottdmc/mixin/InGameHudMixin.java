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

package stone.ottdmc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import stone.ottdmc.network.FundsHandler;

/**
 * @author SamSt
 *
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Shadow
	private int scaledWidth;
	@Shadow
	private int scaledHeight;

	/**
	 * 
	 */
	public InGameHudMixin() {
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "render(F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(F)V"))
	public void onRender(float tickDelta, CallbackInfo callbackInfo) {
		TextRenderer renderer = ((InGameHud) (Object) this).getFontRenderer();
		FundsHandler.FUNDS_HUD.render(renderer, tickDelta, scaledWidth, scaledHeight);
	}
}
