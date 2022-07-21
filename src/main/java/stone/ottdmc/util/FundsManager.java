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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import stone.ottdmc.OpenTTDCraft;

/**
 * @author SamSt
 *
 */
public class FundsManager {

	public static final Identifier FUNDS_CHANNEL = new Identifier(OpenTTDCraft.MOD_ID, "funds");

	private static final int WINDOW = 20;

	private final Set<ServerPlayerEntity> PLAYERS = new HashSet<>();

	private long funds;
	private long corpId;
	private int lastTick;

	/**
	 * 
	 */
	public FundsManager(long corpId) {
		this.corpId = corpId;
	}

	/**
	 * @param corpId2
	 * @param players2
	 */
	public FundsManager(long corpId, ServerPlayerEntity... players) {
		this(corpId);
		PLAYERS.addAll(Arrays.asList(players));
	}

	public void addFunds(long value) {
		this.funds += value;
		if (TickScheduler.tickTime - lastTick > WINDOW)
			sendPackets();
		lastTick = TickScheduler.tickTime;
	}

	public void addPlayers(ServerPlayerEntity... players) {
		PLAYERS.addAll(Arrays.asList(players));
	}

	/**
	 * 
	 */
	private void sendPackets() {
		PacketByteBuf packet = PacketByteBufs.create();
		packet.writeLong(funds);
		for (ServerPlayerEntity player : PLAYERS)
			ServerPlayNetworking.send(player, FUNDS_CHANNEL, packet);
	}

	public static class Registry {

		private static final Map<Long, FundsManager> MANAGER_REGISTRY = new HashMap<>();

		public static FundsManager getManager(long corpId) {
			return MANAGER_REGISTRY.get(corpId);
		}

		public static void register(long corpId, ServerPlayerEntity... players) {
			FundsManager manager = MANAGER_REGISTRY.get(corpId);
			if (manager != null)
			{
				manager.addPlayers(players);
				return;
			}
			MANAGER_REGISTRY.put(corpId, new FundsManager(corpId, players));
		}
	}

}
