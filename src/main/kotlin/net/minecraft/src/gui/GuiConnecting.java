package net.minecraft.src.gui;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.networking.NetClientHandler;
import net.minecraft.src.threading.ThreadConnectToServer;

public class GuiConnecting extends GuiScreen
{

    public GuiConnecting(Minecraft minecraft, String s, int i)
    {
        cancelled = false;
        minecraft.func_6261_a(null);
        (new ThreadConnectToServer(this, minecraft, s, i)).start();
    }

    public void updateScreen()
    {
        if(clientHandler != null)
        {
            clientHandler.processReadPackets();
        }
    }

    protected void keyTyped(char c, int i)
    {
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.func_20162_a();
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.func_20163_a("gui.cancel")));
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
            cancelled = true;
            if(clientHandler != null)
            {
                clientHandler.disconnect();
            }
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        StringTranslate stringtranslate = StringTranslate.func_20162_a();
        if(clientHandler == null)
        {
            drawCenteredString(fontRenderer, stringtranslate.func_20163_a("connect.connecting"), width / 2, height / 2 - 50, 0xffffff);
            drawCenteredString(fontRenderer, "", width / 2, height / 2 - 10, 0xffffff);
        } else
        {
            drawCenteredString(fontRenderer, stringtranslate.func_20163_a("connect.authorizing"), width / 2, height / 2 - 50, 0xffffff);
            drawCenteredString(fontRenderer, clientHandler.field_1209_a, width / 2, height / 2 - 10, 0xffffff);
        }
        super.drawScreen(i, j, f);
    }

    public static NetClientHandler setNetClientHandler(GuiConnecting guiconnecting, NetClientHandler netclienthandler)
    {
        return guiconnecting.clientHandler = netclienthandler;
    }

    public static boolean isCancelled(GuiConnecting guiconnecting)
    {
        return guiconnecting.cancelled;
    }

    public static NetClientHandler getNetClientHandler(GuiConnecting guiconnecting)
    {
        return guiconnecting.clientHandler;
    }

    private NetClientHandler clientHandler;
    private boolean cancelled;
}
