package me.topchetoeu.keystrokes.engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ButtonStyle {
    public int passiveColor = Color.make(0f, 0f, 0f, 0.5f);
    public int activeColor = Color.make(1f, 1f, 1f, 0.5f);
    public int textColor = Color.make(1f, 1f, 1f);
    public float fadeoutDuration = .2f;

    public float getRatio(float time) { return (fadeoutDuration - time) / fadeoutDuration; }
    public int getColor(float time) { return Color.mix(passiveColor, activeColor, getRatio(time)); }

    public void write(OutputStream w) throws IOException {
        var str = new DataOutputStream(w);
        str.writeInt(passiveColor);
        str.writeInt(activeColor);
        str.writeInt(textColor);
        str.writeFloat(fadeoutDuration);
    }
    public void read(InputStream r) throws IOException {
        var str = new DataInputStream(r);
        passiveColor = str.readInt();
        activeColor = str.readInt();
        textColor = str.readInt();
        fadeoutDuration = str.readFloat();
    }

    public ButtonStyle() {

    }
    public ButtonStyle(InputStream r) throws IOException {
        read(r);
    }
}
