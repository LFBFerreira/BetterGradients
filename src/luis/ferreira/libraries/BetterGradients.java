package luis.ferreira.libraries;

import processing.core.PApplet;

public class BetterGradients {

    // -----------------------------------------------------------------------------------------------------------------

    // HSB to RGB

    /**
     *
     * @param in
     * @return
     */
    public static float[] hsbToRgb(float[] in) {
        float[] out = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        return hsbToRgb(in[0], in[1], in[2], in[3], out);
    }

    /**
     *
     * @param in
     * @param out
     * @return
     */
    public static float[] hsbToRgb(float[] in, float[] out) {
        if (in.length == 3) {
            return hsbToRgb(in[0], in[1], in[2], 1.0f, out);
        } else if (in.length == 4) {
            return hsbToRgb(in[0], in[1], in[2], in[3], out);
        }
        return out;
    }

    /**
     *
     * @param hue
     * @param sat
     * @param bri
     * @param alpha
     * @return
     */
    public static float[] hsbToRgb(float hue, float sat, float bri, float alpha) {
        float[] out = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        return hsbToRgb(hue, sat, bri, alpha, out);
    }

    /**
     *
     * @param hue
     * @param sat
     * @param bri
     * @param alpha
     * @param out
     * @return
     */
    public static float[] hsbToRgb(float hue, float sat, float bri, float alpha, float[] out) {
        if (sat == 0.0) {

            // 0.0 saturation is grayscale, so all values are equal.
            out[0] = out[1] = out[2] = bri;
        } else {

            // Divide color wheel into 6 sectors.
            // Scale up hue to 6, convert to sector index.
            float h = hue * 6.0f;
            int sector = (int) h;

            // Depending on the sector, three tints will
            // be distributed among R, G, B channels.
            float tint1 = bri * (1.0f - sat);
            float tint2 = bri * (1.0f - sat * (h - sector));
            float tint3 = bri * (1.0f - sat * (1.0f + sector - h));

            switch (sector) {
                case 1:
                    out[0] = tint2;
                    out[1] = bri;
                    out[2] = tint1;
                    break;
                case 2:
                    out[0] = tint1;
                    out[1] = bri;
                    out[2] = tint3;
                    break;
                case 3:
                    out[0] = tint1;
                    out[1] = tint2;
                    out[2] = bri;
                    break;
                case 4:
                    out[0] = tint3;
                    out[1] = tint1;
                    out[2] = bri;
                    break;
                case 5:
                    out[0] = bri;
                    out[1] = tint1;
                    out[2] = tint2;
                    break;
                default:
                    out[0] = bri;
                    out[1] = tint3;
                    out[2] = tint1;
            }
        }

        out[3] = alpha;
        return out;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // RGB to HSB

    /**
     *
     * @param clr
     * @return
     */
    public static float[] rgbToHsb(int clr) {
        return rgbToHsb(clr, new float[]{0.0f, 0.0f, 0.0f, 1.0f});
    }

    /**
     *
     * @param clr
     * @param out
     * @return
     */
    public static float[] rgbToHsb(int clr, float[] out) {
        return rgbToHsb((clr >> 16 & 0xff) * 0.003921569f,
                (clr >> 8 & 0xff) * 0.003921569f,
                (clr & 0xff) * 0.003921569f,
                (clr >> 24 & 0xff) * 0.003921569f, out);
    }

    /**
     *
     * @param in
     * @return
     */
    public static float[] rgbToHsb(float[] in) {
        return rgbToHsb(in, new float[]{0.0f, 0.0f, 0.0f, 1.0f});
    }

    /**
     *
     * @param in
     * @param out
     * @return
     */
    public static float[] rgbToHsb(float[] in, float[] out) {
        if (in.length == 3) {
            return rgbToHsb(in[0], in[1], in[2], 1.0f, out);
        } else if (in.length == 4) {
            return rgbToHsb(in[0], in[1], in[2], in[3], out);
        }
        return out;
    }

    /**
     *
     * @param red
     * @param green
     * @param blue
     * @param alpha
     * @param out
     * @return
     */
    public static float[] rgbToHsb(float red, float green, float blue, float alpha, float[] out) {

        // Find highest and lowest values.
        float max = Math.max(red, Math.max(green, blue));
        float min = Math.min(red, Math.min(green, blue));

        // Find the difference between max and min.
        float delta = max - min;

        // Calculate hue.
        float hue = 0.0f;
        if (delta != 0.0f) {
            if (red == max) {
                hue = (green - blue) / delta;
            } else if (green == max) {
                hue = 2.0f + (blue - red) / delta;
            } else {
                hue = 4.0f + (red - green) / delta;
            }

            hue /= 6.0;
            if (hue < 0.0) {
                hue += 1.0;
            }
        }

        out[0] = hue;
        out[1] = max == 0.0f ? 0.0f : (max - min) / max;
        out[2] = max;
        out[3] = alpha;
        return out;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Assumes that RGBA are in range 0 .. 1
     * @param in
     * @return
     */
    public static int composeclr(float[] in) {
        return composeclr(in[0], in[1], in[2], in[3]);
    }

    //

    /**
     * Assumes that RGBA are in range 0 .. 1
     * @param red
     * @param green
     * @param blue
     * @param alpha
     * @return
     */
    public static int composeclr(float red, float green, float blue, float alpha) {
        return Math.round(alpha * 255.0f) << 24
                | Math.round(red * 255.0f) << 16
                | Math.round(green * 255.0f) << 8
                | Math.round(blue * 255.0f);
    }

    /**
     * Assumes that RGBA are in range 0 .. 1
     * @param in
     * @return
     */
    public static int[] composeclr(float[][] in) {
        int sz = in.length;
        int [] out = new int[sz];
        for (int i = 0; i < sz; ++i) {
            out[i] = composeclr(in[i][0], in[i][1], in[i][2], in[i][3]);
        }
        return out;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param clr
     * @return
     */
    public static float[] decomposeclr(int clr) {
        return decomposeclr(clr, new float[]{0.0f, 0.0f, 0.0f, 1.0f});
    }

    /**
     * Assumes that out has 4 elements
     * 1.0 / 255.0 = 0.003921569
     * @param clr
     * @param out
     * @return
     */
    public static float[] decomposeclr(int clr, float[] out) {
        out[3] = (clr >> 24 & 0xff) * 0.003921569f;
        out[0] = (clr >> 16 & 0xff) * 0.003921569f;
        out[1] = (clr >> 8 & 0xff) * 0.003921569f;
        out[2] = (clr & 0xff) * 0.003921569f;
        return out;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------

    // RGB

    /**
     *
     * @param a
     * @param b
     * @param st
     * @param out
     * @return
     */
    public static float[] smootherStepRgb(float[] a, float[] b, float st, float[] out) {
        float eval = smootherStep(st);
        out[0] = a[0] + eval * (b[0] - a[0]);
        out[1] = a[1] + eval * (b[1] - a[1]);
        out[2] = a[2] + eval * (b[2] - a[2]);
        out[3] = a[3] + eval * (b[3] - a[3]);
        return out;
    }

    /**
     *
     * @param arr
     * @param st
     * @param out
     * @return
     */
    public static float[] smootherStepRgb(float[][] arr, float st, float[] out) {
        int sz = arr.length;
        if (sz == 1 || st < 0) {
            out = java.util.Arrays.copyOf(arr[0], 0);
            return out;
        } else if (st > 1) {
            out = java.util.Arrays.copyOf(arr[sz - 1], 0);
            return out;
        }
        float scl = st * (sz - 1);
        int i = (int) scl;
        float eval = smootherStep(scl - i);
        out[0] = arr[i][0] + eval * (arr[i + 1][0] - arr[i][0]);
        out[1] = arr[i][1] + eval * (arr[i + 1][1] - arr[i][1]);
        out[2] = arr[i][2] + eval * (arr[i + 1][2] - arr[i][2]);
        out[3] = arr[i][3] + eval * (arr[i + 1][3] - arr[i][3]);
        return out;
    }


    // -----------------------------------------------------------------------------------------------------------------

    // HSB

    /**
     *
     * @param a
     * @param b
     * @param st
     * @param out
     * @return
     */
    public static float[] smootherStepHsb(float[] a, float[] b, float st, float[] out) {

        // Find difference in hues.
        float huea = a[0];
        float hueb = b[0];
        float delta = hueb - huea;

        // Prefer shortest distance.
        if (delta < -0.5) {
            hueb += 1.0;
        } else if (delta > 0.5) {
            huea += 1.0;
        }

        float eval = smootherStep(st);

        // The two hues may be outside of 0 .. 1 range,
        // so modulate by 1.
        out[0] = (huea + eval * (hueb - huea)) % 1;
        out[1] = a[1] + eval * (b[1] - a[1]);
        out[2] = a[2] + eval * (b[2] - a[2]);
        out[3] = a[3] + eval * (b[3] - a[3]);
        return out;
    }

    /**
     *
     * @param arr
     * @param st
     * @param out
     * @return
     */
    public static float[] smootherStepHsb(float[][] arr, float st, float[] out) {

        int sz = arr.length;

        if (sz == 1 || st < 0) {
            out = java.util.Arrays.copyOf(arr[0], 0);
            return out;
        } else if (st > 1) {
            out = java.util.Arrays.copyOf(arr[sz - 1], 0);
            return out;
        }

        float scl = st * (sz - 1);
        int i = (int) scl;
        float huea = arr[i][0];
        float hueb = arr[i + 1][0];
        float delta = hueb - huea;

        if (delta < -0.5) {
            hueb += 1.0 / (sz - 1.0);
        } else if (delta > 0.5) {
            huea += 1.0 / (sz - 1.0);
        }

        float eval = smootherStep(scl - i);
        out[0] = (huea + eval * (hueb - huea)) % 1;
        out[1] = arr[i][1] + eval * (arr[i + 1][1] - arr[i][1]);
        out[2] = arr[i][2] + eval * (arr[i + 1][2] - arr[i][2]);
        out[3] = arr[i][3] + eval * (arr[i + 1][3] - arr[i][3]);

        return out;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param st
     * @return
     */
    public static float smootherStep(float st) {
        return st * st * st * (st * (st * 6.0f - 15.0f) + 10.0f);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param arr
     * @param step
     * @param colorMode
     * @return
     */
    public static int lerpColorWrapper(int[] arr, float step, int colorMode) {
        int sz = arr.length;
        if (sz == 1 || step <= 0.0) {
            return arr[0];
        } else if (step >= 1.0) {
            return arr[sz - 1];
        }

        float scl = step * (sz - 1);
        int i = (int) scl;
        return PApplet.lerpColor(arr[i], arr[i + 1], scl - i, colorMode);
    }

    /**
     *
     * @param grad
     * @param step
     * @param colorMode
     * @param parent
     * @return
     */
    public static int lerpColorWrapper(Gradient grad, float step, int colorMode) {
        return lerpColorWrapper(grad.colorStops.stream().mapToInt((ColorStop st)->st.clr).toArray(), step, colorMode);
    }
}
