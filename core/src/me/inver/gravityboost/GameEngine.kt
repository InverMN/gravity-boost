package me.inver.gravityboost

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Fixture
import ktx.box2d.createWorld
import me.inver.gravityboost.components.BodyComponent
import me.inver.gravityboost.systems.PhysicsSystem

object GameEngine : Engine() {
    val physicsSystem = PhysicsSystem()
    lateinit var player: Entity

    fun setupSystems() {
        addSystem(physicsSystem)
    }
}