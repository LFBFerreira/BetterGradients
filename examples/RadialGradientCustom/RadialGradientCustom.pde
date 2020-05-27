import luis.ferreira.libraries.color.*;

int clrCount = 4;
Gradient grd;

void setup() {
  size(512, 256);
  int colorMode = random(1) < 0.5 ? HSB : RGB;

  colorMode(colorMode, 1, 1, 1, 1);

  ColorStop[] temp = new ColorStop[clrCount];
  float prc;
  for (int i = 0; i < clrCount; ++i) {
    prc = i == 0 ? 0 : i == clrCount - 1 ? 1 : random(1);
    temp[i] = new ColorStop(prc, 
      BetterGradients.composeclr(random(0, 1), random(0, 1), random(0, 1), 1));
  }

  grd = new Gradient(temp);

  println("Color Mode: " + (colorMode == RGB ? "RGB" : "HSB"));
  println("Colors: ");
  for (ColorStop clr : grd.getColorStops()) {
    println(clr);
  }

  int current;
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

    //current = lerpColor(start, stop, step, colorMode);

    current = grd.eval(step, colorMode);

    // To offset the radial gradient, move the center of the
    // circle each step through the for-loop.
    centerX = lerp(centerXStart, centerXStop, step);
    centerY = lerp(centerYStart, centerYStop, step);

    fill(current);
    ellipse(centerX, centerY, i, i);
  }
}
