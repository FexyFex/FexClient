package net.minecraft.src.networking;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkManager {

    public NetworkManager(Socket socket, String s, NetHandler nethandler) throws IOException {
        sendQueueLock = new Object();
        isRunning = true;
        readPackets = Collections.synchronizedList(new ArrayList());
        dataPackets = Collections.synchronizedList(new ArrayList());
        chunkDataPackets = Collections.synchronizedList(new ArrayList());
        isServerTerminating = false;
        isTerminating = false;
        terminationReason = "";
        timeSinceLastRead = 0;
        sendQueueByteLength = 0;
        chunkDataSendCounter = 0;
        field_20100_w = 0;
        field_12258_e = socket;
        networkSocket = socket.getRemoteSocketAddress();
        netHandler = nethandler;
        socket.setTrafficClass(24);
        socketInputStream = new DataInputStream(socket.getInputStream());
        socketOutputStream = new DataOutputStream(socket.getOutputStream());
        readThread = new NetworkReaderThread(this, (new StringBuilder()).append(s).append(" read thread").toString());
        writeThread = new NetworkWriterThread(this, (new StringBuilder()).append(s).append(" write thread").toString());
        readThread.start();
        writeThread.start();
    }

    public void addToSendQueue(Packet packet) {
        if (isServerTerminating) {
            return;
        }
        synchronized (sendQueueLock) {
            sendQueueByteLength += packet.getPacketSize() + 1;
            if (packet.isChunkDataPacket) {
                chunkDataPackets.add(packet);
            } else {
                dataPackets.add(packet);
            }
        }
    }

    private void sendPacket() {
        try {
            boolean flag = true;
            if (!dataPackets.isEmpty() && (chunkDataSendCounter == 0 || System.currentTimeMillis() - ((Packet) dataPackets.get(0)).field_20018_j >= (long) chunkDataSendCounter)) {
                flag = false;
                Packet packet;
                synchronized (sendQueueLock) {
                    packet = (Packet) dataPackets.remove(0);
                    sendQueueByteLength -= packet.getPacketSize() + 1;
                }
                Packet.writePacket(packet, socketOutputStream);
            }
            if ((flag || field_20100_w-- <= 0) && !chunkDataPackets.isEmpty() && (chunkDataSendCounter == 0 || System.currentTimeMillis() - ((Packet) chunkDataPackets.get(0)).field_20018_j >= (long) chunkDataSendCounter)) {
                flag = false;
                Packet packet1;
                synchronized (sendQueueLock) {
                    packet1 = (Packet) chunkDataPackets.remove(0);
                    sendQueueByteLength -= packet1.getPacketSize() + 1;
                }
                Packet.writePacket(packet1, socketOutputStream);
                field_20100_w = 50;
            }
            if (flag) {
                Thread.sleep(10L);
            }
        } catch (InterruptedException interruptedexception) {
        } catch (Exception exception) {
            if (!isTerminating) {
                onNetworkError(exception);
            }
        }
    }

    private void readPacket() {
        try {
            Packet packet = Packet.readPacket(socketInputStream);
            if (packet != null) {
                readPackets.add(packet);
            } else {
                networkShutdown("disconnect.endOfStream", new Object[0]);
            }
        } catch (Exception exception) {
            if (!isTerminating) {
                onNetworkError(exception);
            }
        }
    }

    private void onNetworkError(Exception exception) {
        exception.printStackTrace();
        networkShutdown("disconnect.genericReason", new Object[]{
                (new StringBuilder()).append("Internal exception: ").append(exception.toString()).toString()
        });
    }

    public void networkShutdown(String s, Object aobj[]) {
        if (!isRunning) {
            return;
        }
        isTerminating = true;
        terminationReason = s;
        field_20101_t = aobj;
        (new NetworkMasterThread(this)).start();
        isRunning = false;
        try {
            socketInputStream.close();
            socketInputStream = null;
        } catch (Throwable throwable) {
        }
        try {
            socketOutputStream.close();
            socketOutputStream = null;
        } catch (Throwable throwable1) {
        }
        try {
            field_12258_e.close();
            field_12258_e = null;
        } catch (Throwable throwable2) {
        }
    }

    public void processReadPackets() {
        if (sendQueueByteLength > 0x100000) {
            networkShutdown("disconnect.overflow", new Object[0]);
        }
        if (readPackets.isEmpty()) {
            if (timeSinceLastRead++ == 1200) {
                networkShutdown("disconnect.timeout", new Object[0]);
            }
        } else {
            timeSinceLastRead = 0;
        }
        Packet packet;
        for (int i = 100; !readPackets.isEmpty() && i-- >= 0; packet.processPacket(netHandler)) {
            packet = (Packet) readPackets.remove(0);
        }

        if (isTerminating && readPackets.isEmpty()) {
            netHandler.handleErrorMessage(terminationReason, field_20101_t);
        }
    }

    static boolean isRunning(NetworkManager networkmanager) {
        return networkmanager.isRunning;
    }

    static boolean isServerTerminating(NetworkManager networkmanager) {
        return networkmanager.isServerTerminating;
    }

    static void readNetworkPacket(NetworkManager networkmanager) {
        networkmanager.readPacket();
    }

    static void sendNetworkPacket(NetworkManager networkmanager) {
        networkmanager.sendPacket();
    }

    static Thread getReadThread(NetworkManager networkmanager) {
        return networkmanager.readThread;
    }

    static Thread getWriteThread(NetworkManager networkmanager) {
        return networkmanager.writeThread;
    }

    public static final Object threadSyncObject = new Object();
    public static int numReadThreads;
    public static int numWriteThreads;
    private Object sendQueueLock;
    private Socket field_12258_e;
    private final SocketAddress networkSocket;
    private DataInputStream socketInputStream;
    private DataOutputStream socketOutputStream;
    private boolean isRunning;
    private List readPackets;
    private List dataPackets;
    private List chunkDataPackets;
    private NetHandler netHandler;
    private boolean isServerTerminating;
    private Thread writeThread;
    private Thread readThread;
    private boolean isTerminating;
    private String terminationReason;
    private Object field_20101_t[];
    private int timeSinceLastRead;
    private int sendQueueByteLength;
    public int chunkDataSendCounter;
    private int field_20100_w;

}
