package com.daytheipc.oraxencoolmechanics.OraxenMechanics.Package;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.persistence.PersistentDataType;

enum Type{
    ITEM,
    BOX,
}

public class PackageMechanic extends Mechanic {
    public static final String mechanic_id = "package";
    public static final NamespacedKey NAMESPACE_KEY_TYPE = new NamespacedKey(OraxenPlugin.get(), "package-type");
    public static final NamespacedKey NAMESPACE_KEY_CATEGORY = new NamespacedKey(OraxenPlugin.get(), "package-category");
    private final Type type;
    private final String category;


    public PackageMechanic(MechanicFactory mechanicFactory, ConfigurationSection section) {
        super(mechanicFactory, section, item ->
                item.setCustomTag(NAMESPACE_KEY_TYPE, PersistentDataType.STRING, section.getString("type"))
                .setCustomTag(NAMESPACE_KEY_CATEGORY, PersistentDataType.STRING, section.getString("category"))
        );

        this.category = section.getString("category");
        this.type = Type.valueOf(section.getString("type"));
    }

    public Type getType() {
       return this.type;
    }
    public String getCategory() {
        return  this.category;
    }
}
