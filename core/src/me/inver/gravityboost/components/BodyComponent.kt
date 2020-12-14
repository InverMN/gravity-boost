package me.inver.gravityboost.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.BodyDef
import me.inver.gravityboost.GameEngine

class BodyComponent(bodyDef: BodyDef) : Component {
    val body = GameEngine.physicsSystem.world.createBody(bodyDef)
}