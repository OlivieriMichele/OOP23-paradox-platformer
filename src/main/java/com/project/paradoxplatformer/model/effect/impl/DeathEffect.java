package com.project.paradoxplatformer.model.effect.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.project.paradoxplatformer.controller.event.EventManager;
import com.project.paradoxplatformer.model.effect.abstracts.AbstractOneTimeEffect;
import com.project.paradoxplatformer.model.entity.dynamics.ControllableObject;
import com.project.paradoxplatformer.model.obstacles.abstracts.AbstractDeathObstacle;
import com.project.paradoxplatformer.utils.collision.api.CollidableGameObject;
import com.project.paradoxplatformer.view.javafx.PageIdentifier;

public class DeathEffect extends AbstractOneTimeEffect {

    @Override
    protected CompletableFuture<Void> applyToGameObject(CollidableGameObject gameObject) {
        return CompletableFuture.runAsync(() -> {
            ((AbstractDeathObstacle) ((ControllableObject) gameObject)).onCollision(Optional.empty());
        });
    }

    @Override
    protected CompletableFuture<Void> applyToTarget(Optional<? extends CollidableGameObject> target) {
        return CompletableFuture.runAsync(() -> {
            target.ifPresent(t -> {
                if (t instanceof ControllableObject) {
                    ((ControllableObject) t).onCollision();
                    // System.out.println("DeathEffect applied: Player killed.");
                }
            });
        });
    }

}
