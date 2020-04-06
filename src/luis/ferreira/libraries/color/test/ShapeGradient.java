package luis.ferreira.libraries.color.test;

import luis.ferreira.libraries.color.BetterGradients;
import processing.core.PApplet;

import static luis.ferreira.libraries.color.BetterGradients.lerpColorSmoother;

public class ShapeGradient extends PApplet {
    int currentRender = 0;
    int detail = 20;
    int colorMode = RGB;

    float cx0, cy0, cx1, cy1, radius;
    float iprc = 0;
    float jprc = 0;
    float t0 = 0;
    float t1 = 0;
    float dtf = detail;

    int[] palette = {0xffff7f00, 0xff7fff00,
            0xff00ff7f, 0xff007fff,
            0xff7f00ff, 0xffff007f};
    int[] renderTypes = {
            TRIANGLES, TRIANGLE_STRIP, TRIANGLE_FAN,
            QUADS, QUAD_STRIP, POLYGON};
    String[] typelabels = {
            "TRIANGLES", "TRIANGLE_STRIP", "TRIANGLE_FAN",
            "QUADS", "QUAD_STRIP", "POLYGON"};

    public void settings() {
        size(512, 256, P3D);
    }

    public void setup() {

        // Left shape.
        cx0 = width * 0.25f;
        cy0 = height * 0.5f;

        // Right shape.
        cx1 = width * 0.75f;
        cy1 = height * 0.5f;

        radius = min(width, height) * 0.5f;
    }

    public void draw() {
        surface.setTitle(String.format("%.2f %s %s",
                frameRate, typelabels[currentRender],
                colorMode == HSB ? "HSB" : "RGB"));
        background(0xffffffff);

        // Draw left shape from <1, 0, 0>.
        iprc = 0;
        t0 = 0;
        beginShape(renderTypes[currentRender]);
        for (int i = 0; i < detail; ++i) {
            iprc = i / dtf;
            t0 = TWO_PI * iprc;
            fill(BetterGradients.lerpColorSmoother(palette, iprc, colorMode));
            vertex(cx0 + cos(t0) * radius, cy0 + sin(t0) * radius, 0.0f);
        }
        endShape(CLOSE);

        // Draw right shape from <0, 0, 0>.
        iprc = 0;
        jprc = 0;
        t0 = 0;
        t1 = 0;
        beginShape(renderTypes[currentRender]);

        // Begin at center.
        fill(0xffffffff);
        vertex(cx1, cy1, 0);

        for (int i = 0, j = 1; i < detail; ++i, j = (j + 1) % detail) {
            iprc = i / dtf;
            jprc = j / dtf;
            t0 = TWO_PI * iprc;
            t1 = TWO_PI * jprc;

            // Move from center to current angle.
            fill(BetterGradients.lerpColorSmoother(palette, iprc, colorMode));
            vertex(cx1 + cos(t0) * radius, cy1 + sin(t0) * radius, 0.0f);

            // Move from current to next angle.
            fill(BetterGradients.lerpColorSmoother(palette, jprc, colorMode));
            vertex(cx1 + cos(t1) * radius, cy1 + sin(t1) * radius, 0.0f);

            // Return to center.
            fill(0xffffffff);
            vertex(cx1, cy1, 0.0f);
        }
        endShape(CLOSE);
    }

    public void mouseReleased() {
        if (mouseButton == LEFT) {
            currentRender = (currentRender + 1) % renderTypes.length;
        } else if (mouseButton == RIGHT) {
            colorMode = colorMode == HSB ? RGB : HSB;
        }
    }
}
