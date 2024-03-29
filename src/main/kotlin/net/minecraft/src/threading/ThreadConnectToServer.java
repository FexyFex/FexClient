package net.minecraft.src.threading;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.net.ConnectException;
import java.net.UnknownHostException;

import net.minecraft.client.Minecraft;
import net.minecraft.src.gui.GuiConnectFailed;
import net.minecraft.src.gui.GuiConnecting;
import net.minecraft.src.networking.NetClientHandler;
import net.minecraft.src.packet.Packet2Handshake;

public class ThreadConnectToServer extends Thread
{

    public ThreadConnectToServer(GuiConnecting guiconnecting, Minecraft minecraft, String s, int i)
    {
        connectingGui = guiconnecting;
        mc = minecraft;
        hostName = s;
        port = i;
    }

    public void run()
    {
        try
        {
            GuiConnecting.setNetClientHandler(connectingGui, new NetClientHandler(mc, hostName, port));
            if(GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }
            GuiConnecting.getNetClientHandler(connectingGui).addToSendQueue(new Packet2Handshake(mc.session.playerName));
        }
        catch(UnknownHostException unknownhostexception)
        {
            if(GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }
            mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[] {
                (new StringBuilder()).append("Unknown host '").append(hostName).append("'").toString()
            }));
        }
        catch(ConnectException connectexception)
        {
            if(GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }
            mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[] {
                connectexception.getMessage()
            }));
        }
        catch(Exception exception)
        {
            if(GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }
            exception.printStackTrace();
            mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[] {
                exception.toString()
            }));
        }
    }

    final Minecraft mc; /* synthetic field */
    final String hostName; /* synthetic field */
    final int port; /* synthetic field */
    final GuiConnecting connectingGui; /* synthetic field */
}
