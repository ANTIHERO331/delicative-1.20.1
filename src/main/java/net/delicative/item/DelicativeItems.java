package net.delicative.item;

import net.delicative.Delicative;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DelicativeItems {
    public static final Item STREETLIGHT_ROD = register("streetlight_rod", new StreetlightItem(new Item.Settings().maxCount(64)));

    private static Item register(String id, Item entry) {
        return Registry.register(Registries.ITEM, new Identifier(Delicative.ID, id), entry);
    }

    public static void register(boolean announceRegistration) {
        if (announceRegistration) Delicative.LOGGER.info("Registration invoked");
    }
}
