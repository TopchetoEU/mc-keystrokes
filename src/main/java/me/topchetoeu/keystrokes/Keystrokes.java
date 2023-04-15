package me.topchetoeu.keystrokes;

import java.io.File;

import org.lwjgl.glfw.GLFW;

import me.topchetoeu.keystrokes.engine.Button;
import me.topchetoeu.keystrokes.engine.ButtonStyle;
import me.topchetoeu.keystrokes.engine.Buttons;
import me.topchetoeu.keystrokes.engine.Color;
import me.topchetoeu.keystrokes.engine.KeyBindings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Keystrokes implements ClientModInitializer {
    private static Keystrokes instance;
    public static Keystrokes getInstance() { return instance; }

    private Buttons buttons;

    public Buttons getButtons() { return buttons; }

    @Override
    public void onInitializeClient() {
        instance = this;
        buttons = new Buttons(new File("config/keystrokes/conf.bin"));

        ButtonStyle style = new ButtonStyle();
        style.passiveColor = Color.make(0f, 0f, 0f, 0.5f);
        style.activeColor = Color.make(1f, 1f, 1f, 0.5f);
        style.textColor = Color.make(1f, 1f, 1f);
        style.fadeoutDuration = .2f;

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {

            KeyBinding w = KeyBindings.get(GLFW.GLFW_KEY_W);
            KeyBinding a = KeyBindings.get(GLFW.GLFW_KEY_A);
            KeyBinding s = KeyBindings.get(GLFW.GLFW_KEY_S);
            KeyBinding d = KeyBindings.get(GLFW.GLFW_KEY_D);
            KeyBinding space = KeyBindings.get(GLFW.GLFW_KEY_SPACE);
            KeyBinding shift = KeyBindings.get(GLFW.GLFW_KEY_LEFT_SHIFT);
            KeyBinding lbm = KeyBindings.get(InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_1);
            KeyBinding rbm = KeyBindings.get(InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_2);

            buttons.register(new Button(style, w, "W", 27, 5, 20, 20));
            buttons.register(new Button(style, a, "A", 5, 27, 20, 20));
            buttons.register(new Button(style, s, "S", 27, 27, 20, 20));
            buttons.register(new Button(style, d, "D", 49, 27, 20, 20));
            buttons.register(new Button(style, space, "Space", 5, 52, 42, 20));
            buttons.register(new Button(style, shift, "SFT", 49, 52, 20, 20));
            buttons.register(new Button(style, lbm, "LMB", 5, 77, 31, 20));
            buttons.register(new Button(style, rbm, "RMB", 38, 77, 31, 20));
        });
    }
}
