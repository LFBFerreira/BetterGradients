package luis.ferreira.libraries.color.test;

import processing.core.PApplet;

import static luis.ferreira.libraries.color.BetterGradients.*;


public class EasingColorsHSV extends PApplet {
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
            stroke(lerpColor(startc, stopc, stepc, HSB));
            line(i, 0, i, halfh);

            // Draw custom gradient.
            smootherStepHsb(startf, stopf, stepc, current);
            stroke(composeclr(hsbToRgb(current)));
            line(i, halfh, i, height);
        }
    }

    void rndclrs() {
        startf = new float[] { random(1), 1, 1, 1 };
        stopf = new float[] { random(1), 1, 1, 1 };
        startc = composeclr(hsbToRgb(startf));
        stopc = composeclr(hsbToRgb(stopf));
        /* ... */
    }

    float[] smootherStepHsb(float[] a, float[] b, float st, float[] out) {

        // Find difference in hues.
        float huea = a[0];
        float hueb = b[0];
        float delta = hueb - huea;

        // Prefer shortest distance.
        if (delta < -0.5) {
            hueb += 1.0;
        } else if (delta > 0.5) {
            huea += 1.0;
        }

        float eval = smootherStep(st);

        // The two hues may be outside of 0 .. 1 range,
        // so modulate by 1.
        out[0] = (huea + eval * (hueb - huea)) % 1;
        out[1] = a[1] + eval * (b[1] - a[1]);
        out[2] = a[2] + eval * (b[2] - a[2]);
        out[3] = a[3] + eval * (b[3] - a[3]);
        return out;
    }
}
