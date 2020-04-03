package luis.ferreira.libraries.test;

import java.io.File;
import java.io.IOException;

import static processing.core.PApplet.runSketch;

public class SketchLauncher {
    private static final int DISPLAY_INDEX = 2; // 1 -> Main Screen, 2 -> Second Screen

    public static void main(String[] args){

        ConvertionHsbToRgb sketch = new ConvertionHsbToRgb();

        String sketchPath = "";

        try {
            sketchPath = new File(".").getCanonicalPath().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] localArgs = new String[]{"--display=" + DISPLAY_INDEX,
                "--sketch-path=" + sketchPath,
                sketch.getClass().getCanonicalName()};

        runSketch(localArgs, sketch);
    }
}