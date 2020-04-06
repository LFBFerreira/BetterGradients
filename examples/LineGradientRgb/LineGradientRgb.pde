import luis.ferreira.libraries.color.*;

void setup() {
  // Set the sketch size to 512 pixels wide by 256 pixels high.
  size(512, 256);

  // Choose between RGB and HSB.
  int colorMode = RGB;

  // These are the two colors we want to use, in RGBA order.
  int red = color(255, 0, 0, 255);
  int blue = color(0, 0, 255, 255);

  // Set the background color to white.
  background(255);

  // These define the four edges of the square.
  int margin = 64;
  int left = margin;
  int top = margin;
  int right = width - margin;
  int bottom = height - margin;

  // These will be used in the for-loop.
  int currentStroke = red;
  float step = 0;

  // Start at the top of the rectangle.
  // Add one until the bottom is reached.
  for (int i = top; i <= bottom; ++i) {

    // Convert the loop's i counter to a range between
    // 0 and 1 (0 .. 100%).
    step = map(i, top, bottom, 0.0f, 1.0f);

    // Linear interpolation.
    currentStroke = lerpColor(red, blue, step, colorMode);
    stroke(currentStroke);

    // Syntax is line(startx, starty, endx, endy).
    line(left, i, right, i);
  }
}
