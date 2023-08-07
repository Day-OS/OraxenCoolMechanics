package com.daytheipc.oraxencoolmechanics.OraxenMechanics.MMSpawner;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.persistence.PersistentDataType;


public class MMSpawnerMechanic  extends Mechanic {

    public static final String mechanic_id = "mythicmobs_spawner";
    public static final NamespacedKey NAMESPACED_MOB_NAME = new NamespacedKey(OraxenPlugin.get(), "name");

    private String name;


    public MMSpawnerMechanic(MechanicFactory mechanicFactory,
                              ConfigurationSection section) {
        super(mechanicFactory, section, item ->
                item.setCustomTag(NAMESPACED_MOB_NAME, PersistentDataType.STRING, section.getString("name"))
        );
        this.name = section.getString("name");
    }

    public String getMobName() {
        return name;
    }
}