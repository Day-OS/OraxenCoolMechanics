package com.daytheipc.oraxencoolmechanics.OraxenMechanics.JukeboxMobSpawning;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JukeboxMobSpawningMechanic  extends Mechanic {

    public static final String mechanic_id = "jukebox_spawning";
    public static final NamespacedKey NAMESPACED_KEY_MOB_NAME = new NamespacedKey(OraxenPlugin.get(), "juke_mob_name");
    public static final NamespacedKey NAMESPACED_KEY_INTERVAL = new NamespacedKey(OraxenPlugin.get(), "juke_interval");
    public static final NamespacedKey NAMESPACED_KEY_THUNDER = new NamespacedKey(OraxenPlugin.get(), "juke_thunderstorm");

    private String mob_name;
    private float interval;
    private boolean thunderstorm;


    public JukeboxMobSpawningMechanic(MechanicFactory mechanicFactory,
                              ConfigurationSection section) {
        super(mechanicFactory, section, item ->
                item.setCustomTag(NAMESPACED_KEY_MOB_NAME, PersistentDataType.STRING, section.getString("mob_name"))
                .setCustomTag(NAMESPACED_KEY_INTERVAL, PersistentDataType.STRING, section.getString("interval"))
                .setCustomTag(NAMESPACED_KEY_THUNDER, PersistentDataType.STRING, section.getString("thunderstorm"))
        );
        this.mob_name = section.getString("mob_name");
        this.interval = (float) section.getDouble("interval");
        this.thunderstorm =  Boolean.parseBoolean(section.getString("thunderstorm"));
    }

    public String getMobName() {
        return mob_name;
    }
    public float getInterval() {
        return interval;
    }
    public boolean getThunderstormActivated() {
        return thunderstorm;
    }
}