package me.inver.gravityboost.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import me.inver.gravityboost.GameEngine
import me.inver.gravityboost.components.BodyComponent
import me.inver.gravityboost.components.TextureComponent
import kotlin.random.Random
import me.inver.gravityboost.GravityBoost.pixelsPerMeter

object PlayerFactory {
    val texture = Texture(Gdx.files.internal("textures/player.png"))
    val random = Random(2077)

    fun create(position: Vector2 = Vector2(0f, 0f)): Entity {
        //Create blank entity
        val entity = Entity()
        entity.flags = 1

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

        //Add fixture to body
        val body = entity.getComponent(BodyComponent::class.java).body
        val hitbox = CircleShape()
        hitbox.radius = texture.width.toFloat() / pixelsPerMeter / 2

        val fixtureDef = FixtureDef()
        fixtureDef.shape = hitbox
        fixtureDef.density = 1f
        fixtureDef.restitution = 0f

        body.createFixture(fixtureDef)
        hitbox.dispose()

        //Add entity to the engine
        GameEngine.addEntity(entity)

        return entity
    }
}