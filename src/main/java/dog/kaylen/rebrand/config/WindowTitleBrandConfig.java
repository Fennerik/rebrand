/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.config;

import lombok.Getter;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Window title brand configuration. Controls branding shown in the Minecraft
 * window title.
 */
@Getter
public final class WindowTitleBrandConfig {
	/**
	 * Whether to enable branding in the window title.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean enabled = false;

	/**
	 * Custom brand name shown in the window title.
	 */
	@ConfigEntry.Gui.Tooltip
	String overrideBrandName = "";

	/**
	 * Whether to display the version in the window title.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean showVersion = true;

	/**
	 * Custom version string to show in the window title.
	 */
	@ConfigEntry.Gui.Tooltip
	String overrideVersion = "";

	/**
	 * Whether to show "Modded" in the window title if applicable.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean showModdedStatus = true;

	/**
	 * Whether to show server information in the window title (MP only).
	 */
	@ConfigEntry.Gui.Tooltip
	boolean showServerInfo = true;

	/**
	 * Gets the brand name used in the window title.
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
	 * Gets the version string used in the window title.
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
	 * Whether the version should be shown in the window title.
	 */
	public boolean shouldShowVersion(RebrandModConfig root) {
		return !root.globalEnabled || !enabled || showVersion;
	}

	/**
	 * Whether server info should be shown in the window title.
	 */
	public boolean shouldShowServerInfo(RebrandModConfig root) {
		return !root.globalEnabled || !enabled || showServerInfo;
	}

	/**
	 * Whether modded status should be shown in the window title.
	 */
	public boolean shouldShowModdedStatus(RebrandModConfig root) {
		return !root.globalEnabled || !enabled || showModdedStatus;
	}
}
