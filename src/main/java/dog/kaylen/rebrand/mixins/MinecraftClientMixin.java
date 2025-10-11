/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.mixins;

import dog.kaylen.rebrand.RebrandClientMod;
import dog.kaylen.rebrand.config.RebrandModConfig;
import dog.kaylen.rebrand.config.WindowTitleBrandConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class MinecraftClientMixin {
	@Inject(at = @At(value = "RETURN"), method = "getWindowTitle", cancellable = true)
	private void rebrand$getWindowTitle(final CallbackInfoReturnable<String> info) {
		// Wait for mod initialisation.
		if (RebrandClientMod.getInstance() == null) {
			return;
		}

		MinecraftClient client = (MinecraftClient) (Object) this;

		// Append name
		RebrandModConfig root = RebrandClientMod.getInstance().getConfig();
		WindowTitleBrandConfig config = root.getWindowTitleBrand();
		StringBuilder stringBuilder = new StringBuilder(config.getBrandName(root));

		// Append modded status
		if (config.shouldShowModdedStatus(root)) {
			stringBuilder.append("*");
		}

		// Append version
		if (config.shouldShowVersion(root)) {
			stringBuilder.append(" ");
			stringBuilder.append(config.getVersion(root));
		}

		ClientPlayNetworkHandler clientPlayNetworkHandler = client.getNetworkHandler();

		// If we aren't in a multiplayer server, or we disable this, don't bother
		// constructing this.
		if (clientPlayNetworkHandler == null || !clientPlayNetworkHandler.getConnection().isOpen()
				|| !config.shouldShowServerInfo(root)) {
			info.setReturnValue(stringBuilder.toString());
			return;
		}

		// Append server info like vanilla.
		stringBuilder.append(" - ");
		ServerInfo serverInfo = client.getCurrentServerEntry();
		if (client.getServer() != null && !client.getServer().isRemote()) {
			stringBuilder.append(I18n.translate("title.singleplayer"));
		} else if (serverInfo != null && serverInfo.isRealm()) {
			stringBuilder.append(I18n.translate("title.multiplayer.realms"));
		} else if (client.getServer() == null && (serverInfo == null || !serverInfo.isLocal())) {
			stringBuilder.append(I18n.translate("title.multiplayer.other"));
		} else {
			stringBuilder.append(I18n.translate("title.multiplayer.lan"));
		}

		info.setReturnValue(stringBuilder.toString());
	}
}
