package com.daytheipc.oraxencoolmechanics;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;
import java.util.logging.Level;

public class PackageUnboxerManager implements Listener {
    private PackageUnboxerFactory factory;
    public PackageUnboxerManager(PackageUnboxerFactory factory){
        this.factory = factory;
    }

    public ItemStack getBoxItem(String category){
        for (String id: this.factory.getItems()) {
            ItemStack item = OraxenItems.getItemById(id).build();
            PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();
            if (PackageUnboxer.getType(item) == Type.ITEM){continue;}
            if (!PackageUnboxer.getCategory(item) .equals(category)){continue;}
            return item;
        }
        return null;
    }

    public void openBoxMenu(Player player, String category) {
        SGMenu menu = OraxenCoolMechanics.spiGUI.create("<glyph:neg_shift_16><glyph:neg_shift_2><glyph:menu_box>", 5);

        for (String id: this.factory.getItems()) {
            ItemStack item = OraxenItems.getItemById(id).build();
            PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();
            Type type_from_data = Type.valueOf(persistentDataContainer.get(PackageUnboxer.NAMESPACED_KEY_TYPE, PersistentDataType.STRING));
            String category_from_data = persistentDataContainer.get(PackageUnboxer.NAMESPACED_KEY_CATEGORY, PersistentDataType.STRING);

            if (type_from_data == Type.BOX) continue;
            if (!category_from_data.equals(category)) continue;

            SGButton button = new SGButton(item).withListener(
                (InventoryClickEvent event) -> {
                    HumanEntity human = event.getWhoClicked();
                    human.getInventory().setItemInMainHand(event.getCurrentItem());
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
        Type type = PackageUnboxer.getType(item);
        String category = PackageUnboxer.getCategory(item);
        if (type == null || category == null) return;

        if (type == Type.BOX){
            openBoxMenu(e.getPlayer(), category);
        }
        else if (type == Type.ITEM){
            if(e.getClickedBlock().getType() != Material.STONECUTTER) return;
            e.setCancelled(true);
            ItemStack box =  getBoxItem(category);
            if (box == null) return;
            player.getInventory().setItemInMainHand(box);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_MASON, 1, 0.7F);
        }


        //ItemStack item = e.getPlayer().getInventory().getItem(e.getNewSlot());

    }
}
