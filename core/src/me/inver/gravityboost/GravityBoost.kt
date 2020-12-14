package me.inver.gravityboost

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2D
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.ashley.create
import ktx.box2d.createWorld
import ktx.box2d.earthGravity
import me.inver.gravityboost.components.BodyComponent
import me.inver.gravityboost.components.TextureComponent
import me.inver.gravityboost.entities.AsteroidFactory
import me.inver.gravityboost.entities.PlayerFactory
import me.inver.gravityboost.screens.MainMenuScreen

object GravityBoost : KtxGame<KtxScreen>() {
    val batch by lazy { SpriteBatch() }
    val font by lazy { BitmapFont() }

    val pixelsPerMeter = 4
    var score = 0

    override fun create() {
        Box2D.init()
        GameEngine.setupSystems()
        addScreen(MainMenuScreen(this))
        setScreen<MainMenuScreen>()
        super.create()

        for(x in 1..5)
            for(y in 1..5)
                AsteroidFactory.create(Vector2(70f * x - 200f, 70f * y -200f))

        val player = PlayerFactory.create(Vector2(50f, 50f))
        GameEngine.player = player
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        super.dispose()
    }
}