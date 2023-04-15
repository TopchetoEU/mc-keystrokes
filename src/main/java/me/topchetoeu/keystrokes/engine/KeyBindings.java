package me.topchetoeu.keystrokes.engine;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyBindings {
    public static KeyBinding get(String name) {
        return KeyBinding.keysById.get(name);
    }
    public static KeyBinding get(int code) {
        return KeyBinding.keyToBindings.get(InputUtil.Type.KEYSYM.createFromCode(code));
    }
    public static KeyBinding get(InputUtil.Type type, int code) {
        return KeyBinding.keyToBindings.get(type.createFromCode(code));
    }
}
