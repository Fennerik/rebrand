/*
 * Copyright Kaylen Dart 2022-2025
 * This project is licensed under the GNU GPLv3 license. See the LICENSE file for more information.
 */
package dog.kaylen.rebrand;

import dog.kaylen.rebrand.config.RebrandModConfig;
import lombok.Getter;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RebrandClientMod implements ClientModInitializer {
	/**
	 * The mod logger.
	 */
	@Getter
	private final Logger logger = LogManager.getLogger("rebrand");

	private RebrandModConfig config;

	public RebrandModConfig getConfig() {
		if (config == null) {
			config = AutoConfig.getConfigHolder(RebrandModConfig.class).getConfig();
		}
		return config;
	}

	/**
	 * The mod instance.
	 */
	@Getter
	private static RebrandClientMod instance;

	@Override
	public void onInitializeClient() {
		instance = this;
		AutoConfig.register(RebrandModConfig.class, Toml4jConfigSerializer::new);
		logger.info("Rebrand v{} loaded.",
				FabricLoader.getInstance().getModContainer("rebrand").get().getMetadata().getVersion());
	}
}
