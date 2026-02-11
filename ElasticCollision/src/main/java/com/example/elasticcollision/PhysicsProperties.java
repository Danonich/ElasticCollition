package com.example.elasticcollision;


public class PhysicsProperties {

    private double wallRestitution;
    private double surfaceRestitution;
    private double frictionCoefficient;

    public double getWallRestitution() {
        return wallRestitution;
    }

    public void setWallRestitution(double wallRestitution) {
        this.wallRestitution = wallRestitution;
    }

    public double getSurfaceRestitution() {
        return surfaceRestitution;
    }

    public void setSurfaceRestitution(double surfaceRestitution) {
        this.surfaceRestitution = surfaceRestitution;
    }

    public double getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public void setFrictionCoefficient(double frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }
}
