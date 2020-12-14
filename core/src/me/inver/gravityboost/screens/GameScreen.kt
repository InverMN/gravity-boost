package com.libktx.game.screen

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import ktx.app.KtxScreen
import ktx.graphics.use
import me.inver.gravityboost.GameEngine
import me.inver.gravityboost.GravityBoost
import me.inver.gravityboost.components.BodyComponent
import me.inver.gravityboost.components.TextureComponent
import me.inver.gravityboost.GravityBoost.pixelsPerMeter
import me.inver.gravityboost.GameEngine.player
import me.inver.gravityboost.controls.PlayerControls

class GameScreen(val game: GravityBoost) : KtxScreen {

    private val camera = OrthographicCamera().apply { setToOrtho(false, 800f, 600f) }
    private lateinit var entities: ImmutableArray<Entity>

    private val debugRendered = Box2DDebugRenderer()

    private val playerControls = PlayerControls(player)

    override fun show() {
        super.show()
        centerCameraAtPlayer()
        setCustomCursor()
    }

    override fun render(delta: Float) {
        camera.update()
        camera.zoom = 0.5f

        playerControls.update(delta)

        centerCameraAtPlayer()
        renderEntities()
        debugRenderBox2D()

        GameEngine.update(delta)
    }

    private fun debugRenderBox2D() {
        debugRendered.render(GameEngine.physicsSystem.world, camera.projection)
    }

    private fun renderEntities() {
        game.batch.use(camera) {
            entities = GameEngine.getEntitiesFor(Family.all(BodyComponent::class.java, TextureComponent::class.java).get())
//            game.font.draw(it, "[ESC TO EXIT]", 175f, 270f)
            val batch = it
            entities.forEach {
                val textureComponent = it.getComponent(TextureComponent::class.java)
                val texture = textureComponent.texture
                val size = textureComponent.size
                val body = it.getComponent(BodyComponent::class.java).body
                val textureRegion = TextureRegion(texture)


//                if(it.flags == 1)
//                    game.font.draw(batch, "SCORE: ${GravityBoost.score}", body.position.x * pixelsPerMeter - 20f, body.position.y * pixelsPerMeter - 15f)

                game.batch.draw(textureRegion, body.position.x * pixelsPerMeter, body.position.y * pixelsPerMeter, size.width / 2, size.height / 2, size.width, size.height, 1f, 1f, body.angle * 57.2957795f)
            }
        }
    }

    private fun centerCameraAtPlayer() {
        val playerPosition = player.getComponent(BodyComponent::class.java).body.position
        val playerSize = player.getComponent(TextureComponent::class.java).size
        val playerCenter = Vector2(playerPosition.x * pixelsPerMeter + playerSize.width / 2, playerPosition.y * pixelsPerMeter + playerSize.height / 2)
        camera.position.set(playerCenter.x, playerCenter.y, 0f)
    }

    fun setCustomCursor() {
        val cursorPixmap = Pixmap(Gdx.files.internal("textures/game_cursor.png"))
        val xHotSpot = cursorPixmap.width / 2
        val yHotSpot = cursorPixmap.height / 2
        val cursor = Gdx.graphics.newCursor(cursorPixmap, xHotSpot, yHotSpot)
        Gdx.graphics.setCursor(cursor)
        cursorPixmap.dispose()
    }
}