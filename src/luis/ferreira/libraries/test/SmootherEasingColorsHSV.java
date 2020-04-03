package luis.ferreira.libraries.test;

import processing.core.PApplet;

import static luis.ferreira.libraries.BetterGradients.*;

public class SmootherEasingColorsHSV extends PApplet {

    int halfh;
    int startc, stopc;
    float w, step0, step1, stepc;
    float[] startf, stopf, current = new float[4];
    String fmt = "%s\tX %.2f\tY %.2f\tZ %.2f\tW %.2f";

    float[][] palettef;
    int[] palettec;
    int clrcount = 5;

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
            stroke(lerpColorWrapper(palettec, stepc, RGB));
            line(i, 0, i, halfh);

            smootherStepHsb(palettef, stepc, current);
            stroke(composeclr(current));
            line(i, halfh, i, height);
        }
    }

    void rndclrs() {
        palettef = new float[clrcount][4];
        for (int i = 0; i < clrcount; ++i) {
            palettef[i] = new float[]{random(1.0f), random(1.0f), random(1.0f), 1.0f};
        }
        palettec = composeclr(palettef);
    }


    public void mousePressed() {
        rndclrs();
    }
}
