package com.kingmarco.scenes;

import com.kingmarco.components.*;
import com.kingmarco.forge.GameObject;

public class Prefabs {

    //TODO: Your Code here
    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY){
        return MyPrefabs.generateSpriteObject(sprite,sizeX,sizeY);
    }

    public static GameObject generatePrefab(String file){
        return MyPrefabs.generatePrefab(file);
    }
}
