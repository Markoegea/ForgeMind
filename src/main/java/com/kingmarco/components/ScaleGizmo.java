package com.kingmarco.components;

import com.kingmarco.editor.PropertiesWindow;
import com.kingmarco.forge.MouseListener;
import org.joml.Vector2f;

public class ScaleGizmo extends Gizmo {
    public ScaleGizmo(Sprite scalesSprite, PropertiesWindow propertiesWindow){
        super(scalesSprite, propertiesWindow);
    }

    @Override
    public void editorUpdate(float dt) {
        if (deactivate) return;
        if (activeGameObject != null) {
            Vector2f delta = MouseListener.screenToWorld(MouseListener.getScreenD());
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.scale.x -= delta.x;
            } else if (yAxisActive) {
                activeGameObject.transform.scale.y -= -delta.y;
            }
        }
        super.editorUpdate(dt);
    }
}
