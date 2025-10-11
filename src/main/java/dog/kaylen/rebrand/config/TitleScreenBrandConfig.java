/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.config;

import lombok.Getter;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Title screen brand configuration. Controls how branding appears on the main
 * menu.
 */
@Getter
public final class TitleScreenBrandConfig {
	/**
	 * Whether to enable branding on the title screen.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean enabled = false;

	/**
	 * Whether to show the brand name on the title screen.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean showName = true;

	/**
	 * Custom name shown instead of the default on the title screen.
	 */
	@ConfigEntry.Gui.Tooltip
	String overrideBrandName = "";

	/**
	 * Whether to show the Minecraft version on the title screen.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean showVersion = true;

	/**
	 * Custom version string to show on the title screen.
	 */
	@ConfigEntry.Gui.Tooltip
	String overrideVersion = "";

	/**
	 * Whether to show "Modded" status on the title screen.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean showModdedStatus = true;

	/**
	 * Enables fabulous GAY MODE 🌈 for fun title screen changes.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean gayMode = false;

	/**
	 * Gets the brand name shown on the title screen.
	 */
	public String getBrandName(RebrandModConfig root) {
		if (!root.globalEnabled || !enabled) {
			return "Minecraft";
		}
		if (!overrideBrandName.isEmpty()) {
			return overrideBrandName;
		}
		return root.globalBrandName;
	}

	/**
	 * Gets the version string shown on the title screen.
	 */
	public String getVersion(RebrandModConfig root) {
		if (!root.globalEnabled || !enabled) {
			return FabricLoader.getInstance().getRawGameVersion();
		}
		if (!overrideVersion.isEmpty()) {
			return overrideVersion;
		}
		return root.globalVersion;
	}

	/**
	 * Whether to show the brand name on the title screen.
	 */
	public boolean shouldShowBrandName(RebrandModConfig root) {
		return !root.globalEnabled || !enabled || showName;
	}

	/**
	 * Whether to show the version on the title screen.
	 */
	public boolean shouldShowVersion(RebrandModConfig root) {
		return !root.globalEnabled || !enabled || showVersion;
	}

	/**
	 * Whether to show the modded status on the title screen.
	 */
	public boolean shouldShowModdedStatus(RebrandModConfig root) {
		return !root.globalEnabled || !enabled || showModdedStatus;
	}

	/**
	 * Whether gay mode is enabled on the title screen.
	 */
	public boolean isGayModeEnabled(RebrandModConfig root) {
		return root.globalEnabled && enabled && gayMode;
	}
}
