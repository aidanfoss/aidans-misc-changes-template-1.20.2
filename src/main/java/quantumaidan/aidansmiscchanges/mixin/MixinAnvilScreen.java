package quantumaidan.aidansmiscchanges.mixin;

//import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
//import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreen.class)
public abstract class MixinAnvilScreen
        extends ForgingScreen<AnvilScreenHandler> {

    @Shadow @Final
    private PlayerEntity player;

    public MixinAnvilScreen(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title, texture);
    }

    @Inject(method = "drawForeground", at = @At("HEAD"), cancellable = true)
    protected void $anviltooexpensiveOnAnvilScreen(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        super.drawForeground(context, mouseX, mouseY);
        int i = ((AnvilScreenHandler) this.handler).getLevelCost();
        Object text;
        text = Text.translatable("container.repair.cost", new Object[]{i});

        int color = 8453920;
        if (player.experienceLevel < i && !player.getAbilities().creativeMode) {
            color = 16736352;
        }

        if (text != null) {
            int k = this.backgroundWidth - 8 - this.textRenderer.getWidth((StringVisitable) text) - 2;
            context.fill(k - 2, 67, this.backgroundWidth - 8, 79, 1325400064);
            context.drawTextWithShadow(this.textRenderer, (Text) text, k, 69, color);
        }
        ci.cancel();
    }
}
