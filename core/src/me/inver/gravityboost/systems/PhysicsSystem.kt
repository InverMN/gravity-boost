package me.inver.gravityboost.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import ktx.box2d.body
import ktx.box2d.box
import ktx.box2d.createWorld
import me.inver.gravityboost.components.BodyComponent
import me.inver.gravityboost.components.TextureComponent
import kotlin.math.log

class PhysicsSystem : EntitySystem() {
    val world = createWorld()
    var iterator = 0

    private lateinit var entities: ImmutableArray<Entity>

    private var bodyComponents = ComponentMapper.getFor(BodyComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        entities = engine!!.getEntitiesFor(Family.all(BodyComponent::class.java).get())
        addBorders()
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        world.step(deltaTime, 1, 1)
    }

    fun addBorders() {
        addSingleBorder(Rectangle(0f, 160f, 410f, 30f))
        addSingleBorder(Rectangle(0f, -160f, 410f, 30f))
        addSingleBorder(Rectangle(210f, 0f, 30f, 350f))
        addSingleBorder(Rectangle(-210f, 0f, 30f, 350f))
    }

    fun addSingleBorder(rectangle: Rectangle) {
        world.body(type = BodyDef.BodyType.StaticBody) {
            box(width = rectangle.width, height = rectangle.height, position = Vector2(rectangle.x, rectangle.y))
        }
    }
}