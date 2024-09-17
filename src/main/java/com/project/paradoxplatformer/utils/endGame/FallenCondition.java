package com.project.paradoxplatformer.utils.endGame;

import com.project.paradoxplatformer.model.player.PlayerModel;

/**
 * FallenCondition checks if the player's height has fallen below zero.
 */
public class FallenCondition implements DeathCondition {

    private final PlayerModel player;

    /**
     * Constructs a FallenCondition with the specified player model.
     *
     * @param player the player model to check the health of.
     */
    public FallenCondition(final PlayerModel player) {
        this.player = player;
    }

    /**
     * Checks if the player's health is below zero.
     *
     * @return true if the player's height is below zero, false otherwise.
     */
    @Override
    public boolean death() {
        return player.getPosition().y() <= 0;
    }

}
