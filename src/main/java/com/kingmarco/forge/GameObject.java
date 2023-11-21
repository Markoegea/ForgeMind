package com.kingmarco.forge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kingmarco.components.Component;
import com.kingmarco.components.SpriteRenderer;
import com.kingmarco.components.Transform;
import com.kingmarco.deserializers.ComponentDeserializer;
import com.kingmarco.deserializers.GameObjectDeserializer;
import com.kingmarco.util.AssetPool;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private static int ID_COUNTER = 0;
    private int uid = -1;
    private String name;
    private List<Component> components;
    public transient Transform transform;
    private transient boolean doSerialization = true;
    private transient boolean isDead = false;


    public GameObject(String name){
        this.name = name;
        this.components = new ArrayList<>();

        this.uid = ID_COUNTER++;
    }

    public <T extends Component> T getComponent(Class<T> componentClass){
        for (Component c : this.components){
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException cce){
                    cce.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i=0; i < this.components.size(); i++){
            Component c = this.components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                this.components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        c.generateId();
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(float dt){
        for (int i=0; i < components.size(); i++){
            components.get(i).update(dt);
        }
    }

    public void editorUpdate(float dt) {
        for (int i=0; i < components.size(); i++){
            components.get(i).editorUpdate(dt);
        }
    }

    public void start(){
        for (int i=0; i < components.size(); i++){
            components.get(i).start();
        }
    }

    public void imgui(){
        for (Component c : components){
            if (ImGui.collapsingHeader(c.getClass().getSimpleName())){
                c.imgui();
            }
        }
    }

    public void destroy() {
        this.isDead = true;
        for (int i=0; i < components.size(); i++){
            components.get(i).destroy();
        }
    }

    public GameObject copy(){
        // TODO: Come up with a clean solution
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        String objAsJson = gson.toJson(this);
        GameObject obj = gson.fromJson(objAsJson, GameObject.class);
        obj.generateUid();
        for (Component c : obj.getAllComponents()){
            c.generateId();
        }

        SpriteRenderer sprite = obj.getComponent(SpriteRenderer.class);
        if (sprite != null && sprite.getTexture() != null){
            sprite.setTexture(AssetPool.getTexture(sprite.getTexture().getFilepath()));
        }
        return obj;
    }

    public boolean isDead() {
        return isDead;
    }

    public static void init(int maxId){
        ID_COUNTER = maxId;
    }

    public int getUid() {
        return this.uid;
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    public void setNoSerialize() {
        this.doSerialization = false;
    }

    public boolean doSerialization() {
        return this.doSerialization;
    }

    public void generateUid() {
        this.uid = ID_COUNTER++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
