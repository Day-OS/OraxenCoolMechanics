package com.daytheipc.oraxencoolmechanics;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.th0rgal.oraxen.api.OraxenFurniture;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.api.events.furniture.OraxenFurnitureInteractEvent;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.jukebox.JukeboxBlock;
import io.th0rgal.oraxen.mechanics.provided.misc.music_disc.MusicDiscListener;
import io.th0rgal.oraxen.shaded.morepersistentdatatypes.DataType;
import io.th0rgal.oraxen.utils.BlockHelpers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import io.th0rgal.oraxen.OraxenPlugin;


public class JukeboxMobSpawningMechanicManager implements Listener {
    private JukeboxMobSpawningMechanicFactory factory;
    public JukeboxMobSpawningMechanicManager(JukeboxMobSpawningMechanicFactory factory){
        this.factory = factory;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onInteract(PlayerInteractEvent e)
    {
        Block block = e.getClickedBlock();
        FurnitureMechanic furnitureMechanic = OraxenFurniture.getFurnitureMechanic(block);
        if (furnitureMechanic == null) return;
        if (!furnitureMechanic.isJukebox()) return;

        ItemStack item;
        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack itemStack = BlockHelpers.getPDC(block).get(MusicDiscListener.MUSIC_DISC_KEY, DataType.ITEM_STACK);
                if(itemStack == null) return;
                if(factory.isNotImplementedIn(itemStack)) return;
                JukeboxMobSpawningMechanic mechanic = (JukeboxMobSpawningMechanic) factory.getMechanic(itemStack);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location location = block.getLocation();
                        location.add(0, 4, 0);
                        ItemStack itemStack2 = BlockHelpers.getPDC(block).get(MusicDiscListener.MUSIC_DISC_KEY, DataType.ITEM_STACK);
                        if(!itemStack.isSimilar(itemStack2)) return;
                        if (mechanic.getThunderstormActivated()) block.getWorld().setStorm(true);
                        MythicBukkit.inst().getMobManager().getMythicMob(mechanic.getMobName()).orElse(null)
                                .spawn(BukkitAdapter.adapt(location),1);
                    }
                }.runTaskLater((Plugin) OraxenPlugin.get(), (long) mechanic.getInterval()*20);
            }
        }.runTaskLater(OraxenPlugin.get(), 1);
    }
}
