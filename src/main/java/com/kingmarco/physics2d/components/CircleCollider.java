package com.kingmarco.physics2d.components;


import com.kingmarco.components.Component;
import com.kingmarco.renderer.DebugDraw;
import com.kingmarco.renderer.DrawLines;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CircleCollider extends Component {
    private float radius = 1f;
    private Vector2f offset = new Vector2f();
    private transient DrawLines drawLines = new DrawLines(2);

    public CircleCollider() {
        drawLines.start();
        DebugDraw.addDrawLines(drawLines);
    }

    public Vector2f getOffset() {
        return this.offset;
    }
    public void setOffset(Vector2f newOffset) {
        this.offset.set(newOffset);
    }
    public float getRadius() {
        return radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void editorUpdate(float dt) {
        Vector2f center = new Vector2f(this.gameObject.transform.position).add(this.offset);
        drawLines.addCircle(center, radius);
    }
}
