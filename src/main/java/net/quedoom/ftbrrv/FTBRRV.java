package net.quedoom.ftbrrv;

import dev.ftb.mods.ftbquests.FTBQuests;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTBRRV implements ModInitializer {
	public static final String MOD_ID = "ftbrrv";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		if (FabricLoader.getInstance().isModLoaded("rrv"))
			FTBQuests.setRecipeModHelper(new RRVRecipeHelper());
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
