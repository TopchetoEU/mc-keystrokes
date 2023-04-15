package me.topchetoeu.keystrokes.engine;

public class Color {
    private Color() { }

    public static float getR(int col) { return (col & 0xFF) / 255f; }
    public static float getG(int col) { return ((col >> 8) & 0xFF) / 255f; }
    public static float getB(int col) { return ((col >> 16) & 0xFF) / 255f; }
    public static float getA(int col) { return ((col >> 24) & 0xFF) / 255f; }

    public static float setR(int col, float r) { return (col & 0xFFFFFF00) | (int)(r * 255); }
    public static float setG(int col, float g) { return (col & 0xFFFF00FF) | ((int)(g * 255) << 8); }
    public static float setB(int col, float b) { return (col & 0xFF00FFFF) | ((int)(b * 255) << 16); }
    public static float setA(int col, float a) { return (col & 0x00FFFFFF) | ((int)(a * 255) << 24); }

    public static int make(float r, float g, float b, float a) {
        return
            (int)(r * 255) |
            ((int)(g * 255) << 8) |
            ((int)(b * 255) << 16) |
            ((int)(a * 255) << 24);
    }
    public static int make(float r, float g, float b) {
        return make(r, g, b, 1);
    }

    private static float mix(float a, float b, float ratio) {
        return a * (1 - ratio) + b * ratio;
    }

    public static int mix(int col1, int col2, float ratio) {
        return Color.make(
            mix(getR(col1), getR(col2), ratio),
            mix(getG(col1), getG(col2), ratio),
            mix(getB(col1), getB(col2), ratio),
            mix(getA(col1), getA(col2), ratio)
        );
    }
}
