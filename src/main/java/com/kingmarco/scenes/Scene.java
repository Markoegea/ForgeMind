package com.kingmarco.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kingmarco.components.SpriteRenderer;
import com.kingmarco.components.Transform;
import com.kingmarco.deserializers.ComponentDeserializer;
import com.kingmarco.deserializers.GameObjectDeserializer;
import com.kingmarco.forge.Camera;
import com.kingmarco.components.Component;
import com.kingmarco.forge.GameObject;
import com.kingmarco.physics2d.Physics2D;
import com.kingmarco.renderer.Renderer;
import imgui.ImGui;
import org.joml.Vector2f;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scene {

    private Renderer renderer;
    private Camera camera;
    private boolean isRunning;
    private List<GameObject> gameObjects;
    private List<GameObject> pendingObject;
    private Physics2D physics2D;
    private SceneInitializer sceneInitializer;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
            .registerTypeAdapter(Component.class, new ComponentDeserializer())
            .enableComplexMapKeySerialization()
            .create();

    public Scene(SceneInitializer sceneInitializer){
        this.sceneInitializer = sceneInitializer;
        this.renderer = new Renderer();
        this.physics2D = new Physics2D();
        this.gameObjects = new ArrayList<>();
        this.pendingObject = new ArrayList<>();
        this.isRunning = false;
    }

    public Physics2D getPhysics() {
        return this.physics2D;
    }

    public void init() {
        this.camera = new Camera(new Vector2f());
        this.sceneInitializer.loadResources(this);
        this.sceneInitializer.init(this);
    }

    public void awake() {
        for (int i =0; i < gameObjects.size(); i++){
            GameObject go = gameObjects.get(i);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        isRunning = true;
    }

    public GameObject createGameObject(String name) {
        GameObject go = new GameObject(name);
        go.addComponent((new Transform()));
        go.transform = go.getComponent(Transform.class);
        return go;
    }

    public void addGameObjectToScene(GameObject go){
        if (!isRunning){
            gameObjects.add(go);
        } else {
            pendingObject.add(go);
        }
    }

    public void destroy() {
        for (int i =0; i < gameObjects.size(); i++){
            GameObject go = gameObjects.get(i);
            go.destroy();
        }
    }

    public <T extends Component> GameObject getGameObjectWith(Class<T> clazz){
        for (GameObject go : gameObjects){
            if (go.getComponent(clazz) != null) {
                return go;
            }
        }

        return null;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameObject getGameObject(int gameObjectId) {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getUid() == gameObjectId)
                .findFirst();
        return result.orElse(null);
    }

    public GameObject getGameObject(String gameObjectName) {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getName().equals(gameObjectName))
                .findFirst();
        return result.orElse(null);
    }

    public void removeGameObjectOfScene(GameObject gameObject){
        for(GameObject go: gameObjects){
            if (go.getUid() == gameObject.getUid()){
                gameObjects.remove(go);
                break;
            }
        }
    }

    public void start() {
    }

    public void editorUpdate(float dt){
        this.camera.adjustProjection();
        //System.out.println("FPS: " + (1.0f / dt));

        for (int i=0; i < gameObjects.size(); i++){
            GameObject go = gameObjects.get(i);
            go.editorUpdate(dt);

            if(go.isDead()){
                gameObjects.remove(i);
                this.renderer.destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;
            }
        }

        for (GameObject go : pendingObject){
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        pendingObject.clear();
    }

    public void update(float dt){
        this.camera.adjustProjection();
        this.physics2D.update(dt);
        //System.out.println("FPS: " + (1.0f / dt));
        /*float t = 0.0f;
        float x = (Math.sin(t) * 200.0f) + 600;
        float y = (Math.cos(t) * 200.0f) + 400;
        float nx = (Math.sin(t) * (-200.0f)) + 600;
        float ny = (Math.cos(t) * (-200.0f)) + 400;
        t += 0.05f;
        DebugDraw.addLine2D(new Vector2f(600, 400), new Vector2f(x, y), new Vector3f(1,0,0), 135);
        DebugDraw.addLine2D(new Vector2f(600, 400), new Vector2f(nx, ny), new Vector3f(0,0,1), 30);
        DebugDraw.addBox2D(new Vector2f(200, 200), new Vector2f(64, 32), angle, new Vector3f(0, 1, 0), 1);
        angle += 50.0f * dt;

        DebugDraw.addCircle(new Vector2f(x, y), 64, new Vector3f(0, 1, 0), 1);
        x += 50f * dt;
        y += 50f * dt;*/

        for (int i=0; i < gameObjects.size(); i++){
            GameObject go = gameObjects.get(i);
            go.update(dt);

            if(go.isDead()){
                gameObjects.remove(i);
                this.renderer.destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;
            }
        }

        for (GameObject go : pendingObject){
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        pendingObject.clear();
    }
    public void render(){
        this.renderer.render();
    }

    public Camera camera(){
        return this.camera;
    }

    public void imgui(){
        this.sceneInitializer.imgui();
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter("level.txt");
            List<GameObject> objsToSerialize = new ArrayList<>();
            for (GameObject obj : this.gameObjects) {
                if (obj.doSerialization()){
                    objsToSerialize.add(obj);
                }
            }
            writer.write(gson.toJson(objsToSerialize));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!inFile.isEmpty()){
            int maxGoId = -1;
            int maxComId = -1;
            GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
            for (int i=0; i < objs.length; i++){
                addGameObjectToScene(objs[i]);
                for (Component c : objs[i].getAllComponents()){
                    if (c.getUid() > maxComId){
                        maxComId = c.getUid();
                    }
                }
                if (objs[i].getUid() > maxGoId){
                    maxGoId = objs[i].getUid();
                }
            }

            maxGoId++;
            maxComId++;
            GameObject.init(maxGoId);
            Component.init(maxComId);
        }
    }
}
