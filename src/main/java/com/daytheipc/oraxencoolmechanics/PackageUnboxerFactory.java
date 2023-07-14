package com.daytheipc.oraxencoolmechanics;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import io.th0rgal.oraxen.mechanics.MechanicsManager;
import org.bukkit.configuration.ConfigurationSection;

public class PackageUnboxerFactory extends MechanicFactory {

    public PackageUnboxerFactory(ConfigurationSection section) {
        super(section);
        MechanicsManager.registerListeners(OraxenPlugin.get(), new PackageUnboxerManager(this));
    }

    @Override
    public Mechanic parse(ConfigurationSection itemMechanicConfiguration) {
        Mechanic mechanic = new PackageUnboxer(this, itemMechanicConfiguration);
        addToImplemented(mechanic);
        return mechanic;
    }
}