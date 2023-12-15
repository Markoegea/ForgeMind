# ForgeMind

A 2D game engine made in Java, using the LWJGL (Lightweight Java Game Library).

## Table of Contents

- [Description](#description)
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [Credits](#credits)
- [License](#license)
- [Contact Information](#contact-information)
- [FAQs](#faqs)
- [Examples/Demo](#examplesdemo)
- [Roadmap](#roadmap)
- [Security](#security)
- [Dependencies](#dependencies)

## Description

This project is a 2D game engine (Name under development too), that give you the tools to create a wide range of games, like RPG, Action, Fight, Adventure, Racing, using the programming Language Java. But it's still in a early development stage, it use the LWJG Library, that is basically a collections of other libraries that enable the engine to work with the GPU to render images, textures and music, for the physics, the engine use the Jbox2D library, for the UI the engine use the ImGuI library that allow you to see the object variables and change them and place object in the game world, and GSON to serialize and deserialize game objects, this game engine is totally free, and nobody is going to charge you for your game.

## Installation

For the moment the installation procces is very easy:
1. Go to the code button
2. Select if you want to clone the project with Github or Download the ZIP folder.
3. Open the project with your favorite code editor or IDE (In my case I use IntelliJ IDEA)
4. Import the necessary libraries
5. Enjoy!

## Usage

In the project, we have 9 important folders, in the src/main/java/com/kingmarco path:

1. The first one is "components", store the engine logic, and allow you to interact with the game.
2. The second is "deserializers", only contain the necessary classes that indicate to GSON, how to serialize and deserialize objects.
3. The third is "editor", contains the necessary classes to display the items in the ImGuI, like the game view, the object variables, the objects hierarchy.
4. The fourth is "forge", contains the core of the game engine, all the necessary classes to make the engine works.
5. The fifth is "observers", contains a implementation of the observers pattern to notify the engine when a specific event happens.
6. The sixth is "physics2d", have the classes that give the game objects the physics, colliders and raycasts.
7. The seventh is "renderer", contains the classes that are the responsable for render the graphics, lines and textures.
8. The eighth is "scenes", that have the scenes classes, indicate what have to be seen and show, the editor scene and then, the game scene.
9. The ninth is "util", have two helping classes that, load the sprites, shaders, texture and sounds from the system, and indicate the size of the grid.

## Features

- 2D game engine
- Sounds
- Animations
- Physics

## Documentation

COMING SOON!

## Contributing

COMING SOON!

## Credits

- https://github.com/jbox2d/jbox2d - The library responsable for calculate the physics in the engine.
- https://youtu.be/025QFeZfeyM?si=vsTN189_NiZNzyTH - freeCodeCamp.org video that show me how to build a 2D game engine.
- https://www.lwjgl.org/ - Page of the Lightweight Java Game Library that contains all the libraries necesary to run the engine.
- https://github.com/google/gson - The Github project responsable for the Gson library.
- https://github.com/ocornut/imgui - The Github project responsable for the UI of the engine.

## License

Project under the Apache License 2.0.

## Contact Information

Gmail: thekingmarco03@gmail.com
Instagram: https://www.instagram.com/titancloudofficial/

## FAQs

Make your question and they will be posted here.

## Examples/Demo

COMING SOON!

## Roadmap

The future plans for the 2D game engine are:
 
- Fix the gizmo system, to make it easy to use.
- Improve the editor to make it more helpful and readable.
- Improve the scene hierarchy to be able to modify, delete and reorganize the game objects.
- Add the option to map all the files in the folder, to create prefabs, add components, sound and animations, without code it directly.
- Create my first game with the engine.
- Simplify the process to convert the project folder in a executable to run the game.
- Integrate the editor with AI deep learning models to improve the enemy and companions behavior.

This plan will be updated with new steps, or modify the previous ones.

## Security

Any information regarding security vulnerabilities, please write to the email: thekingmarco03@gmail.com.

## Dependencies

1. **jbox2d**: This is a physics engine library used in the project.

2. **GSON (Google Gson)**: A library for Java that helps with JSON serialization and deserialization.

3. **ImGuI (Dear ImGui)**: A graphical user interface library used for creating graphical interfaces.
   - `imgui-java-binding`: Java bindings for ImGui.
   - `imgui-java-lwjgl3`: LWJGL3 bindings for ImGui.
   - `imgui-java-natives-windows-ft`: Native files required for ImGui to work on Windows using LWJGL3.

4. **LWJGL (Lightweight Java Game Library)**: A library providing access to native APIs used in game development.
   - `lwjgl-bom`: A Bill of Materials (BOM) defining versions for LWJGL modules.
   - Individual modules:
     - `lwjgl`: Core LWJGL module.
     - `lwjgl-assimp`: LWJGL module for Assimp (3D model import library).
     - `lwjgl-glfw`: LWJGL module providing access to the GLFW library for windowing.
     - `lwjgl-nfd`: LWJGL module for Native File Dialog (cross-platform file dialog library).
     - `lwjgl-openal`: LWJGL module for OpenAL (audio library).
     - `lwjgl-opengl`: LWJGL module for OpenGL (graphics rendering).
     - `lwjgl-stb`: LWJGL module containing bindings for various stb libraries (image loading, etc.).

5. **JOML (Java OpenGL Math Library)**: A math library for computer graphics programming.

