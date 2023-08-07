package com.daytheipc.oraxencoolmechanics.OraxenMechanics.Package;

import com.daytheipc.oraxencoolmechanics.OraxenCoolMechanics;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class PackageMechanicManager implements Listener {
    private PackageMechanicFactory factory;
    public PackageMechanicManager(PackageMechanicFactory factory){
        this.factory = factory;
    }

    public ItemStack getBoxItem(String category){
        for (String id: this.factory.getItems()) {
            ItemStack item = OraxenItems.getItemById(id).build();
            PackageMechanic packageMechanic = (PackageMechanic) this.factory.getMechanic(item);

            PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();
            if (packageMechanic.getType() == Type.ITEM){continue;}
            if (!packageMechanic.getCategory().equals(category)){continue;}
            return item;
        }
        return null;
    }

    public void openBoxMenu(Player player, String category, ItemStack box) {
        SGMenu menu = OraxenCoolMechanics.spiGUI.create("<glyph:neg_shift_16><glyph:neg_shift_2><glyph:menu_box>", 5);

        for (String id: this.factory.getItems()) {
            ItemStack item = OraxenItems.getItemById(id).build();
            PackageMechanic mechanic = (PackageMechanic) this.factory.getMechanic(item);

            Type type_from_data = mechanic.getType();
            String category_from_data = mechanic.getCategory();

            if (type_from_data == Type.BOX) continue;
            if (!category_from_data.equals(category)) continue;

            SGButton button = new SGButton(item).withListener(
                (InventoryClickEvent event) -> {
                    box.setAmount(box.getAmount() - 1);
                    HumanEntity human = event.getWhoClicked();
                    human.getWorld().dropItem(human.getLocation(), event.getCurrentItem());
                    human.getWorld().playSound(human.getLocation(), Sound.BLOCK_BARREL_OPEN, 1, 1);
                    event.getWhoClicked().closeInventory();
                }
            );

            menu.addButton(button);
        }
        player.openInventory(menu.getInventory());

    }
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) return;
        if (this.factory.isNotImplementedIn(item)) return;
        PackageMechanic packageMechanic = (PackageMechanic) this.factory.getMechanic(item);

        Type type = packageMechanic.getType();
        String category = packageMechanic.getCategory();

        if (type == null || category == null) return;

        if (type == Type.BOX){
            e.setCancelled(true);
            openBoxMenu(e.getPlayer(), category, item);
        }
        else if (type == Type.ITEM){
            if(e.getClickedBlock().getType() != Material.STONECUTTER) return;
            e.setCancelled(true);
            ItemStack box =  getBoxItem(category);
            if (box == null) return;
            player.getInventory().setItemInMainHand(box);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_MASON, 1, 0.7F);
        }

    }
}
