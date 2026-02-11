package com.example.elasticcollision;


public class CollisionObject {

    private double mass;
    private double radius;
    private double x;
    private double velocity;
    private double startVelocity;
    private double restitutionCoefficient;

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getStartVelocity() {
        return startVelocity;
    }

    public void setStartVelocity(double startVelocity) {
        this.startVelocity = startVelocity;
    }

    public double getRestitutionCoefficient() {
        return restitutionCoefficient;
    }

    public void setRestitutionCoefficient(double restitutionCoefficient) {
        this.restitutionCoefficient = restitutionCoefficient;
    }
    public double kineticEnergy() {
        return 0.5 * mass * velocity * velocity;
    }

}
