package luis.ferreira.libraries.color;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static luis.ferreira.libraries.color.BetterGradients.*;
import static processing.core.PConstants.RGB;
import static processing.core.PConstants.HSB;


public class Gradient {
    static int DEFAULT_COLOR_MODE = RGB;

    private List<ColorStop> colorStops = new LinkedList<>();

    // -----------------------------------------------------------------------------------------------------------------

    // Constructors

    /**
     *
     */
    public Gradient() {
        this(new int[]{0xff000000, 0xffffffff});
    }

    /**
     *
     * @param colorMode
     */
    public Gradient(int colorMode) {
        this();
        DEFAULT_COLOR_MODE = colorMode;
    }

    /**
     * @param colors
     */
    public Gradient(int colorMode, int... colors) {
        // Creates equidistant color stops.
        int sz = colors.length;
        float szf = sz <= 1.0f ? 1.0f : sz - 1.0f;
        for (int i = 0; i < sz; ++i) {
            colorStops.add(new ColorStop(i / szf, colors[i]));
        }
    }

    /**
     *
     * @param colors
     */
    public Gradient(int... colors) {
        this(DEFAULT_COLOR_MODE, colors);
    }

    /**
     * @param colorMode
     * @param colors
     */
    public Gradient(int colorMode, float[]... colors) {
        // Creates equidistant color stops.
        int sz = colors.length;
        float szf = sz <= 1.0f ? 1.0f : sz - 1.0f;
        for (int i = 0; i < sz; ++i) {
            colorStops.add(new ColorStop(colorMode, i / szf, colors[i]));
        }
    }

    /**
     * @param colorStops
     */
    public Gradient(ColorStop... colorStops) {
        int sz = colorStops.length;
        for (int i = 0; i < sz; ++i) {
            this.colorStops.add(colorStops[i]);
        }
        Collections.sort(this.colorStops);
        consolidate();
    }

    /**
     *
     * @param colorMode
     * @param colorStops
     */
    public Gradient(int colorMode, List<ColorStop> colorStops) {
        DEFAULT_COLOR_MODE = colorMode;
        this.colorStops = colorStops;
        Collections.sort(this.colorStops);
        consolidate();
    }

    /**
     * @param colorStops
     */
    public Gradient(List<ColorStop> colorStops) {
        this(DEFAULT_COLOR_MODE, colorStops);
    }

    /**
     *
     * @return
     */
    public int getNumberColorStops()
    {
        return colorStops.size();
    }

    // -----------------------------------------------------------------------------------------------------------------

    // ColorStops control

    /**
     * @param colorMode
     * @param percent
     * @param arr
     */
    public void add(int colorMode, float percent, float[] arr) {
        add(new ColorStop(colorMode, percent, arr));
    }

    /**
     * @param colorMode
     * @param percent
     * @param x
     * @param y
     * @param z
     * @param w
     */
    public void add(int colorMode, float percent, float x, float y, float z, float w) {
        add(new ColorStop(colorMode, percent, x, y, z, w));
    }

    /**
     * @param percent
     * @param clr
     */
    public void add(final float percent, final int clr) {
        add(new ColorStop(percent, clr));
    }

    /**
     * @param newColorStops
     */
    public void add(List<ColorStop> newColorStops) {
        colorStops.addAll(newColorStops);
    }

    /**
     * @param colorStop
     */
    public void add(final ColorStop colorStop) {
        // remove colorstops that are too close
        for (int sz = colorStops.size(), i = sz - 1; i > 0; --i) {
            ColorStop current = colorStops.get(i);
            if (current.approxPercent(colorStop, ColorStop.TOLERANCE)) {
                System.out.println(current + " will be replaced by " + colorStop);
                colorStops.remove(current);
            }
        }

        colorStops.add(colorStop);

        Collections.sort(colorStops);
    }

    // ----------------------------------------------------------

    // Getters and Setters

    /**
     * @return
     */
    public List<ColorStop> getColorStops() {
        return colorStops;
    }

    public int getColor(int index) {
        if (colorStops.isEmpty()) {
            return 0;
        }

        if (index >= colorStops.size()) {
            return colorStops.get(colorStops.size() - 1).color;
        } else if (index <= 0) {
            return colorStops.get(0).color;
        }

        return colorStops.get(index).color;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @param colorStop
     * @return
     */
    public boolean remove(ColorStop colorStop) {
        return colorStops.remove(colorStop);
    }

    /**
     * @param i
     * @return
     */
    public ColorStop remove(int i) {
        return colorStops.remove(i);
    }

    public void removeAll() {
        colorStops.clear();
    }

    public void reset() {
        removeAll();
        add(0, 0xFF000000);
        add(1, 0xffffffff);
    }

    // -----------------------------------------------------------------------------------------------------------------

    // Evaluate

    /**
     * Calculates the color at the requested step
     *
     * @param step [0-1]
     * @return
     */
    public int eval(final float step) {
        return eval(step, DEFAULT_COLOR_MODE);
    }

    /**
     * Calculates the color at the requested step
     *
     * @param step      [0-1]
     * @param colorMode RGB or HSV
     * @return
     */
    public int eval(final float step, final int colorMode) {
        int sz = colorStops.size();

        // Exit from the function early whenever possible.
        if (sz == 0) {
            return 0x00000000;
        } else if (sz == 1 || step < 0.0) {
            return colorStops.get(0).color;
        } else if (step >= 1.0) {
            return colorStops.get(sz - 1).color;
        }

        ColorStop currStop;
        ColorStop prevStop;
        float currPercent, scaledst;
        for (int i = 0; i < sz; ++i) {
            currStop = colorStops.get(i);
            currPercent = currStop.percent;

            if (step < currPercent) {

                // These can be declared within the for-loop because
                // if step < currPercent, the function will return
                // and no more iterations will be executed.
                float[] originclr = new float[4];
                float[] destclr = new float[4];
                float[] rsltclr = new float[4];

                // If not at the first stop in the gradient (i == 0),
                // then get the previous.
                prevStop = colorStops.get(i - 1 < 0 ? 0 : i - 1);

                scaledst = step - currPercent;
                float denom = prevStop.percent - currPercent;
                if (denom != 0) {
                    scaledst /= denom;
                }

                // Assumes that color stops' colors are ints. They could
                // also be float[] arrays, in which case they wouldn't
                // need to be decomposed.
                switch (colorMode) {
                    case HSB:
                        rgbToHsb(currStop.color, originclr);
                        rgbToHsb(prevStop.color, destclr);
                        smootherStepHsb(originclr, destclr, scaledst, rsltclr);
                        return composeclr(hsbToRgb(rsltclr));
                    case RGB:
                        decomposeclr(currStop.color, originclr);
                        decomposeclr(prevStop.color, destclr);
                        smootherStepRgb(originclr, destclr, scaledst, rsltclr);
                        return composeclr(rsltclr);
                }
            }
        }
        return colorStops.get(sz - 1).color;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // Helpers

    /**
     * @return
     */
    private int consolidate() {
        int removed = 0;
        for (int sz = colorStops.size(), i = sz - 1; i > 0; --i) {
            ColorStop current = colorStops.get(i);
            ColorStop prev = colorStops.get(i - 1);
            if (current.approxPercent(prev, ColorStop.TOLERANCE)) {
                System.out.println(current + " removed, as it was too close to " + prev);
                colorStops.remove(current);
                removed++;
            }
        }
        return removed;
    }
}