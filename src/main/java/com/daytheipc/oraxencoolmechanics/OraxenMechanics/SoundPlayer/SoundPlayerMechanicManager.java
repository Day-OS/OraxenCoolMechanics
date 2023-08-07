package com.daytheipc.oraxencoolmechanics.OraxenMechanics.SoundPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class SoundPlayerMechanicManager implements Listener {
    private SoundPlayerMechanicFactory factory;
    public SoundPlayerMechanicManager(SoundPlayerMechanicFactory factory){
        this.factory = factory;
    }
    @EventHandler(priority = EventPriority.HIGH)
    private void onInteract(PlayerInteractEvent e)
    {
        if (e.getHand() == EquipmentSlot.HAND) return;
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) return;
        if (this.factory.isNotImplementedIn(item)) return;
        SoundPlayerMechanic mechanic = (SoundPlayerMechanic) this.factory.getMechanic(item);
        player.getWorld().playSound(player.getLocation(), mechanic.getName(), mechanic.getVolume(), mechanic.getPitch());
    }
}
