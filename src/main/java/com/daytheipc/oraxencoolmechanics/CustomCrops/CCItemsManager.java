package com.daytheipc.oraxencoolmechanics.CustomCrops;

import net.momirealms.customcrops.CustomCrops;
import net.momirealms.customcrops.api.object.action.Action;
import net.momirealms.customcrops.api.object.action.DropItemImpl;
import net.momirealms.customcrops.api.object.crop.CropConfig;
import net.momirealms.customcrops.api.object.crop.CropManager;
import net.momirealms.customcrops.api.object.crop.StageConfig;
import net.momirealms.customcrops.api.object.loot.Loot;
import net.momirealms.customcrops.api.object.loot.QualityLoot;
import net.momirealms.customcrops.api.object.requirement.Requirement;
import net.momirealms.customcrops.api.object.requirement.SeasonImpl;
import net.momirealms.customcrops.api.object.season.CCSeason;
import net.momirealms.customcrops.integration.SeasonInterface;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class CCItemsManager {

    private static ArrayList<CCCropsCollection> crops = new ArrayList<CCCropsCollection>();
    private static int day = 0;
    public ArrayList<CCCropsCollection> getCrops() {
        return crops;
    }

    public CCItemsManager(){}



    private boolean checkIfSeasonMatchesRequirements(Requirement[] req, World world) throws NoSuchFieldException, IllegalAccessException {
        if (req == null) return false;
        for (Requirement r: req){
            if (!(r instanceof SeasonImpl)) continue;
            SeasonImpl seasonimpl = (SeasonImpl) r;

            List<CCSeason> seasons = Utils.getSeasonsFromRequired(seasonimpl);
            SeasonInterface seasonInterface = CustomCrops.getInstance().getIntegrationManager().getSeasonInterface();
            CCSeason currentSeason = seasonInterface.getSeason(world.getName());
            if (seasons.contains(currentSeason)) {
                return true;
            }
        }
        return true;
    }

    private ArrayList<Plant> getPlantsFromAction(Action action){
        ArrayList<Plant> plants = new ArrayList<>();
        DropItemImpl dropItem = (DropItemImpl) action;
        for (Loot loot : dropItem.loots()){
            if (loot instanceof QualityLoot){
                String[] qualityLoots = null;
                try {
                    qualityLoots = Utils.getQualityLoots((QualityLoot) loot);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                Integer i = 1;
                for (String id : qualityLoots) {
                    plants.add(new Plant(id, i));
                    i++;
                }
                Bukkit.getServer().broadcastMessage(Arrays.toString(qualityLoots));
            }
            else{//if (loot instanceof OtherLoot){
                //OtherLoot otherLoot = (OtherLoot) loot;
                //plants.add(new Plant(otherLoot.getItemID(),1));
            }
        }
        return plants;
    }
    private CCCropsCollection getCrop(String id, CropConfig config, World world, CropManager cropManager) throws NoSuchFieldException, IllegalAccessException {
        CropConfig cropConfig = cropManager.getCropConfigByID(config.getKey());

        Requirement[] req = cropConfig.getPlantRequirements();
        boolean goodSeason = false; try { goodSeason = checkIfSeasonMatchesRequirements(req, world); } catch (NoSuchFieldException | IllegalAccessException ex) {}


        CCCropsCollection ccItemsCollection = new CCCropsCollection(id, config.getMaxPoints(), goodSeason);
        ArrayList<Plant> firstPlantsList = new ArrayList<>();

        HashMap<Integer, StageConfig> stageMap = Utils.getStages(cropConfig);
        stageMap.forEach((i, stageConfig)->{
            Action[] actions = stageConfig.getBreakActions(); if (actions == null) return;
            for (Action action: actions){
                if (!(action instanceof DropItemImpl)) continue;
                ArrayList<Plant> secondPlantsList = getPlantsFromAction(action);
                secondPlantsList.removeIf(
                        plant2 -> firstPlantsList.stream().anyMatch(plant1 ->
                                plant1.getModelName().equals(plant2.getModelName())
                        )
                );
                firstPlantsList.addAll(secondPlantsList);
            }
        });
        ccItemsCollection.setPlants(firstPlantsList);

        return ccItemsCollection;
    }
    public void updateItems(World world) throws NoSuchFieldException, IllegalAccessException {
        int worldDay = CustomCrops.getInstance().getSeasonManager().getDate(world.getName());
        if (this.day == worldDay) return;
        this.day = worldDay;
        Bukkit.getServer().getLogger().log(Level.INFO, "Updating crops :3");
        this.crops = new ArrayList<CCCropsCollection>();
        CropManager cropManager = CustomCrops.getInstance().getCropManager();
        HashMap<String, CropConfig> hashMapSeed = Utils.getHashMapSeed(cropManager);

        hashMapSeed.forEach((id, config)->{
            try { this.crops.add(getCrop(id, config, world,cropManager));
            } catch (NoSuchFieldException ex) {throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {throw new RuntimeException(ex);}
        });
    }

}
