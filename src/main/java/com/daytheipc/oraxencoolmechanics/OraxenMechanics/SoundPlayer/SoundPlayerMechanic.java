package com.daytheipc.oraxencoolmechanics.OraxenMechanics.SoundPlayer;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.persistence.PersistentDataType;


public class SoundPlayerMechanic  extends Mechanic {

    public static final String mechanic_id = "sound_player";
    public static final NamespacedKey NAMESPACED_KEY_SOUND = new NamespacedKey(OraxenPlugin.get(), "sfx_sound");
    public static final NamespacedKey NAMESPACED_KEY_VOLUME = new NamespacedKey(OraxenPlugin.get(), "sfx_volume");
    public static final NamespacedKey NAMESPACED_KEY_PITCH = new NamespacedKey(OraxenPlugin.get(), "sfx_pitch");

    private String name;
    private float volume;
    private float pitch;


    public SoundPlayerMechanic(MechanicFactory mechanicFactory,
                              ConfigurationSection section) {
        super(mechanicFactory, section, item ->
                item.setCustomTag(NAMESPACED_KEY_SOUND, PersistentDataType.STRING, section.getString("name"))
                .setCustomTag(NAMESPACED_KEY_VOLUME, PersistentDataType.STRING, section.getString("volume"))
                .setCustomTag(NAMESPACED_KEY_PITCH, PersistentDataType.STRING, section.getString("pitch"))
        );
        this.name = section.getString("name");
        this.volume = (float) section.getDouble("volume");
        this.pitch =  (float) section.getDouble("pitch");
    }

    public String getName() {
        return name;
    }
    public float getVolume() {
        return volume;
    }
    public float getPitch() {
        return pitch;
    }
}