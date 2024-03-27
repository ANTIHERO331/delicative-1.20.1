package net.delicative.block;

import net.delicative.Delicative;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class DelicativeBlocks {
    public static final Block STREETLIGHT_BASE = register("streetlight_base", new StreetlightBaseBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .instrument(Instrument.IRON_XYLOPHONE)
                    .strength(5.0f, 6.0f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.LANTERN)
            )
    );
    public static final Block STREETLIGHT_ROD = register("streetlight_rod", new StreetlightBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .instrument(Instrument.IRON_XYLOPHONE)
                    .strength(5.0f, 6.0f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.LANTERN)
            )
    );
//    public static final Block STREETLIGHT_HEAD = ;
//    public static final Block EDITING_TABLE = ;
//    public static final Block TRADE_BENCH = ;

    private static Block register(String id, Block entry) {
        return Registry.register(Registries.BLOCK, new Identifier(Delicative.ID, id), entry);
    }

    public static void register(boolean announceRegistration) {
        if (announceRegistration) Delicative.LOGGER.info("Registration invoked");
    }
}
