package com.project.paradoxplatformer.model.player;

import org.apache.commons.lang3.tuple.Pair;

import com.project.paradoxplatformer.utils.geometries.coordinates.Coord2D;
import com.project.paradoxplatformer.utils.geometries.interpolations.InterpolatorFactory;
import com.project.paradoxplatformer.utils.geometries.interpolations.InterpolatorFactoryImpl;
import com.project.paradoxplatformer.utils.geometries.modifiers.PhysicsEngine;
import com.project.paradoxplatformer.utils.geometries.vector.api.Polar2DVector;
import com.project.paradoxplatformer.utils.geometries.vector.api.Simple2DVector;
import com.project.paradoxplatformer.utils.geometries.vector.api.Vector2D;

public class MovementController {

    private Vector2D horizontalSpeed;
    private Vector2D verticalSpeed;
    private Coord2D position;
    private final PhysicsEngine physics;
    private final InterpolatorFactory interpolatorFactory;

    public MovementController(Coord2D startPosition, PhysicsEngine physicsEngine) {
        this.position = startPosition;
        this.physics = physicsEngine;
        this.interpolatorFactory = new InterpolatorFactoryImpl();
        this.horizontalSpeed = Polar2DVector.nullVector(); // Inizializzazione della velocità orizzontale
        this.verticalSpeed = Polar2DVector.nullVector();   // Inizializzazione della velocità verticale
    }

    public Coord2D updateMovement(long dt) {
        // Interpolazione del movimento orizzontale
        Vector2D newDisplacement = physics.step(this.toVector2D(position),
                this.toVector2D(position).add(horizontalSpeed),
                interpolatorFactory.linear(),
                dt);

        // Movimento verticale (es. salto o caduta)
        Pair<Vector2D, Double> verticalMovement = physics.moveTo(newDisplacement,
                newDisplacement.add(verticalSpeed), 1, interpolatorFactory.easeIn(), dt);

        // Aggiorna la posizione del giocatore
        this.position = verticalMovement.getLeft().convert(); 
        return this.position;
    }

    public void setSpeed(Vector2D speed) {
        this.horizontalSpeed = speed;
    }

    public Vector2D getSpeed() {
        return this.horizontalSpeed;
    }

    public void stopMovement() {
        this.horizontalSpeed = physics.stop();
        this.verticalSpeed = physics.stop();
    }

    private Vector2D toVector2D(Coord2D position) {
        return new Simple2DVector(position.x(),position.y());
    }
    
}