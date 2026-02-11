package com.example.elasticcollision;

public enum FrictionPreset {

    DRY_WOOD("Dry Wood", 0.6),
    DRY_STEEL("Dry Steel", 0.5),
    ICE("Ice", 0.1),
    OIL_ON_STEEL("Oil on Steel", 0.02),
    PERFECT("Perfect", 0.0);

    private final String name;
    private final double coefficient;

    FrictionPreset(String name, double coefficient) {
        this.name = name;
        this.coefficient = coefficient;
    }

    public double coefficient() {
        return coefficient;
    }

    @Override
    public String toString() {
        return name;
    }
}
