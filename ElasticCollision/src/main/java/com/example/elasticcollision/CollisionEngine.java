package com.example.elasticcollision;


public class CollisionEngine {

    private final double timeStep;

    public CollisionEngine(double timeStep) {
        this.timeStep = timeStep;
    }

    public void update(
            CollisionObject a,
            CollisionObject b,
            PhysicsProperties props,
            double width
    ) {
        collideWithWalls(a, props, width);
        collideWithWalls(b, props, width);

        if (a.getX() + a.getRadius() >= b.getX() - b.getRadius()) {
            resolveCollision(a, b, props);
        } else {
            applyFriction(a, props);
            applyFriction(b, props);
        }

        a.setX(a.getX() + a.getVelocity() * timeStep);
        b.setX(b.getX() + b.getVelocity() * timeStep);
    }

    private void resolveCollision(
            CollisionObject a,
            CollisionObject b,
            PhysicsProperties props
    ) {
        double v1 =
                ((a.getMass() - b.getMass()) * a.getVelocity()
                        + 2 * b.getMass() * b.getVelocity())
                        / (a.getMass() + b.getMass());

        double v2 =
                ((b.getMass() - a.getMass()) * b.getVelocity()
                        + 2 * a.getMass() * a.getVelocity())
                        / (a.getMass() + b.getMass());

        a.setVelocity(v1 * a.getRestitutionCoefficient());
        b.setVelocity(v2 * b.getRestitutionCoefficient());

        applyFriction(a, props);
        applyFriction(b, props);
    }

    private void applyFriction(CollisionObject obj, PhysicsProperties props) {
        obj.setVelocity(
                obj.getVelocity()
                        - props.getFrictionCoefficient() * obj.getVelocity() * timeStep
        );
    }

    private void collideWithWalls(
            CollisionObject obj,
            PhysicsProperties props,
            double width
    ) {
        if (obj.getX() - obj.getRadius() < 0) {
            obj.setVelocity(Math.abs(obj.getVelocity()) * props.getWallRestitution());
        }

        if (obj.getX() + obj.getRadius() > width) {
            obj.setVelocity(-Math.abs(obj.getVelocity()) * props.getWallRestitution());
        }
    }
}
