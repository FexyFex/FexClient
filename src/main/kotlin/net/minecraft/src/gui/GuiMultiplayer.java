package net.minecraft.src.gui;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.StringTranslate;
import net.minecraft.src.gui.font.FontAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen {
    public GuiMultiplayer(GuiScreen guiscreen) {
        parentScreen = 0;
        serverAddress = "";
        updateCounter = guiscreen;
    }

    public void updateScreen() {
        parentScreen++;
    }

    public void initGui() {
        StringTranslate stringtranslate = StringTranslate.func_20162_a();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.func_20163_a("multiplayer.connect")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.func_20163_a("gui.cancel")));
        serverAddress = mc.gameSettings.lastServer.replaceAll("_", ":");
        ((GuiButton) controlList.get(0)).enabled = serverAddress.length() > 0;
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton) {
        if (!guibutton.enabled) {
            return;
        }
        if (guibutton.id == 1) {
            mc.displayGuiScreen(updateCounter);
        } else if (guibutton.id == 0) {
            mc.gameSettings.lastServer = serverAddress.replaceAll(":", "_");
            mc.gameSettings.saveOptions();
            String as[] = serverAddress.split(":");
            mc.displayGuiScreen(new GuiConnecting(mc, as[0], as.length <= 1 ? 25565 : func_4067_a(as[1], 25565)));
        }
    }

    private int func_4067_a(String s, int i) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception exception) {
            return i;
        }
    }

    protected void keyTyped(char c, int i) {
        if (c == '\026') {
            String s;
            int j;
            s = getClipboardString();
            if (s == null) {
                s = "";
            }
            j = 32 - serverAddress.length();
            if (j > s.length()) {
                j = s.length();
            }
            if (j > 0) {
                serverAddress += s.substring(0, j);
            }
        }
        if (c == '\r') {
            actionPerformed((GuiButton) controlList.get(0));
        }
        if (i == 14 && serverAddress.length() > 0) {
            serverAddress = serverAddress.substring(0, serverAddress.length() - 1);
        }
        if (FontAllowedCharacters.field_20157_a.indexOf(c) >= 0 && serverAddress.length() < 32) {
            serverAddress += c;
        }
        ((GuiButton) controlList.get(0)).enabled = serverAddress.length() > 0;
        return;
    }

    public void drawScreen(int i, int j, float f) {
        StringTranslate stringtranslate = StringTranslate.func_20162_a();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.func_20163_a("multiplayer.title"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.func_20163_a("multiplayer.info1"), width / 2 - 140, (height / 4 - 60) + 60 + 0, 0xa0a0a0);
        drawString(fontRenderer, stringtranslate.func_20163_a("multiplayer.info2"), width / 2 - 140, (height / 4 - 60) + 60 + 9, 0xa0a0a0);
        drawString(fontRenderer, stringtranslate.func_20163_a("multiplayer.ipinfo"), width / 2 - 140, (height / 4 - 60) + 60 + 36, 0xa0a0a0);
        int k = width / 2 - 100;
        int l = (height / 4 - 10) + 50 + 18;
        char c = '\310';
        byte byte0 = 20;
        drawRect(k - 1, l - 1, k + c + 1, l + byte0 + 1, 0xffa0a0a0);
        drawRect(k, l, k + c, l + byte0, 0xff000000);
        drawString(fontRenderer, (new StringBuilder()).append(serverAddress).append((parentScreen / 6) % 2 != 0 ? "" : "_").toString(), k + 4, l + (byte0 - 8) / 2, 0xe0e0e0);
        super.drawScreen(i, j, f);
    }

    private GuiScreen updateCounter;
    private int parentScreen;
    private String serverAddress;
}
