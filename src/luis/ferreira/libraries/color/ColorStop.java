package luis.ferreira.libraries.color;


import static luis.ferreira.libraries.color.BetterGradients.*;
import static processing.core.PConstants.HSB;

public class ColorStop implements Comparable<ColorStop> {
    public static final float TOLERANCE = 0.09f;

    public float percent;
    public int color;

    /**
     *
     * @param colorMode
     * @param percent
     * @param arr
     */
    public ColorStop(int colorMode, float percent, float[] arr) {
        this(colorMode, percent, arr[0], arr[1], arr[2],
                arr.length == 4 ? arr[3] : 1.0f);
    }

    /**
     *
     * @param colorMode
     * @param percent
     * @param r
     * @param g
     * @param b
     * @param a
     */
    public ColorStop(int colorMode, float percent, float r, float g, float b, float a) {
        this(percent, colorMode == HSB ? composeclr(hsbToRgb(r, g, b, a))
                : composeclr(r, g, b, a));
    }

    /**
     *
     * @param percent
     * @param color
     */
    public ColorStop(float percent, int color) {
        this.percent = constrain(percent, 0.0f, 1.0f);
        this.color = color;
    }

    /**
     *
     * @param cs
     * @param tolerance
     * @return
     */
    public boolean approxPercent(ColorStop cs, float tolerance) {
        return Math.abs(percent - cs.percent) < tolerance;
    }

    // Mandated by the interface Comparable<ColorStop>.
    // Permits color stops to be sorted by Collections.sort.
    @Override
    public int compareTo(ColorStop cs) {
        return Float.compare(percent, cs.percent);
    }

    /**
     *
     * @param val
     * @param min
     * @param max
     * @return
     */
    public float constrain(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     *
     * @return
     */
    public String toString() {
        return String.format("0x%08X @ %.2f", color, percent);
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @param a
     * @param colorMode
     */
    public void setColor(float r, float g, float b, float a, int colorMode)
    {
        this.color = colorMode == HSB ? composeclr(hsbToRgb(r, g, b, a))
                : composeclr(r, g, b, a);
    }

    public void setColor(float r, float g, float b, float a)
    {
        setColor(r, g, b, a, DEFAULT_COLOR_MODE);
    }
}
