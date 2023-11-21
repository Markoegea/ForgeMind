package com.kingmarco.editor;

import com.kingmarco.forge.MouseListener;
import com.kingmarco.forge.Window;
import com.kingmarco.observers.EventSystem;
import com.kingmarco.observers.events.Event;
import com.kingmarco.observers.events.EventType;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector2f;

public class MenuBar {

    public void imgui() {

        ImGui.begin("Settings", ImGuiWindowFlags.NoScrollbar |
                ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoTitleBar);

        ImGui.beginMenuBar();

        if(ImGui.beginMenu("File")){
            if (ImGui.menuItem("Save", "Ctrl+S")){
                EventSystem.notify(null, new Event(EventType.SaveLevel));
            }

            if (ImGui.menuItem("Load", "Ctrl+O")) {
                EventSystem.notify(null, new Event(EventType.LoadLevel));
            }

            ImGui.endMenu();
        }

        ImGui.endMenuBar();

        ImGui.end();
    }
}
