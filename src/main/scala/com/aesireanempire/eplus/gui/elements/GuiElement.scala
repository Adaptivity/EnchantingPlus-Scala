package com.aesireanempire.eplus.gui.elements

import com.aesireanempire.eplus.GUIAdvEnchantment
import cpw.mods.fml.client.FMLClientHandler
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

abstract class GuiElement(var posX: Int, var posY: Int, var width: Int, var height: Int, u: Int,
                          texture: ResourceLocation, val screen: GUIAdvEnchantment) extends Gui {

    private def setPosition(x: Int, y: Int) = {
        posX = x
        posY = y
    }

    def handleMovementChange(dY: Int) = {
        setPosition(posX, posY + dY)
    }

    def isVisible: Boolean

    def isDragging: Boolean = dragging

    def setDragging(b: Boolean) = dragging = b

    def isUnderMouse(x: Int, y: Int): Boolean = {
        if (posX <= x && x <= posX + width) {
            if (posY <= y && y <= posY + height) {
                return true
            }
        }
        false
    }

    def mouseMoved(x: Int, y: Int)

    private var dragging: Boolean = false


    def draw() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
        FMLClientHandler.instance().getClient.renderEngine.bindTexture(texture)

        drawTop()
        drawMiddle()
        drawBottom()

        drawExtras()
    }

    def drawExtras()

    def update()


    private def drawTop() {
        drawTexturedModalRect(posX, posY, u, 0, if (width >= 15) 15 else width, 15)
        if (width > 30) {
            for (x <- posX + 15 to posX + width - 15 by 15) {
                drawTexturedModalRect(x, posY, u + 15, 0, 15, 15)
            }
        }

        if (width >= 30) {
            drawTexturedModalRect(posX + width - 15, posY, u + 30, 0, 15, 15)
        }
    }

    private def drawMiddle() {
        if (height >= 30) {
            for (y <- posY + 15 to posY + height - 15 by 15) {
                drawTexturedModalRect(posX, y, u, 15, if (width >= 15) 15 else width, 15)

                if (width > 30) {
                    for (x <- posX + 15 to posX + width - 15 by 15) {
                        drawTexturedModalRect(x, y, u + 15, 15, 15, 15)
                    }
                }
                if (width >= 30) {
                    drawTexturedModalRect(posX + width - 15, y, u + 30, 15, 15, 15)
                }
            }
        }
    }

    private def drawBottom() {
        val y: Int = posY + height - 15
        drawTexturedModalRect(posX, y, u, 30, if (width >= 15) 15 else width, 15)
        if (width > 30) {
            for (x <- posX + 15 to posX + width - 15 by 15) {
                drawTexturedModalRect(x, y, u + 15, 30, 15, 15)
            }
        }

        if (width >= 30) {
            drawTexturedModalRect(posX + width - 15, y, u + 30, 30, 15, 15)
        }
    }
}