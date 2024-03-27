package net.delicative;

import net.delicative.block.DelicativeBlocks;
import net.delicative.item.DelicativeItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Delicative implements ModInitializer {
	public static final String NAME = "Delicative";
	public static final String ID = "delicative";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	private static final boolean DEVELOPMENT = FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment();

	@Override
	public void onInitialize() {
		DelicativeItems.register(DEVELOPMENT);
		DelicativeBlocks.register(DEVELOPMENT);
	}
}