package keystrokesmod.script;

import keystrokesmod.Raven;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.impl.combat.KillAura;
import keystrokesmod.module.setting.Setting;
import keystrokesmod.module.setting.impl.ButtonSetting;
import keystrokesmod.module.setting.impl.SliderSetting;
import keystrokesmod.script.classes.Bridge;
import keystrokesmod.script.classes.Entity;
import keystrokesmod.script.classes.Vec3;
import keystrokesmod.script.classes.World;
import keystrokesmod.script.packets.serverbound.CPacket;
import keystrokesmod.script.packets.serverbound.PacketHandler;
import keystrokesmod.utility.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class ScriptDefaults {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static World world = new World();
    public final Bridge bridge = new Bridge();

    public static class client {
        public static final String colorSymbol = "§";
        public static boolean allowFlying() {
            return mc.thePlayer.capabilities.allowFlying;
        }

        public static void async(Runnable method) {
            Raven.getExecutor().execute(method);
        }

        public static int getSlot() {
            return mc.thePlayer.inventory.currentItem;
        }

        public static long getFPS() {
            return Minecraft.getDebugFPS();
        }

        public static void chat(String message) {
            mc.thePlayer.sendChatMessage(message);
        }

        public static void print(String string) {
            Utils.sendRawMessage(string);
        }

        public static int getFontHeight() {
            return mc.fontRendererObj.FONT_HEIGHT;
        }

        public static void setTimer(float timer) {
            Utils.getTimer().timerSpeed = timer;
        }

        public static int getFontWidth(String text) {
            return mc.fontRendererObj.getStringWidth(text);
        }

        public static boolean isCreative() {
            return mc.thePlayer.capabilities.isCreativeMode;
        }

        public static boolean isFlying() {
            return mc.thePlayer.capabilities.isFlying;
        }

        public static void attack(Entity entity) {
            Utils.attackEntity(entity.entity, true, true);
        }

        public static boolean isSinglePlayer() {
            return mc.isSingleplayer();
        }

        public static void setFlying(boolean flying) {
            mc.thePlayer.capabilities.isFlying = flying;
        }

        public static void setJump(boolean jump) {
            mc.thePlayer.setJumping(jump);
        }

        public static void jump() {
            mc.thePlayer.jump();
        }

        public static void log(String message) {
            System.out.println(message);
        }

        public static boolean isMouseDown(int button) {
            return Mouse.isButtonDown(button);
        }

        public static boolean isKeyDown(int key) {
            return Keyboard.isKeyDown(key);
        }

        public static void setSneak(boolean sneak) {
            mc.thePlayer.setSneaking(sneak);
        }

        public static Entity getPlayer() {
            return new Entity(mc.thePlayer);
        }

        public static Vec3 getMotion() {
            return new Vec3(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
        }

        public static void ping() {
            mc.thePlayer.playSound("note.pling", 1.0f, 1.0f);
        }

        public static boolean isMoving() {
            return Utils.isMoving();
        }

        public static boolean isJump() {
            return mc.thePlayer.movementInput.jump;
        }

        public static float getStrafe() {
            return mc.thePlayer.moveStrafing;
        }

        public static void sleep(int ms) {
            try {
                Thread.sleep(ms);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static float getForward() {
            return mc.thePlayer.moveForward;
        }

        public static void closeScreen() {
            mc.thePlayer.closeScreen();
        }

        public static String getScreen() {
            return mc.currentScreen == null ? "" : mc.currentScreen.getClass().getSimpleName();
        }

        public static float[] getRotationsToEntity(Entity entity) {
            return RotationUtils.getRotations(entity.entity);
        }

        public static void sendPacket(CPacket packet) {
            Packet packet1 = PacketHandler.convertCPacket(packet);
            if (packet1 == null) {
                return;
            }
            mc.thePlayer.sendQueue.addToSendQueue(packet1);
        }

        public static void sendPacketNoEvent(CPacket packet) {
            Packet packet1 = PacketHandler.convertCPacket(packet);
            if (packet1 == null) {
                Utils.sendMessage(packet.name);
                return;
            }
            PacketUtils.sendPacketNoEvent(packet1);
        }

        public static void setMotion(double x, double y, double z) {
            mc.thePlayer.motionX = x;
            mc.thePlayer.motionY = y;
            mc.thePlayer.motionZ = z;
        }

        public static void setSpeed(double speed) {
            Utils.setSpeed(speed);
        }

        public static void setSlot(int slot) {
            mc.thePlayer.inventory.currentItem = slot;
        }

        public static void setForward(float forward) {
            mc.thePlayer.moveForward = forward;
        }

        public static void setStrafe(float strafe) {
            mc.thePlayer.moveStrafing = strafe;
        }

        public static String getServerIP() {
            return mc.getCurrentServerData().serverIP;
        }

        public static int[] getDisplaySize() {
            final ScaledResolution scaledResolution = new ScaledResolution(mc);
            return new int[]{scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight()};
        }

        public static void setSprinting(boolean sprinting) {
            mc.thePlayer.setSprinting(sprinting);
        }

        public static void swing() {
            mc.thePlayer.swingItem();
        }

        public static World getWorld() {
            return world;
        }

        public static long time() {
            return System.currentTimeMillis();
        }

        public static boolean isFriend(Entity entity) {
            return Utils.isFriended(entity.getName());
        }

        public static boolean isEnemy(Entity entity) {
            return Utils.isEnemy(entity.getName());
        }

        public static class render {

            public static void block(Vec3 position, int color, boolean outline, boolean shade) {
                RenderUtils.renderBlock(new BlockPos(position.x, position.y, position.z), color, outline, shade);
            }

            public static void text(String text, float x, float y, int color, boolean shadow) {
                mc.fontRendererObj.drawString(text, x, y, color, shadow);
            }

            public static void player(Entity entity, int color, float partialTicks, boolean outline, boolean shade) {
                net.minecraft.entity.Entity e = entity.entity;
                if (e instanceof EntityLivingBase) {
                    double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
                    double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
                    double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
                    GlStateManager.pushMatrix();
                    float a = (float) (color >> 24 & 255) / 255.0F;
                    float r = (float) (color >> 16 & 255) / 255.0F;
                    float g = (float) (color >> 8 & 255) / 255.0F;
                    float b = (float) (color & 255) / 255.0F;
                    AxisAlignedBB bbox = e.getEntityBoundingBox().expand(0.1D, 0.1D, 0.1D);
                    AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(3042);
                    GL11.glDisable(3553);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glLineWidth(2.0F);
                    GL11.glColor4f(r, g, b, a);
                    if (outline) {
                        RenderGlobal.drawSelectionBoundingBox(axis);
                    }
                    if (shade) {
                        RenderUtils.drawBoundingBox(axis, r, g, b);
                    }
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    GL11.glDisable(3042);
                    GlStateManager.popMatrix();
                }
            }

            public static void rect(int startX, int startY, int endX, int endY, int color) {
                Gui.drawRect(startX, startY, endX, endY, color);
            }
        }

        public static class util {
            public static String strip(String string) {
                return Utils.stripColor(string);
            }

            public static double round(double value, int decimals) {
                return Utils.rnd(value, decimals);
            }

            public static int randomInt(int min, int max) {
                return Utils.randomizeInt(min, max);
            }

            public static double randomDouble(double min, double max) {
                return Utils.randomizeDouble(min, max);
            }
        }

        public static class keybinds {
            public static boolean isPressed(String key) {
                for (Map.Entry<KeyBinding, String> map : Reflection.keyBindings.entrySet()) {
                    if (map.getValue().equals(key)) {
                        return map.getKey().isKeyDown();
                    }
                }
                return false;
            }

            public static void setPressed(String key, boolean pressed) {
                for (Map.Entry<KeyBinding, String> map : Reflection.keyBindings.entrySet()) {
                    if (map.getValue().equals(key)) {
                        KeyBinding.setKeyBindState(map.getKey().getKeyCode(), pressed);
                    }
                }
            }

            public static int getKeycode(String key) {
                return Keyboard.getKeyIndex(key);
            }
            public static boolean isMouseDown(int mouseButton) {
                return Mouse.isButtonDown(mouseButton);
            }

            public static boolean isKeyDown(int key) {
                return Keyboard.isKeyDown(key);
            }
        }
    }

    public static class modules {
        private String superName;

        public modules(String superName) {
            this.superName = superName;
        }
        private Module getModule(String moduleName) {
            boolean found = false;
            for (Module module : Raven.getModuleManager().getModules()) {
                if (module.getName().equals(moduleName)) {
                    return module;
                }
            }
            if (!found) {
                for (Module module : Raven.scriptManager.scripts.values()) {
                    if (module.getName().equals(moduleName)) {
                        return module;
                    }
                }
            }
            return null;
        }

        private Module getScript(String name) {
            for (Module module : Raven.scriptManager.scripts.values()) {
                if (module.getName().equals(name)) {
                    return module;
                }
            }
            return null;
        }

        private Setting getSetting(Module module, String settingName) {
            if (module == null) {
                return null;
            }
            for (Setting setting : module.getSettings()) {
                if (setting.getName().equals(settingName)) {
                    return setting;
                }
            }
            return null;
        }

        public void enable(String moduleName) {
            getModule(moduleName).enable();
        }

        public void disable(String moduleName) {
            getModule(moduleName).disable();
        }

        public boolean isEnabled(String moduleName) {
            return getModule(moduleName).isEnabled();
        }

        public Entity getKillAuraTarget() {
            return new Entity(KillAura.target);
        }

        public Vec3 getBedAuraPosition() {
            BlockPos blockPos = ModuleManager.bedAura.currentBlock;
            return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        public void registerButton(String name, boolean defaultValue) {
            getScript(this.superName).registerSetting(new ButtonSetting(name, defaultValue));
        }

        public void registerSlider(String name, double defaultValue, double minimum, double maximum, double interval) {
            getScript(this.superName).registerSetting(new SliderSetting(name, defaultValue, minimum, maximum, interval));
        }

        public void registerSlider(String name, int defaultValue, String[] stringArray) {
            getScript(this.superName).registerSetting(new SliderSetting(name, stringArray, defaultValue));
        }

        public boolean getButton(String moduleName, String name) {
            ButtonSetting setting = (ButtonSetting) getSetting(getModule(moduleName), name);
            if (setting == null) {
                return false;
            }
            boolean buttonState = setting.isToggled();
            return buttonState;
        }

        public double getSlider(String moduleName, String name) {
            SliderSetting setting = ((SliderSetting) getSetting(getModule(moduleName), name));
            if (setting == null) {
                return 0;
            }
            double sliderValue = setting.getInput();
            return sliderValue;
        }

        public void setButton(String moduleName, String name, boolean value) {
            ButtonSetting setting = (ButtonSetting) getSetting(getModule(moduleName), name);
            if (setting == null) {
                return;
            }
            setting.setEnabled(value);
        }

        public void setSlider(String moduleName, String name, double value) {
            SliderSetting setting = ((SliderSetting) getSetting(getModule(moduleName), name));
            if (setting == null) {
                return;
            }
            setting.setValue(value);
        }
    }
}