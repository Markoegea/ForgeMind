package com.kingmarco.physics2d.components;

import com.kingmarco.components.Component;
import com.kingmarco.renderer.DebugDraw;
import com.kingmarco.renderer.DrawLines;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Box2DCollider extends Component {
    private Vector2f halfSize = new Vector2f(1);
    private Vector2f origin = new Vector2f();
    private Vector2f offset = new Vector2f();
    private transient DrawLines drawLines = new DrawLines(2);

    public Box2DCollider() {
        drawLines.start();
        DebugDraw.addDrawLines(drawLines);
    }

    public Vector2f getOffset() {
        return this.offset;
    }
    public void setOffset(Vector2f newOffset) {
        this.offset.set(newOffset);
    }
    public Vector2f getHalfSize() {
        return halfSize;
    }
    public void setHalfSize(Vector2f halfSize) {
        this.halfSize = halfSize;
    }
    public Vector2f getOrigin() {
        return this.origin;
    }

    @Override
    public void editorUpdate(float dt) {
        Vector2f center = new Vector2f(this.gameObject.transform.position).add(this.offset);
        drawLines.addBox2D(center, this.halfSize, this.gameObject.transform.rotation, new Vector3f(0f, 0f, 1f));
    }
}
