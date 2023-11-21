package com.kingmarco.components;

import com.kingmarco.forge.KeyListener;
import com.kingmarco.forge.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class GizmoSystem extends Component {

    private SpritesSheet gizmos;
    private int usingGizmo = 0;

    public GizmoSystem(SpritesSheet gizmos) {
        this.gizmos = gizmos;
    }

    @Override
    public void start() {
        gameObject.addComponent(new TranslateGizmo(Window.getImGuiLayer().getPropertiesWindow()));
        gameObject.addComponent(new ScaleGizmo(gizmos.getSprite(2),
                Window.getImGuiLayer().getPropertiesWindow()));
    }

    @Override
    public void editorUpdate(float dt) {
        if (usingGizmo == 0){
            gameObject.getComponent(TranslateGizmo.class).setDeactivate(false);
            gameObject.getComponent(ScaleGizmo.class).setDeactivate(true);
        } else if (usingGizmo == 1){
            gameObject.getComponent(TranslateGizmo.class).setDeactivate(true);
            gameObject.getComponent(ScaleGizmo.class).setDeactivate(false);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_E)){
            usingGizmo = 0;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_R)){
            usingGizmo = 1;
        }
    }
}
