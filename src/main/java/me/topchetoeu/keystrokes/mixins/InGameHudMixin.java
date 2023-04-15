package me.topchetoeu.keystrokes.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.topchetoeu.keystrokes.Keystrokes;
import me.topchetoeu.keystrokes.engine.Button;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = { "render" }, at = { @At("TAIL") })
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo info) {
        for (Button button : Keystrokes.getInstance().getButtons().get()) {
            button.draw(matrices, ((InGameHud)(Object)this).scaledWidth, ((InGameHud)(Object)this).scaledHeight);
        }
    }
}
