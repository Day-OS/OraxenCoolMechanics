package com.daytheipc.oraxencoolmechanics;

import com.daytheipc.oraxencoolmechanics.OraxenMechanics.CCShopMechanic;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.CCShopMechanicFactory;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.JukeboxMobSpawning.JukeboxMobSpawningMechanic;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.JukeboxMobSpawning.JukeboxMobSpawningMechanicFactory;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.MMSpawner.MMSpawnerMechanic;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.MMSpawner.MMSpawnerMechanicFactory;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.Package.PackageMechanic;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.Package.PackageMechanicFactory;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.SoundPlayer.SoundPlayerMechanic;
import com.daytheipc.oraxencoolmechanics.OraxenMechanics.SoundPlayer.SoundPlayerMechanicFactory;
import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.toolbar.SGToolbarBuilder;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.mechanics.MechanicsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class OraxenCoolMechanics extends JavaPlugin {
    public static SpiGUI spiGUI;
    public static SpiGUI ccShopGUI;
    private final SGToolbarBuilder defaultToolbarBuilder = (slot, page, type, menu) -> {
        switch (type) {
            case PREV_BUTTON:
                if (menu.getCurrentPage() > 0) {
                    return (new SGButton(OraxenItems.getItemById("arrow_previous_icon").build())).withListener((event) -> {
                        event.setCancelled(true);
                        menu.previousPage(event.getWhoClicked());
                    });
                }

                return null;
            case CURRENT_BUTTON:
                return (new SGButton((new ItemBuilder(Material.NAME_TAG)).name("&7&lPage " + (menu.getCurrentPage() + 1) + " of " + menu.getMaxPage()).lore(new String[]{"&7You are currently viewing", "&7page " + (menu.getCurrentPage() + 1) + "."}).build())).withListener((event) -> {
                    event.setCancelled(true);
                });
            case NEXT_BUTTON:
                if (menu.getCurrentPage() < menu.getMaxPage() - 1) {
                    return (new SGButton(OraxenItems.getItemById("arrow_next_icon").build())).withListener((event) -> {
                        event.setCancelled(true);
                        menu.nextPage(event.getWhoClicked());
                    });
                }

                return null;
            case UNASSIGNED:
            default:
                return null;
        }
    };
    @Override
    public void onEnable() {
        ccShopGUI = new SpiGUI(this);
        spiGUI = new SpiGUI(this);
        spiGUI.setDefaultToolbarBuilder(defaultToolbarBuilder);

        MechanicsManager.registerMechanicFactory(PackageMechanic.mechanic_id, new PackageMechanicFactory(PackageMechanic.mechanic_id), true);
        MechanicsManager.registerMechanicFactory(SoundPlayerMechanic.mechanic_id, new SoundPlayerMechanicFactory(SoundPlayerMechanic.mechanic_id), true);
        MechanicsManager.registerMechanicFactory(JukeboxMobSpawningMechanic.mechanic_id, new JukeboxMobSpawningMechanicFactory(JukeboxMobSpawningMechanic.mechanic_id), true);
        MechanicsManager.registerMechanicFactory(MMSpawnerMechanic.mechanic_id, new MMSpawnerMechanicFactory(MMSpawnerMechanic.mechanic_id), true);
        MechanicsManager.registerMechanicFactory(CCShopMechanic.mechanic_id, new CCShopMechanicFactory(CCShopMechanic.mechanic_id), true);

        OraxenItems.loadItems();
        Bukkit.getLogger().log(Level.FINE, "Plugin Loaded ^^!!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}

