package com.example.elasticcollision;

public enum MaterialPreset {
    RUBBER("Rubber", 0.99),
    PLASTIC("Plastic", 0.71),
    BILLIARD("Billiard Ball", 0.95),
    METAL("Metal", 0.66),
    PERFECT("Perfect", 1.0);

    private final String name;
    private final double restitution;

    MaterialPreset(String name, double restitution) {
        this.name = name;
        this.restitution = restitution;
    }

    public double restitution() {
        return restitution;
    }

    @Override
    public String toString() {
        return name;
    }
}
