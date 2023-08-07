package com.daytheipc.oraxencoolmechanics.CustomCrops;


import java.util.ArrayList;

public class CCCropsCollection {
    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getSeedModelName() {
        return seedModelName;
    }

    public void setSeedModelName(String seedModelName) {
        this.seedModelName = seedModelName;
    }

    public void setGoodSeason(boolean goodSeason) {
        isGoodSeason = goodSeason;
    }

    private Integer maxPoints;
    private String seedModelName;

    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    private ArrayList<Plant> plants;
    private boolean isGoodSeason;
    public boolean isGoodSeason() {
        return isGoodSeason;
    }


    public CCCropsCollection(String modelName, Integer maxPoints, boolean isGoodSeason){
        this.maxPoints = maxPoints;
        this.seedModelName = modelName;
        this.isGoodSeason = isGoodSeason;
    }
}
