package luis.ferreira.libraries.test;

import processing.core.PApplet;

import static luis.ferreira.libraries.BetterGradients.composeclr;
import static luis.ferreira.libraries.BetterGradients.smootherStepRgb;

public class EasingColorsRGB extends PApplet {
    int halfh;
    int startc, stopc;
    float w, step0, step1, stepc;
    float[] startf, stopf, current = new float[4];
    String fmt = "%s\tX %.2f\tY %.2f\tZ %.2f\tW %.2f";

    public void settings() {
        size(512, 256);
    }

    public void setup() {
        halfh = height / 2;
        w = (float) width;
        rndclrs();
    }

    public void draw() {
        surface.setTitle(String.format("fps %.2f f %d s %.2f",
                frameRate, frameCount, millis() * .001));
        background(0xffffffff);

        step1 = abs(cos(frameCount * 0.01f));
        for (int i = 0; i < width; ++i) {
            step0 = i / w;
            stepc = step0 * step1;

            // Draw Processing gradient.
            stroke(lerpColor(startc, stopc, stepc, RGB));
            line(i, 0, i, halfh);

            // Draw custom gradient.
            smootherStepRgb(startf, stopf, stepc, current);
            stroke(composeclr(current));
            line(i, halfh, i, height);
        }
    }

    void rndclrs() {

        // Create random RGB values.
        startf = new float[]{random(1), random(1), random(1), 1};
        stopf = new float[]{random(1), random(1), random(1), 1};

        // Convert to colors for Processing gradient.
        startc = composeclr(startf);
        stopc = composeclr(stopf);

        // Print color values.
        println(String.format(fmt,
                hex(startc), startf[0], startf[1], startf[2], startf[3]));
        println(String.format(fmt,
                hex(stopc), stopf[0], stopf[1], stopf[2], stopf[3]));
    }

    public void mousePressed() {
        rndclrs();
    }
}
