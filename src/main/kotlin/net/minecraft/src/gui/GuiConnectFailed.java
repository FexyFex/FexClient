package net.minecraft.src.gui;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.StringTranslate;

public class GuiConnectFailed extends GuiScreen {
    public GuiConnectFailed(String s, String s1, Object aobj[]) {
        StringTranslate stringtranslate = StringTranslate.func_20162_a();
        errorMessage = stringtranslate.func_20163_a(s);
        if (aobj != null) {
            errorDetail = stringtranslate.func_20160_a(s1, aobj);
        } else {
            errorDetail = stringtranslate.func_20163_a(s1);
        }
    }

    public void updateScreen() {
    }

    protected void keyTyped(char c, int i) {
    }

    public void initGui() {
        StringTranslate stringtranslate = StringTranslate.func_20162_a();
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.func_20163_a("gui.toMenu")));
    }

    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == 0) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    public void drawScreen(int i, int j, float f) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, errorMessage, width / 2, height / 2 - 50, 0xffffff);
        drawCenteredString(fontRenderer, errorDetail, width / 2, height / 2 - 10, 0xffffff);
        super.drawScreen(i, j, f);
    }

    private String errorMessage;
    private String errorDetail;
}
