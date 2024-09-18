package com.project.paradoxplatformer.model.world;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.paradoxplatformer.model.obstacles.Obstacle;
import com.project.paradoxplatformer.model.obstacles.Saw;
import com.project.paradoxplatformer.model.player.PlayerModel;
import com.project.paradoxplatformer.model.trigger.Floor;
import com.project.paradoxplatformer.model.trigger.Trigger;
import com.project.paradoxplatformer.model.world.api.World;
import com.project.paradoxplatformer.utils.geometries.Dimension;
import com.project.paradoxplatformer.utils.geometries.coordinates.Coord2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link WordBuilderImpl} class, ensuring correct
 * functionality
 * for adding game elements and building the world.
 * <p>
 * Tests include:
 * <ul>
 * <li>Adding a player, obstacles, triggers, and bounds.</li>
 * <li>Ensuring the world can only be built once.</li>
 * <li>Verifying that all components are properly set in the final world.</li>
 * <li>Handling of worlds built without certain elements, such as a player.</li>
 * </ul>
 */
public final class WordBuilderImplTest {

    private static final int PLAYER_WIDTH = 30;
    private static final int PLAYER_HEIGHT = 30;
    private static final int WORLD_WIDTH = 1000;
    private static final int WORLD_HEIGHT = 800;
    private static final int SAW_ID = 2;
    private static final int FLOOR_TRIGGER_ID = 1;
    private static final Coord2D ORIGIN = Coord2D.origin();

    private WordBuilderImpl builder;
    private PlayerModel playerTest;
    private Obstacle sawTest;
    private Trigger floorTrigger;
    private Dimension worldBounds;
    private Dimension mockDimension;

    @BeforeEach
    void setUp() {
        builder = new WordBuilderImpl();

        // Mock dependencies
        this.playerTest = new PlayerModel();
        this.mockDimension = new Dimension(PLAYER_WIDTH, PLAYER_HEIGHT);
        this.sawTest = new Saw(SAW_ID, ORIGIN, mockDimension, null);
        this.worldBounds = new Dimension(WORLD_WIDTH, WORLD_HEIGHT); // World dimensions
        this.floorTrigger = new Floor(FLOOR_TRIGGER_ID, new Coord2D(2, 0), mockDimension, null);
    }

    @Test
    void testAddPlayer() {
        builder.addPlayer(playerTest); // Add a player

        // Attempt to build the world
        World world = builder.build();

        // Verify the player is included in the built world
        assertNotNull(world.player(), "Player should be present in the world.");
    }

    @Test
    void testAddObstacle() {
        builder.addObstacle(sawTest); // Add an obstacle

        // Attempt to build the world
        World world = builder.build();

        // Verify the obstacle is included in the world
        assertTrue(world.obstacles().contains(sawTest), "Obstacle should be present in the world.");
    }

    @Test
    void testBuildOnlyOnce() {
        builder.addPlayer(playerTest).addBounds(worldBounds); // Add player and bounds
        builder.build(); // First build

        // Try to add another element after the world is built
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            builder.addObstacle(sawTest); // Adding more elements should fail
        });

        assertEquals("World is already built, cannot rebuild!", exception.getMessage());

        // Try to build the world again, which should also fail
        assertThrows(IllegalStateException.class, () -> {
            builder.build();
        });
    }

    @Test
    void testBuildCompleteWorld() {
        // Add all components: player, obstacle, trigger, and bounds
        builder.addPlayer(playerTest)
                .addObstacle(sawTest)
                .addTrigger(floorTrigger)
                .addBounds(worldBounds);

        // Build the world
        World world = builder.build();

        // Verify that all elements are present in the world
        assertEquals(playerTest.getID(), world.player().getID(), "Player should be set correctly.");
        assertTrue(world.obstacles().contains(sawTest), "Obstacle should be added to the world.");
        assertTrue(world.triggers().contains(floorTrigger), "Trigger should be added to the world.");
        assertEquals(worldBounds, world.bounds(), "World bounds should be set correctly.");
    }

    @Test
    void testBuildWithoutPlayer() {
        builder.addBounds(worldBounds); // Add only bounds, no player

        // Build the world
        World world = builder.build();

        // Verify the world is built without a player
        assertNull(world.player(), "Player should be null if not added.");
        assertEquals(worldBounds, world.bounds(), "World bounds should be set correctly.");
    }
}
