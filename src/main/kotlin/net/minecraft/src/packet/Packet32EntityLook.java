package net.minecraft.src.packet;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet32EntityLook extends Packet30Entity
{

    public Packet32EntityLook()
    {
        rotating = true;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        super.readPacketData(datainputstream);
        yaw = datainputstream.readByte();
        pitch = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        super.writePacketData(dataoutputstream);
        dataoutputstream.writeByte(yaw);
        dataoutputstream.writeByte(pitch);
    }

    public int getPacketSize()
    {
        return 6;
    }
}
