package com.kingmarco.renderer;

import org.joml.Vector2f;

public class MathForms {

    public static void rotate(Vector2f vert, float angleDeg, Vector2f center) {
        float angle = (float) Math.toRadians(angleDeg);

        // Calculate sine and cosine of the angle
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        // Translate the point to the origin (center of rotation)
        float translatedX = vert.x - center.x;
        float translatedY = vert.y - center.y;

        // Calculate the rotated coordinates
        vert.x = (((translatedX * cos) - (translatedY * sin)) + center.x);
        vert.y = (((translatedX * sin)  + (translatedY * cos)) + center.y);
    }
}
