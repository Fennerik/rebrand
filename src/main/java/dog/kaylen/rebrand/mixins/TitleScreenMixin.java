/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.mixins;

import dog.kaylen.rebrand.RebrandClientMod;
import dog.kaylen.rebrand.config.RebrandModConfig;
import dog.kaylen.rebrand.config.TitleScreenBrandConfig;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import java.awt.Color;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)I"))
	public void rebrand$modifyRenderArgs(Args args) {
		// Wait for mod initialisation.
		if (RebrandClientMod.getInstance() == null) {
			return;
		}

		RebrandModConfig root = RebrandClientMod.getInstance().getConfig();
		TitleScreenBrandConfig config = root.getTitleScreenBrand();
		StringBuilder builder = new StringBuilder();

		// Append name
		if (config.shouldShowBrandName(root)) {
			builder.append(config.getBrandName(root));
		}

		// Append version
		if (config.shouldShowVersion(root)) {
			if (config.shouldShowBrandName(root)) {
				builder.append(" ");
			}
			builder.append(config.getVersion(root));
		}

		// Append modded status
		if (config.shouldShowModdedStatus(root)) {
			if (config.shouldShowBrandName(root) || config.shouldShowVersion(root)) {
				builder.append(" ");
			}
			builder.append("(Modded)");
		}

		// Modify arguments
		args.set(1, builder.toString());

		// 🏳️‍🌈🏳️‍⚧️
		if (config.isGayModeEnabled(root)) {
			args.set(4, getGayColor());
		}
	}

	/**
	 * Rotates through the HSV color space and returns an ARGB color.
	 *
	 * @param t
	 *            a value between 0.0 and 1.0 representing the hue position
	 * @return the RGB color as an ARGB int
	 */
	private static int getGayColor() {
		float t = (float) (Util.getMeasuringTimeMs() % 10000L) / 1000f; // cycle every 10 seconds
		Color color = Color.getHSBColor(t, 1.0f, 1.0f);
		return (0xFF << 24) | (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
	}
}
