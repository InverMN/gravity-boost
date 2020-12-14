package me.inver.gravityboost.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import me.inver.gravityboost.GameEngine
import me.inver.gravityboost.components.BodyComponent
import me.inver.gravityboost.components.TextureComponent
import kotlin.random.Random
import me.inver.gravityboost.GravityBoost.pixelsPerMeter

object AsteroidFactory {
    val texture = Texture(Gdx.files.internal("textures/medium_asteroid.png"))
    val random = Random(2077)

    fun create(position: Vector2 = Vector2(0f, 0f)): Entity {
        //Create blank entity
        val entity = Entity()

        //Add texture component
        var textureSize = Rectangle()
        textureSize.width = texture.width.toFloat()
        textureSize.height = texture.height.toFloat()
        entity.add(TextureComponent(textureSize, texture))

        //Add body component
        var bodyDef = BodyDef()
        bodyDef.position.set(position)
        bodyDef.type = BodyDef.BodyType.DynamicBody
        entity.add(BodyComponent(bodyDef))

        //Apply random force to asteroid
        var force = Vector2(random.nextFloat() * 30000f, random.nextFloat() * 30000f)
        var angularVelocity = random.nextFloat() * 0.2f
        val body = entity.getComponent(BodyComponent::class.java).body

        if(random.nextBoolean()) angularVelocity *= -1
        if(random.nextBoolean()) force.x *= -1
        if(random.nextBoolean()) force.y *= -1

        body.applyForceToCenter(force, true)
        body.angularVelocity = angularVelocity

        //Add fixture to body
        val shape = PolygonShape()
        shape.setAsBox(texture.width.toFloat() / pixelsPerMeter / 2, texture.width.toFloat() / pixelsPerMeter / 2)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f
        fixtureDef.restitution = 0f

        body.createFixture(fixtureDef)
        shape.dispose()

        //Add entity to the engine
        GameEngine.addEntity(entity)

        return entity
    }
}