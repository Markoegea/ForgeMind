package com.kingmarco.components;

import com.kingmarco.editor.PropertiesWindow;
import com.kingmarco.forge.MouseListener;

public class ScaleGizmo extends Gizmo {
    public ScaleGizmo(Sprite scalesSprite, PropertiesWindow propertiesWindow){
        super(scalesSprite, propertiesWindow);
    }

    @Override
    public void editorUpdate(float dt) {
        if (deactivate) return;
        if (activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.scale.x -= MouseListener.getWorldX();
            } else if (yAxisActive) {
                activeGameObject.transform.scale.y -= MouseListener.getWorldY();
            }
        }
        super.editorUpdate(dt);
    }
}
