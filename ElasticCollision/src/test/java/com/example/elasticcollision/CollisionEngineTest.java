package com.example.elasticcollision;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollisionEngineTest {

    @Test
    void doesNotResolveRepeatedCollisionWhenObjectsAreSeparating() {
        CollisionEngine engine = new CollisionEngine(0.016);

        CollisionObject a = new CollisionObject();
        a.setRadius(10);
        a.setMass(1);
        a.setX(50);
        a.setVelocity(-20);
        a.setRestitutionCoefficient(1);

        CollisionObject b = new CollisionObject();
        b.setRadius(10);
        b.setMass(1);
        b.setX(69);
        b.setVelocity(20);
        b.setRestitutionCoefficient(1);

        PhysicsProperties props = new PhysicsProperties();
        props.setFrictionCoefficient(0);
        props.setWallRestitution(1);

        engine.update(a, b, props, 500);

        assertEquals(-20, a.getVelocity(), 1e-9);
        assertEquals(20, b.getVelocity(), 1e-9);
    }

    @Test
    void clampsObjectInsideLeftWallAfterCollision() {
        CollisionEngine engine = new CollisionEngine(0.016);

        CollisionObject a = new CollisionObject();
        a.setRadius(10);
        a.setMass(1);
        a.setX(5);
        a.setVelocity(-50);
        a.setRestitutionCoefficient(1);

        CollisionObject b = new CollisionObject();
        b.setRadius(10);
        b.setMass(1);
        b.setX(300);
        b.setVelocity(0);
        b.setRestitutionCoefficient(1);

        PhysicsProperties props = new PhysicsProperties();
        props.setFrictionCoefficient(0);
        props.setWallRestitution(1);

        engine.update(a, b, props, 500);

        assertTrue(a.getX() >= a.getRadius());
        assertTrue(a.getVelocity() > 0);
    }
}
