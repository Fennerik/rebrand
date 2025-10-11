/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dog.kaylen.rebrand.config.RebrandModConfig;
import me.shedaniel.autoconfig.AutoConfig;

/**
 * Configuration menu for when the ModMenu API is available.
 */
public class RebrandModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> AutoConfig.getConfigScreen(RebrandModConfig.class, parent).get();
	}
}
