void setup() {
  size(512, 512);
  background(0xffffffff);
  noStroke();
  colorMode(HSB, 359, 99, 99);
  ellipseMode(RADIUS);
  textAlign(CENTER, CENTER);

  //------------------------------

  int count = 16;

  float centerX = width * 0.5f;
  float centerY = height * 0.5f;
  float radius = 42.0f;
  float dist = min(height, width) * 0.5f - radius;
  float radToDeg = 180.0f / PI;
  float countToRad = TWO_PI / (float) count;
  float angle, hue, x, y;

  String label = "";

  for (int i = 0; i < count; ++i) {
    angle = i * countToRad;
    hue = angle * radToDeg;
    x = centerX + cos(angle) * dist;
    y = centerY + sin(angle) * dist;
    label = ceil(hue) + "\u00b0";

    fill(color(hue, 99, 99));
    ellipse(x, y, radius, radius);

    fill(0xff000000);
    text(label, x, y);
    fill(0xffffffff);
    text(label, x + 1, y - 1);
  }
}
