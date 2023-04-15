package me.topchetoeu.keystrokes.engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;

public class Button {
    public static enum Align {
        TopLeft,
        BottomLeft,
        TopRight,
        BottomRight,
    }

    public ButtonStyle style;

    public int x = 0, y = 0, width = 20, height = 20;
    public Align alignment = Align.TopLeft;

    public KeyBinding key;
    public String text;

    public int getRealX(int screenWidth) {
        switch (alignment) {
            case TopRight:
            case BottomRight:
                return screenWidth - width - x;
            default:
                return x;
        }
    }
    public int getRealY(int screenHeight) {
        switch (alignment) {
            case BottomLeft:
            case BottomRight:
                return screenHeight - height - y;
            default:
                return y;
        }
    }

    public void write(List<ButtonStyle> styles, OutputStream w) throws IOException {
        var str = new DataOutputStream(w);
        str.writeInt(x);
        str.writeInt(y);
        str.writeInt(width);
        str.writeInt(height);
        str.writeInt(alignment.ordinal());
        str.writeInt(styles.indexOf(style));
        str.writeUTF(text);
        str.writeUTF(key.getTranslationKey());
    }
    public void read(List<ButtonStyle> styles, InputStream r) throws IOException {
        var str = new DataInputStream(r);
        x = str.readInt();
        y = str.readInt();
        width = str.readInt();
        height = str.readInt();
        alignment = Align.values()[str.readInt()];
        style = styles.get(str.readInt());
        text = str.readUTF();
        key = KeyBindings.get(str.readUTF());
    }

    private float lastTime = System.nanoTime() / 10000000000f;
    private float time;
    private boolean activated = false;

    public boolean isActivated() { return activated; }

    public void update() {
        float curr = System.nanoTime() / 1000000000f;
        float delta = curr - lastTime;
        lastTime = curr;
        activated = key.isPressed();

        if (activated) time = 0;
        else time += delta;
        if (time > style.fadeoutDuration) time = style.fadeoutDuration;
    }

    @SuppressWarnings("all")
    public void draw(MatrixStack stack, int screenWidth, int screenHeight) {
        var textRenderer = MinecraftClient.getInstance().textRenderer;
        update();

        int x = getRealX(screenWidth);
        int y = getRealY(screenHeight);

        stack.push();
        stack.translate(x, y, 0);

        DrawableHelper.fill(stack, 0, 0, width, height, style.getColor(time));
        int textWidth = textRenderer.getWidth(text);
        int textHeight = (int)(textRenderer.fontHeight / 1.5f);

        textRenderer.draw(
            stack, text,
            (width - textWidth) / 2,
            (height - textHeight) / 2,
            style.textColor
        );

        stack.pop();
    }


    public Button(ButtonStyle style, KeyBinding key, String text, int x, int y, int w, int h, Align alignment) {
        this.style = style;
        this.key = key;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.alignment = alignment;
    }
    public Button(ButtonStyle style, KeyBinding key, String text, int x, int y, int w, int h) {
        this.style = style;
        this.key = key;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    public Button(ButtonStyle style, KeyBinding key, String text) {
        this.style = style;
        this.key = key;
        this.text = text;
    }
    public Button(List<ButtonStyle> styles, InputStream r) throws IOException {
        read(styles, r);
    }
}
