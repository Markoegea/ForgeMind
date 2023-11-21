package com.kingmarco.renderer;

import com.kingmarco.forge.Window;
import com.kingmarco.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.*;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DebugDraw {
    private static List<DrawLines> drawLines = new ArrayList<>();

    public static void beginFrame() {
        if (drawLines.size() <= 0) return;
        for (int i=0; i < drawLines.size(); i++){
            drawLines.get(i).beginFrame();
        }
    }

    public static void draw() {
        if (drawLines.size() <= 0) return;
        for (DrawLines lines : drawLines){
            lines.draw();
        }
    }

    public static void addDrawLines(DrawLines drawLine){
        DebugDraw.drawLines.add(drawLine);
        Collections.sort(DebugDraw.drawLines);
    }

    public static void removeDrawLines(DrawLines drawLine){
        DebugDraw.drawLines.remove(drawLine);
        Collections.sort(DebugDraw.drawLines);
    }

    public static void removeDrawLines(int index){
        DebugDraw.drawLines.remove(index);
        Collections.sort(DebugDraw.drawLines);
    }
}
