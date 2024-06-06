package com.cecer1.projects.mc.nochangethegame.mixin.worldloadscreenbackgrounds;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.cecer1.projects.mc.nochangethegame.config.NCTGConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.screens.ReceivingLevelScreen.Reason.*;

@Mixin(ReceivingLevelScreen.class)
public abstract class DisablePortalScreenMixin extends Screen {

    @Shadow @Final
    private ReceivingLevelScreen.Reason reason;

    protected DisablePortalScreenMixin(Component component) { super(component); }

    @Inject(method = "renderBackground", at = @At(value = "HEAD"), cancellable = true)
    private void onRenderBackground(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        NCTGConfig.WorldLoadingBackgrounds config = NoChangeTheGameMod.INSTANCE.getConfig().getWorldLoadingBackgrounds();

        if ((this.reason == END_PORTAL && config.getDisableEnd()) ||
            (this.reason == NETHER_PORTAL && config.getDisableNether()) ||
            (this.reason == OTHER && config.getDisableOther())) {

            super.renderBackground(guiGraphics, i, j, f);
            ci.cancel();
        }
    }
}