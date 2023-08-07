package com.daytheipc.oraxencoolmechanics.CustomCrops;

import net.momirealms.customcrops.api.object.crop.CropConfig;
import net.momirealms.customcrops.api.object.crop.CropManager;
import net.momirealms.customcrops.api.object.crop.StageConfig;
import net.momirealms.customcrops.api.object.loot.QualityLoot;
import net.momirealms.customcrops.api.object.requirement.SeasonImpl;
import net.momirealms.customcrops.api.object.season.CCSeason;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class Utils {
    static public HashMap<String, CropConfig> getHashMapSeed(@NotNull CropManager cropManager) throws NoSuchFieldException, IllegalAccessException {
        Field seedToCropConfig = cropManager.getClass().getDeclaredField("seedToCropConfig");
        seedToCropConfig.setAccessible(true);
        return (HashMap<String, CropConfig>) seedToCropConfig.get(cropManager);
    }
    static public List<CCSeason> getSeasonsFromRequired(@NotNull SeasonImpl seasonimpl) throws NoSuchFieldException, IllegalAccessException {
        Field seasonsField = seasonimpl.getClass().getDeclaredField("seasons");
        seasonsField.setAccessible(true);
        return (List<CCSeason>) seasonsField.get(seasonimpl);
    }
    static public HashMap<Integer, StageConfig> getStages(@NotNull CropConfig cropConfig) throws NoSuchFieldException, IllegalAccessException {
        Field stageMapField = cropConfig.getClass().getDeclaredField("stageMap");
        stageMapField.setAccessible(true);
        return (HashMap<Integer, StageConfig>) stageMapField.get(cropConfig);
    }

    static public String[] getQualityLoots(@NotNull QualityLoot qualityLoot) throws NoSuchFieldException, IllegalAccessException {
        Field qualityLootsField = qualityLoot.getClass().getDeclaredField("qualityLoots");
        qualityLootsField.setAccessible(true);
        return (String[]) qualityLootsField.get(qualityLoot);
    }
}
