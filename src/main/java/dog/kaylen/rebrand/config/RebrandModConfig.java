/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.config;

import lombok.Getter;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Configuration for the Rebrand mod, allowing customization of brand names,
 * versions, and display behavior across networking, window title, and title
 * screen.
 */
@Config(name = "rebrand")
@Getter
public final class RebrandModConfig implements ConfigData {
	/**
	 * Global enable flag for applying custom branding throughout the mod. If false,
	 * all branding behavior falls back to defaults.
	 */
	@ConfigEntry.Gui.Tooltip
	boolean globalEnabled = false;

	/**
	 * Global brand name applied across all branding sections, unless overridden by
	 * individual section settings.
	 */
	@ConfigEntry.Gui.Tooltip
	String globalBrandName = "Rebrand";

	/**
	 * Global version string applied across all branding sections, unless overridden
	 * by individual section settings.
	 */
	@ConfigEntry.Gui.Tooltip
	String globalVersion = FabricLoader.getInstance().getRawGameVersion();

	/**
	 * Configuration for the brand sent to the server during handshake.
	 */
	@ConfigEntry.Gui.CollapsibleObject
	NetworkingBrandConfig networkingBrand = new NetworkingBrandConfig();

	/**
	 * Configuration for the brand used in the main window title.
	 */
	@ConfigEntry.Gui.CollapsibleObject
	WindowTitleBrandConfig windowTitleBrand = new WindowTitleBrandConfig();

	/**
	 * Configuration for the brand shown on the title screen (main menu).
	 */
	@ConfigEntry.Gui.CollapsibleObject
	TitleScreenBrandConfig titleScreenBrand = new TitleScreenBrandConfig();
}
