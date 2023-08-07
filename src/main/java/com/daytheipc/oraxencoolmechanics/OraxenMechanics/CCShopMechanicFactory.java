package com.daytheipc.oraxencoolmechanics.OraxenMechanics;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import io.th0rgal.oraxen.mechanics.MechanicsManager;
import org.bukkit.configuration.ConfigurationSection;

public class CCShopMechanicFactory extends MechanicFactory {
    public CCShopMechanicFactory(String mechanicId) {
        super(mechanicId);
        MechanicsManager.registerListeners(OraxenPlugin.get(),
                CCShopMechanic.mechanic_id,
                new CCShopMechanicManager(this));
    }

    @Override
    public Mechanic parse(ConfigurationSection itemMechanicConfiguration) {
        Mechanic mechanic = new CCShopMechanic(this, itemMechanicConfiguration);
        addToImplemented(mechanic);
        return mechanic;
    }
}
