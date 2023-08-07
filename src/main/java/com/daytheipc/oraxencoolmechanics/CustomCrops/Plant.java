package com.daytheipc.oraxencoolmechanics.CustomCrops;

public class Plant {
    private String modelName;



    private Integer qualityModifier;

    Plant(String modelName, Integer qualityModifier) {
        this.modelName = modelName;
        this.qualityModifier = qualityModifier;
    }

    public String getModelName() {
        return modelName;
    }
    public Integer getQualityModifier() {return qualityModifier;}
}
