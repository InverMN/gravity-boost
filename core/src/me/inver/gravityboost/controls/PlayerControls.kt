package me.inver.gravityboost.controls

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import me.inver.gravityboost.GravityBoost
import me.inver.gravityboost.components.BodyComponent
import kotlin.math.atan2
import me.inver.gravityboost.GravityBoost.pixelsPerMeter
import me.inver.gravityboost.entities.AsteroidFactory
import kotlin.math.cos
import kotlin.math.sin

class PlayerControls(private val player: Entity) {
    val body = player.getComponent(BodyComponent::class.java).body

    fun update(deltaTime: Float) {
        rotateAtMouse()
        if(Gdx.input.isButtonPressed(Input.Keys.LEFT)) move(deltaTime)
    }

    fun rotateAtMouse() {
        val cursorWorldPosition = Vector2((Gdx.input.x - 320) / pixelsPerMeter + body.position.x, (Gdx.input.y - 240) / pixelsPerMeter + body.position.y)
        val dx = cursorWorldPosition.x - body.position.x
        val dy = cursorWorldPosition.y - body.position.y

        val angle = atan2(dx, dy) + 3.14159265f

        body.setTransform(body.position, angle.toFloat())
    }

    fun move(deltaTime: Float) {

        val x = -sin(body.angle)
        val y = cos(body.angle)

        body.applyForceToCenter(body.mass * (x * 100).toFloat(), body.mass * (y * 100).toFloat(), true)
    }
}