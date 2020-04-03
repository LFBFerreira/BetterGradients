package luis.ferreira.libraries.color.test;

import processing.core.PApplet;

public class VertexGradient extends PApplet {
    public void settings()
    {
        size(512, 256, P2D);
    }

    public void setup() {
        noStroke();

        // Has no effect. Mixing is done by shaders.
        colorMode(HSB, 255, 255, 255, 255);

        // Left rectangle.
        // Vertical gradient.
        beginShape();

        fill(0xffff0000);
        // Top-left corner.
        vertex(0, 0);
        // Top-right corner.
        vertex(width * 0.5f, 0);

        fill(0xff00ffff);
        // Bottom-right corner.
        vertex(width * 0.5f, height);
        // Bottom-left corner.
        vertex(0, height);

        endShape();

        // Right rectangle.
        // Horizontal gradient.
        beginShape();

        fill(0xffff00ff);
        // Bottom-left corner.
        vertex(width * 0.5f, height);
        // Top-left corner.
        vertex(width * 0.5f, 0);

        fill(0xffffff00);
        // Top-right corner.
        vertex(width, 0);
        // Bottom-right corner.
        vertex(width, height);

        endShape();
    }
}
