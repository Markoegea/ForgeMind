package com.kingmarco.components;

import com.kingmarco.editor.PropertiesWindow;
import com.kingmarco.forge.*;
import com.kingmarco.renderer.DebugDraw;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class Gizmo extends Component{
    protected Vector4f xAxisColor = new Vector4f(0.5f, 0, 0, 0.3f);
    protected Vector4f xAxisColorHover = new Vector4f(1f, 0f, 0f, 0f);
    protected Vector4f yAxisColor = new Vector4f(0, 0.5f, 0, 0.3f);
    protected Vector4f yAxisColorHover = new Vector4f(0f, 1f, 0f, 0f);

    protected GameObject xAxisObject;
    protected GameObject yAxisObject;
    protected SpriteRenderer xAxisSprite;
    protected SpriteRenderer yAxisSprite;
    protected GameObject activeGameObject = null;

    protected Vector2f xAxisOffset = new Vector2f(24f / 80f, -6 / 80f);
    protected Vector2f yAxisOffset = new Vector2f(-7f / 80f, 21f / 80f);

    protected float gizmoWidth = 16f / 80f;
    protected float gizmoHeight = 48f / 80f;

    protected boolean xAxisActive = false;
    protected boolean yAxisActive = false;
    protected PropertiesWindow propertiesWindow;
    protected boolean deactivate = false;

    public Gizmo(Sprite Sprite, PropertiesWindow propertiesWindow){
        this.xAxisObject = Prefabs.generateSpriteObject(Sprite, gizmoWidth, gizmoHeight);
        this.yAxisObject = Prefabs.generateSpriteObject(Sprite, gizmoWidth, gizmoHeight);
        this.xAxisSprite = this.xAxisObject.getComponent(SpriteRenderer.class);
        this.yAxisSprite = this.yAxisObject.getComponent(SpriteRenderer.class);
        this.propertiesWindow = propertiesWindow;

        this.xAxisObject.transform.zIndex = 100;
        this.yAxisObject.transform.zIndex = 100;

        Window.getScene().addGameObjectToScene(this.xAxisObject);
        Window.getScene().addGameObjectToScene(this.yAxisObject);
    }

    public Gizmo(PropertiesWindow propertiesWindow){
        this.propertiesWindow = propertiesWindow;
    }

    @Override
    public void start() {
        this.xAxisObject.transform.rotation = 90;
        this.yAxisObject.transform.rotation = 180;
        this.xAxisObject.setNoSerialize();
        this.yAxisObject.setNoSerialize();
    }

    @Override
    public void update(float dt) {
        if (!deactivate){
            deactivate = true;
            this.setInactive();
        }
    }

    @Override
    public void editorUpdate(float dt) {
        this.activeGameObject = this.propertiesWindow.getActiveGameObject();

        if (this.activeGameObject != null) {
            this.setActive();
        } else {
            this.setInactive();
            return;
        }

        boolean xAxisHot = checkXHoverState();
        boolean yAxisHot = checkYHoverState();

        if ((xAxisHot || xAxisActive) && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
            xAxisActive = true;
            yAxisActive = false;
        } else if ((yAxisHot || yAxisActive) && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
            yAxisActive = true;
            xAxisActive = false;
        } else {
            xAxisActive = false;
            yAxisActive = false;
        }

        if (this.activeGameObject != null){
            Vector2f gameObjectPosition = this.activeGameObject.transform.position;
            this.xAxisObject.transform.position.set(gameObjectPosition);
            this.yAxisObject.transform.position.set(gameObjectPosition);
            this.xAxisObject.transform.position.add(this.xAxisOffset);
            this.yAxisObject.transform.position.add(this.yAxisOffset);
        }
    }

    protected void setActive(){
        this.xAxisSprite.setColor(xAxisColor);
        this.yAxisSprite.setColor(yAxisColor);
    }

    protected void setInactive() {
        this.xAxisSprite.setColor(new Vector4f(0, 0, 0, 0));
        this.yAxisSprite.setColor(new Vector4f(0, 0, 0, 0));
    }

    protected void setBehavior(){
        if (this.xAxisObject != null && this.yAxisObject != null) {
            if (deactivate){
                this.setInactive();
            } else {
                this.setActive();
            }
        }
    }

    private boolean checkXHoverState() {
        Vector2f mousePos = MouseListener.getWorld();
        if (mousePos.x <= xAxisObject.transform.position.x + (gizmoHeight / 2.0f) &&
                mousePos.x >= xAxisObject.transform.position.x - (gizmoHeight / 2.0f) &&
                mousePos.y >= xAxisObject.transform.position.y - (gizmoWidth / 2.0f) &&
                mousePos.y <= xAxisObject.transform.position.y + (gizmoWidth / 2.0f)){
            xAxisSprite.setColor(xAxisColorHover);
            return true;
        }
        xAxisSprite.setColor(xAxisColor);
        return false;
    }

    private boolean checkYHoverState() {
        Vector2f mousePos = MouseListener.getWorld();
        if (mousePos.x <= yAxisObject.transform.position.x + (gizmoWidth / 2.0f) &&
                mousePos.x >= yAxisObject.transform.position.x - (gizmoWidth / 2.0f) &&
                mousePos.y <= yAxisObject.transform.position.y + (gizmoHeight / 2.0f) &&
                mousePos.y >= yAxisObject.transform.position.y - (gizmoHeight / 2.0f)){
            yAxisSprite.setColor(yAxisColorHover);
            return true;
        }

        yAxisSprite.setColor(yAxisColor);
        return false;
    }

    public void setDeactivate(boolean deactivate) {
        this.deactivate = deactivate;
        this.setBehavior();
    }
}
