/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.config;

import lombok.Getter;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

/**
 * Networking brand configuration. Controls the brand sent during the server
 * handshake and ghost mode behavior.
 */
@Getter
public final class NetworkingBrandConfig {
	/**
	 * Whether to enable networking branding.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean enabled = false;

	/**
	 * Brand name to send to the server instead of the default "vanilla".
	 */
	@ConfigEntry.Gui.Tooltip
	String overrideBrandName = "";

	/**
	 * Enables ghost mode, which disables plugin-based mod detection.
	 */
	@ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
	boolean ghostMode = false;

	/**
	 * Gets the brand name used for networking (handshake).
	 */
	public String getBrandName(RebrandModConfig root) {
		if (!root.globalEnabled || !enabled) {
			return "vanilla";
		}
		if (!overrideBrandName.isEmpty()) {
			return overrideBrandName;
		}
		return root.globalBrandName;
	}

	/**
	 * Returns whether ghost mode (mod detection disabling) is enabled.
	 */
	public boolean isGhostModeEnabled(RebrandModConfig root) {
		return root.globalEnabled && enabled && ghostMode;
	}
}
