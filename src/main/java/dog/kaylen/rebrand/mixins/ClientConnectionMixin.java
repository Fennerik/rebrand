/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.mixins;

import dog.kaylen.rebrand.RebrandClientMod;
import dog.kaylen.rebrand.config.RebrandModConfig;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents the client from sending custom payload packets ( except vanilla ones
 * that aren't (un)register ) when ghost mode is enabled.
 */
@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
	@Inject(method = "sendInternal(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;Z)V", at = @At("HEAD"), cancellable = true)
	public void rebrand$send(Packet<?> packet, ChannelFutureListener channelFutureListener, boolean bl,
			CallbackInfo ci) {
		// default to ghost mode if the mod is not initialized - shouldn't occur!
		if (RebrandClientMod.getInstance() == null) {
			ci.cancel();
		}

		// Bail early if ghost mode is not enabled.
		RebrandModConfig config = RebrandClientMod.getInstance().getConfig();
		if (config.getNetworkingBrand().isGhostModeEnabled(config)) {
			return;
		}

		// Ensure we handle the right kinds of packets
		if (!(packet instanceof CustomPayloadC2SPacket)) {
			return;
		}

		// Cursed: get the packet identifier
		if (((CustomPayloadC2SPacket) packet).comp_1647().getId().comp_2242().toString()
				.matches("minecraft:(?!(?:un)?register).*")) {
			return;
		}

		// Prevent the packet from being sent.
		ci.cancel();
	}
}
