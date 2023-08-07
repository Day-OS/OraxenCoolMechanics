package com.daytheipc.oraxencoolmechanics.OraxenMechanics;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.persistence.PersistentDataType;


public class CCShopMechanic  extends Mechanic {
    public static final String mechanic_id = "cc_shop";
    public CCShopMechanic(MechanicFactory mechanicFactory,
                              ConfigurationSection section) {
        super(mechanicFactory, section, item ->
                item
        );
    }
}