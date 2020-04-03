import luis.ferreira.libraries.*;
import com.hamoid.*;

MediaExport mediaExport;

public void setup() {
    size(300, 200, PConstants.P2D);
    mediaExport = new MediaExport(100, 30, ".mp4", "png", this);
}


public void draw() {
    background(frameCount % 255);
    mediaExport.saveFrame();
}


public void keyPressed() {
    switch (keyCode) {
    case 97: // F1
    mediaExport.toggleVideoRecording();
    break;
    case 98: // F2
    mediaExport.endCapture();
    break;
    case 99: // F3
    mediaExport.takeScreenshot();
    break;
    }
}