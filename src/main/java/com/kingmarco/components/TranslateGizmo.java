package com.kingmarco.components;

import com.kingmarco.editor.PropertiesWindow;
import com.kingmarco.forge.MouseListener;
import com.kingmarco.renderer.DebugDraw;
import com.kingmarco.renderer.DrawLines;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class TranslateGizmo extends Gizmo{

    private final DrawLines drawLines = new DrawLines(2);
    private boolean hover = false;
    private float constantScale = 1.1f;

    public TranslateGizmo(PropertiesWindow propertiesWindow){
        super(propertiesWindow);
    }

    @Override
    public void start() {
        drawLines.start();
        DebugDraw.addDrawLines(drawLines);
    }

    @Override
    public void editorUpdate(float dt) {
        if (deactivate) return;

        if (activeGameObject != null) {
            if (hover && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                Vector2f delta = MouseListener.screenToWorld(MouseListener.getScreenD());
                activeGameObject.transform.position.x -= delta.x;
                activeGameObject.transform.position.y -= -delta.y;
            }
            drawLine(activeGameObject.transform.position, activeGameObject.transform.scale);
        }

        hover = checkHoverState();

        if (!hover){
            this.activeGameObject = this.propertiesWindow.getActiveGameObject();
        }
    }

    private void drawLine(Vector2f gameObjectPosition, Vector2f gameObjectScale){
        drawLines.addBox2D(new Vector2f(gameObjectPosition.x, gameObjectPosition.y),
                new Vector2f(gameObjectScale.x * constantScale,
                        gameObjectScale.y * constantScale), 0);
    }

    private boolean checkHoverState() {
        if (this.activeGameObject == null) return false;
        Vector2f mousePos = MouseListener.getWorld();
        Vector2f gameObjectPosition = this.activeGameObject.transform.position;
        Vector2f gameObjectScale = this.activeGameObject.transform.scale;
        return mousePos.x >= (gameObjectPosition.x - gameObjectScale.x) &&
                mousePos.x <= (gameObjectPosition.x + gameObjectScale.x) &&
                mousePos.y >= (gameObjectPosition.y - gameObjectScale.y) &&
                mousePos.y <= (gameObjectPosition.y + gameObjectScale.y);
    }

    @Override
    protected void setActive() {

    }

    @Override
    protected void setInactive() {

    }
}
