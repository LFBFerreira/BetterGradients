import luis.ferreira.libraries.color.*;

int[] palette = {0xffff7f00, 0xff007fff, 0xff7f00ff};
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

  for (int i = 0, y = 0, x; y < h; ++y) {
    rise = centerY - y;
    rise *= rise;

    for (x = 0; x < w; ++x, ++i) {
      run = centerX - x;
      run *= run;

      distSq = run + rise;
      dist = 4.0f * distSq / hypotSq;

      renderer.pixels[i] = BetterGradients.lerpColorSmoother(palette, dist, RGB);
    }
  }

  renderer.updatePixels();
  renderer.endDraw();
  image(renderer, 0, 0);
}
