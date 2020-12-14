package me.inver.gravityboost.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.libktx.game.screen.GameScreen
import ktx.app.KtxScreen
import ktx.graphics.use
import me.inver.gravityboost.GravityBoost

class MainMenuScreen(val game: GravityBoost) : KtxScreen {
    private val camera = OrthographicCamera().apply { setToOrtho(false, 800f, 600f) }
    private val title = Texture("textures/game_title.png")

    override fun render(delta: Float) {
        camera.update()

        game.batch.use(camera) {
            game.batch.draw(title, 150f, 450f)
            game.font.draw(it, "Ship rotates at cursor", 300f, 250f)
            game.font.draw(it, "To move press right mouse button", 300f, 230f)
            game.font.draw(it, "Fire by clicking left mouse button", 300f, 210f)
            game.font.setColor(1f, 0f, 0f, 1f)
            game.font.draw(it, "Destroy all asteroids!", 300f, 160f)
            game.font.setColor(1f, 1f, 1f, 1f)
            game.font.draw(it, "[PRESS ANY KEY TO START]", 300f, 120f)
        }
        
        if (Gdx.input.isTouched || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.addScreen(GameScreen(game))
            game.setScreen<GameScreen>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
    }
}