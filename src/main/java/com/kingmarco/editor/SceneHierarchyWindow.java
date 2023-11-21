package com.kingmarco.editor;

import com.kingmarco.forge.GameObject;
import com.kingmarco.forge.Window;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;

import java.util.List;

public class SceneHierarchyWindow {

    private static String payLoadDragDropType = "SceneHierarchy";

    public void imgui() {
        ImGui.begin("Scene Hierarchy");

        List<GameObject> gameObjects = Window.getScene().getGameObjects();
        int index = 0;
        for (GameObject obj : gameObjects){
            if (!obj.doSerialization()){
                continue;
            }

            boolean treeNodeOpen = doTreeNode(obj, index);

            if (treeNodeOpen){
                ImGui.treePop();
            }
            index++;
        }

        ImGui.end();
    }

    private boolean doTreeNode(GameObject obj, int index){
        ImGui.pushID(index);
        boolean treeNodeOpen = ImGui.treeNodeEx(
                obj.getName(),
                ImGuiTreeNodeFlags.DefaultOpen |
                        ImGuiTreeNodeFlags.FramePadding |
                        ImGuiTreeNodeFlags.OpenOnArrow |
                        ImGuiTreeNodeFlags.SpanAvailWidth,
                obj.getName()
        );
        ImGui.popID();

        if (ImGui.beginDragDropSource()){
            ImGui.setDragDropPayload(payLoadDragDropType, obj);
            ImGui.text(obj.getName());
            ImGui.endDragDropSource();
        }

        if (ImGui.beginDragDropTarget()){
            Object payloadObj = ImGui.acceptDragDropPayload(payLoadDragDropType);
            if (payloadObj != null) {
                if (payloadObj.getClass().isAssignableFrom(GameObject.class)){
                    GameObject playerGameObj = (GameObject) payloadObj;
                    System.out.println("Payload accepted '" + playerGameObj.getName() + "'");
                }
            }
            ImGui.endDragDropTarget();
        }
        return treeNodeOpen;
    }
}
