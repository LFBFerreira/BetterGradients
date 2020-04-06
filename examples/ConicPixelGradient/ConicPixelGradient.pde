import luis.ferreira.libraries.color.*;

final float INV_TWO_PI = 1.0f / TWO_PI;
int[] palette = { 0xffff7f00, 0xff007fff, 0xff00ff7f, 0xff7f00ff };
PGraphics renderer;

void setup() {
  size(512, 256);
  renderer = createGraphics(width, height);
  renderer.beginDraw();
  renderer.loadPixels();

  float w = renderer.width;
  float h = renderer.height;

  float centerX = w * 0.5f;
  float centerY = h * 0.5f;
  float hypotSq = w * w + h * h;
  float rise, run, distSq, dist;

  float angle = random(-PI, PI);
  for (int i = 0, y = 0, x; y < h; ++y) {
    rise = centerY - y;
    for (x = 0; x < w; ++x, ++i) {
      run = centerX - x;
      float t = angle + atan2(rise, run);

      // Ensure a positive value if angle is negative.
      t =  floorMod(t, TWO_PI);

      // Divide by TWO_PI to get value in range 0 .. 1.
      t *= INV_TWO_PI;

      renderer.pixels[i] = BetterGradients.lerpColorSmoother(palette, t, RGB);
    }
  }

  renderer.updatePixels();
  renderer.endDraw();
  image(renderer, 0, 0);
}

float floorMod(float a, float b) {
  return a - b * floor(a / b);
}
