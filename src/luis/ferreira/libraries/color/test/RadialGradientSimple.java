package luis.ferreira.libraries.color.test;

import processing.core.PApplet;

public class RadialGradientSimple extends PApplet {
        public void settings() {
            size(512, 256);

        }

        public void setup() {
            int colorMode = random(1) < 0.5 ? HSB : RGB;

            colorMode(colorMode, 1, 1, 1, 1);

            int start, stop, current;
            if (colorMode == HSB) {
                float h = random(1);
                start = color(h, 1.0f, random(0.25f, 1f), 1f);
                stop = color((h + 0.333f) % 1.0f, 1.0f, random(0.25f, 1.0f), 1.0f);
            } else {
                start = color(random(0.25f, 1.0f), random(0.25f, 1.0f), random(0.25f, 1.0f), 1.0f);
                stop = color(random(0.25f, 1.0f), random(0.25f, 1.0f), random(0.25f, 1.0f), 1.0f);
            }

            float innerDiam = 32;
            float outerDiam = 512;
            float detail = 10;

            noStroke();
            background(1);

            float centerXStart = random(width);
            float centerYStart = random(height);
            float offset = (outerDiam * 0.5f) / sqrt(2);
            float centerXStop = random(centerXStart - offset, centerXStart + offset);
            float centerYStop = random(centerYStart - offset, centerYStart + offset);

            float centerX, centerY;

            // This for loop starts drawing the largest circle on the
            // bottom then works up and inward to draw the smallest.
            for (float i = outerDiam, step; i >= innerDiam; i -= detail) {
                step = map(i, innerDiam, outerDiam, 0, 1);

                current = lerpColor(start, stop, step, colorMode);

                // To offset the radial gradient, move the center of the
                // circle each step through the for-loop.
                centerX = lerp(centerXStart, centerXStop, step);
                centerY = lerp(centerYStart, centerYStop, step);

                fill(current);
                ellipse(centerX, centerY, i, i);
            }
        }
}
