package luis.ferreira.libraries.color.test;

import processing.core.PApplet;

import static luis.ferreira.libraries.color.BetterGradients.lerpColorWrapper;


public class AnimatedLineEquidistantGradient extends PApplet {
    int red = color(255, 0, 0, 255);
    int blue = color(0, 0, 255, 255);
    int currentStroke;

    int[] palette = new int[]{ 0xffff0000, 0xff00ffff, 0xffffff00 };

    int margin = 32;
    int bottom0;
    int bottom1;
    int left = margin;
    int right;
    int top0 = margin;
    int top1;

    float loopStep;
    float animStep;

    public void settings()
    {
        size(512, 256);
    }

    public void setup() {
        right = width - margin;
        bottom0 = height / 2;
        top1 = bottom0 + 1;
        bottom1 = height - margin;
    }

    public void draw() {

        // Display the fps, frames and seconds.
        surface.setTitle(String.format("fps %.2f f %d s %.2f",
                frameRate, frameCount, millis() * 0.001));
        background(255);

        // sin returns a value in the range of -1 .. 1, so
        // absolute is used to convert it to a range of 0 .. 1
        animStep = abs(sin(frameCount * 0.016666667f));

        // Top rectangle.
        // Animation step and loop step are multiplied.
        for (int i = left; i <= right; i += 1) {
            loopStep = map(i, left, right, 0, 1);
            currentStroke = lerpColorWrapper(palette, (loopStep + animStep) * 0.5f, HSB);
            stroke(currentStroke);
            line(i, top0, i, bottom0);
        }

        // Bottom rectangle.
        // Animation step and loop step are averaged.
        for (int i = top1; i <= bottom1; i += 1) {
            loopStep = map(i, top1, bottom1, 0, 1);
            currentStroke = lerpColorWrapper(palette, (loopStep + animStep) * 0.5f, RGB);
            stroke(currentStroke);
            line(left, i, right, i);
        }
    }
}
