package luis.ferreira.libraries.color.test;

import luis.ferreira.libraries.color.BetterGradients;
import processing.core.PApplet;
import processing.core.PGraphics;

import static luis.ferreira.libraries.color.BetterGradients.lerpColorSmoother;


public class PixelGradient extends PApplet {
    int[] palette = {0xffff7f00, 0xff007fff, 0xff7f00ff};
    PGraphics renderer;

    public void settings() {
        size(512, 256);
    }

    public void setup() {
        renderer = createGraphics(width, height);
        renderer.beginDraw();
        renderer.loadPixels();

        float w = renderer.width;
        float h = renderer.height;

        for (int i = 0, y = 0, x; y < h; ++y) {
            for (x = 0; x < w; ++x, ++i) {
                renderer.pixels[i] = BetterGradients.lerpColorSmoother(palette, x / w, RGB);
            }
        }
        renderer.updatePixels();
        renderer.endDraw();
        image(renderer, 0, 0);
    }
}
