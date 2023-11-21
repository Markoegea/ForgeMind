package com.kingmarco.renderer;

import com.kingmarco.forge.Camera;
import com.kingmarco.forge.Window;
import com.kingmarco.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DrawLines implements Comparable<DrawLines>{
    private int MAX_LINES = 5000;
    private List<Line2D> lines = new ArrayList<>();
    // 6 floats per vertex, 2 vertex per line
    private float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private Shader shader = AssetPool.getShader("assets/shaders/debugLine2D.glsl");

    private int vaoID;
    private int vboID;

    private boolean started = false;

    private final int zIndex;

    public DrawLines(int zIndex) {
        this.zIndex = zIndex;
    }

    public void start() {
        //Generate the vao
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create the vbo and buffer some memory
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        //Enable the vertex array attributes
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glLineWidth(2f);
    }

    public void beginFrame() {
        if (!started){
            start();
            started = true;
        }

        //Remove deadlines
        for (int i=0; i < lines.size(); i++){
            if (lines.get(i).beginFrame() < 0){
                lines.remove(i);
                i--;
            }
        }
    }

    public void draw() {
        if (lines.size() <= 0) return;
        int index = 0;
        for (Line2D line : lines){
            for (int i=0; i < 2; i++){
                Vector2f position = i == 0 ? line.getFrom() : line.getTo();
                Vector3f color = line.getColor();

                //Load position
                vertexArray[index] = position.x;
                vertexArray[index + 1] = position.y;
                vertexArray[index + 2] = -10.0f;

                // Load the color
                vertexArray[index + 3] = color.x;
                vertexArray[index + 4] = color.y;
                vertexArray[index + 5] = color.z;
                index += 6;
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_DYNAMIC_DRAW);
        //glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray,0, lines.size() * 6 * 2));

        //Use our shader
        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        //Bind the vao
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        //Draw the batch
        glDrawArrays(GL_LINES, 0, lines.size());

        // Disable Location
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        //Unbind shader
        shader.detach();
    }

    // =========================================
    // Add line2D methods
    // =========================================
    public void addLine2D(Vector2f from, Vector2f to) {
        // TODO: ADD CONSTANTS FOR COMMON COLORS
        addLine2D(from, to, new Vector3f(0, 1, 0), 1, 0);
    }

    public void addLine2D(Vector2f from, Vector2f to, Vector3f color) {
        // TODO: ADD CONSTANTS FOR COMMON COLORS
        addLine2D(from, to, color, 1, 0);
    }

    public void addLine2D(Vector2f from, Vector2f to, int zIndex) {
        // TODO: ADD CONSTANTS FOR COMMON COLORS
        addLine2D(from, to, new Vector3f(0, 1, 0), 1, zIndex);
    }

    public void addLine2D(Vector2f from, Vector2f to, Vector3f color, int zIndex) {
        // TODO: ADD CONSTANTS FOR COMMON COLORS
        addLine2D(from, to, color, 1, zIndex);
    }

    public void addLine2D(Vector2f from, Vector2f to, Vector3f color, int lifeTime, int zIndex){
        /*Camera camera = Window.getScene().camera();
        Vector2f cameraLeft = new Vector2f(camera.position).add(new Vector2f(-2.0f, -2.0f));
        Vector2f cameraRight = new Vector2f(camera.position).
                add(new Vector2f(camera.getProjectionSize()).mul(camera.getZoom())).
                add(new Vector2f(4.0f, 4.0f));
        boolean lineInView = ((from.x >= cameraLeft.x && from.x <= cameraRight.x) && (from.y >= cameraLeft.y && from.y <= cameraRight.y)) ||
                ((to.x >= cameraLeft.x && to.x <= cameraRight.x) && (to.y >= cameraLeft.y && to.y <= cameraRight.y));
        if (lines.size() >= MAX_LINES || !lineInView) return;*/
        if (lines.size() >= MAX_LINES) return;
        lines.add(new Line2D(from, to, color, lifeTime));
    }

    // =========================================
    // Add Box2D methods
    // =========================================
    public void addBox2D(Vector2f center, Vector2f dimensions, float rotation) {
        // TODO: ADD CONSTANTS FOR COMMON COLORS
        addBox2D(center, dimensions, rotation, new Vector3f(0, 1, 0), 1);
    }

    public void addBox2D(Vector2f center, Vector2f dimensions, float rotation, Vector3f color) {
        // TODO: ADD CONSTANTS FOR COMMON COLORS
        addBox2D(center, dimensions, rotation, color, 1);
    }

    public void addBox2D(Vector2f center, Vector2f dimensions, float rotation, Vector3f color, int lifeTime){
        Vector2f min = new Vector2f(center).sub(new Vector2f(dimensions).mul(0.5f));
        Vector2f max = new Vector2f(center).add(new Vector2f(dimensions).mul(0.5f));

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
                new Vector2f(max.x, max.y), new Vector2f(max.x, min.y)
        };

        if (rotation != 0.0f) {
            for (Vector2f vert : vertices) {
                MathForms.rotate(vert, rotation, center);
            }
        }

        addLine2D(vertices[0], vertices[1], color, lifeTime, 1);
        addLine2D(vertices[0], vertices[3], color, lifeTime, 1);
        addLine2D(vertices[1], vertices[2], color, lifeTime, 1);
        addLine2D(vertices[2], vertices[3], color, lifeTime, 1);
    }

    // =========================================
    // Add Circle methods
    // =========================================
    public void addCircle(Vector2f center, float radius) {
        // TODO: ADD CONSTANTS FOR COMMON COLORS
        addCircle(center, radius, new Vector3f(0, 1, 0), 1);
    }

    public void addCircle(Vector2f center, float radius, Vector3f color) {
        // TODO: ADD CONSTANTS FOR COMMON COLORS
        addCircle(center, radius, color, 1);
    }

    public void addCircle(Vector2f center, float radius, Vector3f color, int lifeTime){
        Vector2f[] points = new Vector2f[50];
        int increment = 360 / points.length;
        int currentAngle = 0;

        for (int i=0; i < points.length; i++){
            Vector2f tmp = new Vector2f(radius, 0);
            MathForms.rotate(tmp, currentAngle, new Vector2f());
            points[i] = new Vector2f(tmp).add(center);

            if (i > 0) {
                addLine2D(points[i - 1], points[i], color, lifeTime, 1);
            }
            currentAngle += increment;
        }
        addLine2D(points[points.length - 1], points[0], color, lifeTime, 1);
    }

    @Override
    public int compareTo(DrawLines o) {
        return Integer.compare(this.zIndex, o.zIndex);
    }
}
