package com.daytheipc.oraxencoolmechanics.OraxenMechanics.MMSpawner;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class MMSpawnerMechanicManager implements Listener {
    private MMSpawnerMechanicFactory factory;
    public MMSpawnerMechanicManager(MMSpawnerMechanicFactory factory){
        this.factory = factory;
    }
    @EventHandler(priority = EventPriority.HIGH)
    private void onInteract(PlayerInteractEvent e)
    {

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (e.getHand() == EquipmentSlot.HAND) return;
        if (item == null) return;
        if (this.factory.isNotImplementedIn(item)) return;
        if (e.getClickedBlock() == null) return;
        Location location = e.getClickedBlock().getLocation();
        MMSpawnerMechanic mechanic = (MMSpawnerMechanic) this.factory.getMechanic(item);
        MythicBukkit.inst().getMobManager().getMythicMob(mechanic.getMobName()).orElse(null)
                .spawn(BukkitAdapter.adapt(location),1);
        item.setAmount(item.getAmount() - 1);
    }
}
