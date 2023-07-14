package com.daytheipc.oraxencoolmechanics;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

enum Type{
    ITEM,
    BOX,
}

class PackageUnboxer extends Mechanic {
    public static final NamespacedKey NAMESPACED_KEY_TYPE = new NamespacedKey(OraxenPlugin.get(), "packageunboxer-type");
    public static final NamespacedKey NAMESPACED_KEY_CATEGORY = new NamespacedKey(OraxenPlugin.get(), "packageunboxer-category");
    private Type type;
    private String category;


    public PackageUnboxer(MechanicFactory mechanicFactory,ConfigurationSection section) {
        super(mechanicFactory, section, item->
                item.setCustomTag(NAMESPACED_KEY_TYPE, PersistentDataType.STRING, section.getString("type"))
                .setCustomTag(NAMESPACED_KEY_CATEGORY, PersistentDataType.STRING, section.getString("category"))
        );

        this.category = section.getString("category");
        this.type = Type.valueOf(section.getString("type"));
    }

    public static Type getType(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        return Type.valueOf(persistentDataContainer.get(PackageUnboxer.NAMESPACED_KEY_TYPE, PersistentDataType.STRING));
    }
    public static String getCategory(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        return persistentDataContainer.get(PackageUnboxer.NAMESPACED_KEY_CATEGORY, PersistentDataType.STRING);
    }
}
