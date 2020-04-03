package luis.ferreira.libraries;


import static luis.ferreira.libraries.BetterGradients.composeclr;
import static luis.ferreira.libraries.BetterGradients.hsbToRgb;
import static processing.core.PConstants.HSB;

public class ColorStop implements Comparable<ColorStop> {
    static final float TOLERANCE = 0.09f;
    float percent;
    int clr;

    public ColorStop(int colorMode, float percent, float[] arr) {
        this(colorMode, percent, arr[0], arr[1], arr[2],
                arr.length == 4 ? arr[3] : 1.0f);
    }

    public ColorStop(int colorMode, float percent, float x, float y, float z, float w) {
        this(percent, colorMode == HSB ? composeclr(hsbToRgb(x, y, z, w))
                : composeclr(x, y, z, w));
    }

    public ColorStop(float percent, int clr) {
        this.percent = constrain(percent, 0.0f, 1.0f);
        this.clr = clr;
    }

    public boolean approxPercent(ColorStop cs, float tolerance) {
        return Math.abs(percent - cs.percent) < tolerance;
    }

    // Mandated by the interface Comparable<ColorStop>.
    // Permits color stops to be sorted by Collections.sort.
    public int compareTo(ColorStop cs) {
        //percent > cs.percent ? 1 : percent < cs.percent ? -1 : 0
        return Float.compare(percent, cs.percent);
    }

    public float constrain(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public String toString()
    {
        return String.format("0x%08X @ %.2f", clr, percent );
    }
}
