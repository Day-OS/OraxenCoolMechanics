package com.daytheipc.oraxencoolmechanics.OraxenMechanics.MMSpawner;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import io.th0rgal.oraxen.mechanics.MechanicsManager;
import org.bukkit.configuration.ConfigurationSection;

public class MMSpawnerMechanicFactory extends MechanicFactory {
    public MMSpawnerMechanicFactory(String mechanicId) {
        super(mechanicId);
        MechanicsManager.registerListeners(OraxenPlugin.get(),
                MMSpawnerMechanic.mechanic_id,
                new MMSpawnerMechanicManager(this));
    }

    @Override
    public Mechanic parse(ConfigurationSection itemMechanicConfiguration) {
        Mechanic mechanic = new MMSpawnerMechanic(this, itemMechanicConfiguration);
        addToImplemented(mechanic);
        return mechanic;
    }
}
