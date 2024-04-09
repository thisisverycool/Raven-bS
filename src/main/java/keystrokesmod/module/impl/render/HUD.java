package keystrokesmod.module.impl.render;

import java.awt.Color;
import java.io.IOException;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.setting.impl.ButtonSetting;
import keystrokesmod.module.setting.impl.SliderSetting;
import keystrokesmod.utility.RenderUtils;
import keystrokesmod.utility.Theme;
import keystrokesmod.utility.Utils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class HUD extends Module {
   private SliderSetting theme;
   private ButtonSetting editPosition;
   public static ButtonSetting dropShadow;
   public static ButtonSetting alphabeticalSort;
   public static ButtonSetting alignRight;
   public static ButtonSetting lowercase;
   public static ButtonSetting showInfo;
   public static int hudX = 5;
   public static int hudY = 70;

   public HUD() {
      super("HUD", Module.category.render);
      this.registerSetting(theme = new SliderSetting("Theme", Theme.themes, 0));
      this.registerSetting(editPosition = new ButtonSetting("Edit position", () -> {
         mc.displayGuiScreen(new EditScreen());
      }));
      this.registerSetting(alignRight = new ButtonSetting("Align right", false));
      this.registerSetting(alphabeticalSort = new ButtonSetting("Alphabetical sort", false));
      this.registerSetting(dropShadow = new ButtonSetting("Drop shadow", true));
      this.registerSetting(lowercase = new ButtonSetting("Lowercase", false));
      this.registerSetting(showInfo = new ButtonSetting("Show module info", true));
   }

   public void onEnable() {
      ModuleManager.sort();
   }

   public void guiButtonToggled(ButtonSetting b) {
      if (b == alphabeticalSort) {
         ModuleManager.sort();
      }
   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent ev) {
      if (ev.phase != TickEvent.Phase.END || !Utils.nullCheck()) {
         return;
      }
      if (mc.currentScreen != null || mc.gameSettings.showDebugInfo) {
         return;
      }
      int n = hudY;
      double n2 = 0.0;
      for (Module module : ModuleManager.organizedModules) {
         if (module.isEnabled() && module != this) {
            if (!module.isVisible()) {
               continue;
            }
            if (module == ModuleManager.commandLine) {
               continue;
            }
            String moduleName = module.getName();
            if (lowercase.isToggled()) {
               moduleName = moduleName.toLowerCase();
            }
            if (showInfo.isToggled()) {
               moduleName += module.getInfo();
            }
            int e = Theme.getGradient((int) theme.getInput(), n2);
            n2 -= 12;
            int n3 = hudX;
            if (alignRight.isToggled()) {
               n3 -= mc.fontRendererObj.getStringWidth(moduleName);
            }
            mc.fontRendererObj.drawString(moduleName, n3, (float)n, e, dropShadow.isToggled());
            n += mc.fontRendererObj.FONT_HEIGHT + 2;
         }
      }
   }

   static class EditScreen extends GuiScreen {
      final String example = new String("This is an-Example-HUD");
      GuiButtonExt resetPosition;
      boolean d = false;
      int miX = 0;
      int miY = 0;
      int maX = 0;
      int maY = 0;
      int aX = 5;
      int aY = 70;
      int laX = 0;
      int laY = 0;
      int lmX = 0;
      int lmY = 0;

      public void initGui() {
         super.initGui();
         this.buttonList.add(this.resetPosition = new GuiButtonExt(1, this.width - 90, 5, 85, 20, new String("Reset position")));
         this.aX = HUD.hudX;
         this.aY = HUD.hudY;
      }

      public void drawScreen(int mX, int mY, float pt) {
         drawRect(0, 0, this.width, this.height, -1308622848);
         int miX = this.aX;
         int miY = this.aY;
         int maX = miX + 50;
         int maY = miY + 32;
         this.d(this.mc.fontRendererObj, this.example);
         this.miX = miX;
         this.miY = miY;
         this.maX = maX;
         this.maY = maY;
         HUD.hudX = miX;
         HUD.hudY = miY;
         ScaledResolution res = new ScaledResolution(this.mc);
         int x = res.getScaledWidth() / 2 - 84;
         int y = res.getScaledHeight() / 2 - 20;
         RenderUtils.dct("Edit the HUD position by dragging.", '-', x, y, 2L, 0L, true, this.mc.fontRendererObj);

         try {
            this.handleInput();
         } catch (IOException var12) {
         }

         super.drawScreen(mX, mY, pt);
      }

      private void d(FontRenderer fr, String t) {
         int x = this.miX;
         int y = this.miY;
         String[] var5 = t.split("-");

         for (String s : var5) {
            fr.drawString(s, (float) x, (float) y, Color.white.getRGB(), HUD.dropShadow.isToggled());
            y += fr.FONT_HEIGHT + 2;
         }

      }

      protected void mouseClickMove(int mX, int mY, int b, long t) {
         super.mouseClickMove(mX, mY, b, t);
         if (b == 0) {
            if (this.d) {
               this.aX = this.laX + (mX - this.lmX);
               this.aY = this.laY + (mY - this.lmY);
            } else if (mX > this.miX && mX < this.maX && mY > this.miY && mY < this.maY) {
               this.d = true;
               this.lmX = mX;
               this.lmY = mY;
               this.laX = this.aX;
               this.laY = this.aY;
            }

         }
      }

      protected void mouseReleased(int mX, int mY, int s) {
         super.mouseReleased(mX, mY, s);
         if (s == 0) {
            this.d = false;
         }

      }

      public void actionPerformed(GuiButton b) {
         if (b == this.resetPosition) {
            this.aX = HUD.hudX = 5;
            this.aY = HUD.hudY = 70;
         }

      }

      public boolean doesGuiPauseGame() {
         return false;
      }
   }
}