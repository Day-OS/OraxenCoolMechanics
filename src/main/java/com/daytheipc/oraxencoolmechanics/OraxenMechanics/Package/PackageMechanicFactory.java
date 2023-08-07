package com.daytheipc.oraxencoolmechanics.OraxenMechanics.Package;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import io.th0rgal.oraxen.mechanics.MechanicsManager;
import org.bukkit.configuration.ConfigurationSection;

public class PackageMechanicFactory extends MechanicFactory {

    public PackageMechanicFactory(String mechanicId) {
        super(mechanicId);
        MechanicsManager.registerListeners(OraxenPlugin.get(), PackageMechanic.mechanic_id, new PackageMechanicManager(this));
    }

    @Override
    public Mechanic parse(ConfigurationSection itemMechanicConfiguration) {
        Mechanic mechanic = new PackageMechanic(this, itemMechanicConfiguration);
        addToImplemented(mechanic);
        return mechanic;
    }
}