package com.daytheipc.oraxencoolmechanics.OraxenMechanics;

import com.daytheipc.oraxencoolmechanics.CustomCrops.CCCropsCollection;
import com.daytheipc.oraxencoolmechanics.CustomCrops.CCItemsManager;
import com.daytheipc.oraxencoolmechanics.OraxenCoolMechanics;
import com.daytheipc.oraxencoolmechanics.CustomCrops.Plant;
import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import com.samjakob.spigui.toolbar.SGToolbarBuilder;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.api.events.furniture.OraxenFurnitureInteractEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import teammt.villagerguiapi.classes.VillagerInventory;
import teammt.villagerguiapi.classes.VillagerTrade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CCShopMechanicManager implements Listener {

    private CCShopMechanicFactory factory;
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

    public void openSeedGUI(Player player, ArrayList<CCCropsCollection> ccCropsCollections){
        List<VillagerTrade> trades = new ArrayList<>();
        for (CCCropsCollection cropsCollection: ccCropsCollections) {
            if (OraxenItems.getItemById(cropsCollection.getSeedModelName()) == null){continue;}
            int goodSeason = 1;
            if(cropsCollection.isGoodSeason()){
                goodSeason = 2;
            }
            ItemStack price = new ItemStack(Material.EMERALD);
            price.setAmount(cropsCollection.getMaxPoints() / goodSeason);
            ItemStack item = OraxenItems.getItemById(cropsCollection.getSeedModelName()).build();
            item.setAmount(4);
            trades.add(new VillagerTrade(price, item, Integer.MAX_VALUE));
        }

        VillagerInventory cr = new VillagerInventory(trades, player);
        cr.setName("<glyph:hat><gradient:#39b53d:#014e29> Sementes");
        cr.open();
    }
    public void openFruitsGUI(Player player, ArrayList<CCCropsCollection> ccCropsCollections){
        List<VillagerTrade> trades = new ArrayList<>();
        for (CCCropsCollection cropsCollection: ccCropsCollections) {
            for (Plant plant: cropsCollection.getPlants()) {
                if (OraxenItems.getItemById(plant.getModelName()) == null){continue;}
                int goodSeason = 1;
                if(cropsCollection.isGoodSeason()){
                    goodSeason = 2;
                }
                ItemStack price = new ItemStack(Material.EMERALD);
                price.setAmount(cropsCollection.getMaxPoints() * plant.getQualityModifier() * goodSeason);
                ItemStack item = OraxenItems.getItemById(plant.getModelName()).build();
                item.setAmount(32);
                trades.add(new VillagerTrade(item, price, Integer.MAX_VALUE));
            }
        }
        VillagerInventory cr = new VillagerInventory(trades, player);
        cr.setName("<glyph:hat><gradient:#39b53d:#014e29> Hortaliças");
        cr.open();
    }
    public void openShopGUI(Player player, ArrayList<CCCropsCollection> ccCropsCollections){
        SGButton SeedGUI = new SGButton(new ItemBuilder(Material.BEETROOT_SEEDS).name("&rCategoria: Sementes").build()).withListener((InventoryClickEvent)->{
            openSeedGUI(player, ccCropsCollections);
        });
        SGButton fruitsGUI = new SGButton(new ItemBuilder(Material.CARROT).name("&rCategoria: Hortaliças").build()).withListener((InventoryClickEvent)->{
            openFruitsGUI(player, ccCropsCollections);
        });;
        SGButton blank = new SGButton(OraxenItems.getItemById("filler").build());
        SGButton minus = new SGButton(OraxenItems.getItemById("minus").build());
        SGButton shopping_cart = new SGButton(OraxenItems.getItemById("shopping_cart").build());
        int minus_space = 13;
        int shopping_cart_space = 4;
        int[] blank_space = {
                0,  1,  2,  3,      5,  6,  7,  8,
                9, 10, 11,             15, 16, 17,
                18,19, 20, 21, 22, 23, 24, 25, 26
        };
        SGMenu shopMenu = OraxenCoolMechanics.ccShopGUI.create("", 3);
        for (int i: blank_space){
            shopMenu.setButton(i, blank);
        }
        shopMenu.setAutomaticPaginationEnabled(false);
        shopMenu.setButton(12, SeedGUI);
        shopMenu.setButton(14, fruitsGUI);
        shopMenu.setButton(minus_space, minus);
        shopMenu.setButton(shopping_cart_space, shopping_cart);
        player.openInventory(shopMenu.getInventory());
    }
    public CCShopMechanicManager(CCShopMechanicFactory factory){
        this.factory = factory;
    }
    @EventHandler(priority = EventPriority.HIGH)
    private void onInteract(OraxenFurnitureInteractEvent e) throws NoSuchFieldException, IllegalAccessException {
        if(e.getPlayer().isSneaking()) return;
        CCShopMechanic mechanic = (CCShopMechanic) this.factory.getMechanic(e.getMechanic().getItemID());
        if (mechanic == null) return;
        e.setCancelled(true);
        CCItemsManager ccItemsManager = new CCItemsManager();
        ccItemsManager.updateItems(e.getPlayer().getWorld());
        ArrayList<CCCropsCollection> ccCropsCollections = ccItemsManager.getCrops();
        openShopGUI(e.getPlayer(), ccCropsCollections);


    }
}
