/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.mixins;

import dog.kaylen.rebrand.RebrandClientMod;
import dog.kaylen.rebrand.config.RebrandModConfig;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Injects the desired client brand into the ClientBrandRetriever.
 */
@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {
	@Inject(at = @At("HEAD"), method = "getClientModName", cancellable = true, remap = false)
	private static void rebrand$getConfiguredClientBrand(CallbackInfoReturnable<String> info) {
		// prevent npe on client initialization
		if (RebrandClientMod.getInstance() == null) {
			info.setReturnValue("fabric");
			return;
		}
		RebrandModConfig config = RebrandClientMod.getInstance().getConfig();
		info.setReturnValue(config.getNetworkingBrand().getBrandName(config));
	}
}
