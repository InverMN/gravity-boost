package me.inver.gravityboost.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle

class TextureComponent(val size: Rectangle, val texture: Texture) : Component {}