import luis.ferreira.libraries.color.*;

int halfh;
int startc, stopc;
float w, step0, step1, stepc;
float[] startf, stopf, current = new float[4];
String fmt = "%s\tX %.2f\tY %.2f\tZ %.2f\tW %.2f";

void setup() {
  size(512, 256);
  halfh = height / 2;
  w = (float) width;
  rndclrs();
}

void draw() {
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
    BetterGradients.smootherStepRgb(startf, stopf, stepc, current);
    stroke(BetterGradients.composeclr(current));
    line(i, halfh, i, height);
  }
}

void rndclrs() {

  // Create random RGB values.
  startf = new float[]{random(1), random(1), random(1), 1};
  stopf = new float[]{random(1), random(1), random(1), 1};

  // Convert to colors for Processing gradient.
  startc = BetterGradients.composeclr(startf);
  stopc = BetterGradients.composeclr(stopf);

  // Print color values.
  println(String.format(fmt, 
    hex(startc), startf[0], startf[1], startf[2], startf[3]));
  println(String.format(fmt, 
    hex(stopc), stopf[0], stopf[1], stopf[2], stopf[3]));
}

public void mousePressed() {
  rndclrs();
}
