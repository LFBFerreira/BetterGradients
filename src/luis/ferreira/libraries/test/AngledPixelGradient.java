package luis.ferreira.libraries.test;

import processing.core.PApplet;
import processing.core.PGraphics;

import static luis.ferreira.libraries.BetterGradients.lerpColorWrapper;

public class AngledPixelGradient extends PApplet {
    int[] palette = { 0xffff7f00, 0xff007fff, 0xff7f00ff };
    PGraphics renderer;

    public void settings()
    {
        size(512, 256);
    }

    public void setup() {
        renderer = createGraphics(width, height);
        renderer.beginDraw();
        renderer.loadPixels();

        float w = renderer.width;
        float h = renderer.height;


        // Imaginary line of gradient.
        float ox = random(0, w);
        float oy = random(0, h);
        float dx = random(0, w);
        float dy = random(0, h);
        float st;

        for (int i = 0, y = 0, x; y < h; ++y) {
            for (x = 0; x < w; ++x, ++i) {
                st = project(ox, oy, dx, dy, x, y);
                renderer.pixels[i] = lerpColorWrapper(palette, st, RGB);
            }
        }
        renderer.updatePixels();

        renderer.strokeWeight(1);
        renderer.stroke(255);
        renderer.line(ox, oy, dx, dy);

        image(renderer, 0, 0);
    }

    float project(float originX, float originY,
                  float destX, float destY,
                  int pointX, int pointY) {
        // Rise and run of line.
        float odX = destX - originX;
        float odY = destY - originY;

        // Distance-squared of line.
        float odSq = odX * odX + odY * odY;

        // Rise and run of projection.
        float opX = pointX - originX;
        float opY = pointY - originY;
        float opXod = opX * odX + opY * odY;

        // Normalize and clamp range.
        return constrain(opXod / odSq, 0.0f, 1.0f);
    }

}
