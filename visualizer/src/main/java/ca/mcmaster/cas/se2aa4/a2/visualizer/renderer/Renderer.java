package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.visualizer.configuration.Configuration;

import java.awt.*;

public interface Renderer {

    void render(Structs.Mesh aMesh, Graphics2D canvas, Configuration config);
}
