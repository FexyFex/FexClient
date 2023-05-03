package net.minecraft.src.world.chunk;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.block.World;

import java.io.IOException;

public class ChunkProviderLoadOrGenerate implements IChunkProvider {
    public ChunkProviderLoadOrGenerate(World world, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
        chunks = new Chunk[1024];
        lastQueriedChunkXPos = 0xc4653601;
        lastQueriedChunkZPos = 0xc4653601;
        blankChunk = new Chunk(world, new byte[32768], 0, 0);
        blankChunk.field_1524_q = true;
        blankChunk.neverSave = true;
        worldObj = world;
        chunkLoader = ichunkloader;
        chunkProvider = ichunkprovider;
    }

    public boolean chunkExists(int i, int j) {
        if (i == lastQueriedChunkXPos && j == lastQueriedChunkZPos && lastQueriedChunk != null) {
            return true;
        } else {
            int k = i & 0x1f;
            int l = j & 0x1f;
            int i1 = k + l * 32;
            return chunks[i1] != null && (chunks[i1] == blankChunk || chunks[i1].isAtLocation(i, j));
        }
    }

    public Chunk provideChunk(int x, int z) {
        if (x == lastQueriedChunkXPos && z == lastQueriedChunkZPos && lastQueriedChunk != null) {
            return lastQueriedChunk;
        }
        int k = x & 0x1f;
        int l = z & 0x1f;
        int i1 = k + l * 32;
        if (!chunkExists(x, z)) {
            if (chunks[i1] != null) {
                chunks[i1].onChunkUnload();
                saveChunk(chunks[i1]);
                saveExtraChunkData(chunks[i1]);
            }
            Chunk chunk = func_542_c(x, z);
            if (chunk == null) {
                if (chunkProvider == null) {
                    chunk = blankChunk;
                } else {
                    chunk = chunkProvider.provideChunk(x, z);
                }
            }
            chunks[i1] = chunk;
            chunk.func_4143_d();
            if (chunks[i1] != null) {
                chunks[i1].onChunkLoad();
            }
            if (!chunks[i1].isTerrainPopulated && chunkExists(x + 1, z + 1) && chunkExists(x, z + 1) && chunkExists(x + 1, z)) {
                populate(this, x, z);
            }
            if (chunkExists(x - 1, z) && !provideChunk(x - 1, z).isTerrainPopulated && chunkExists(x - 1, z + 1) && chunkExists(x, z + 1) && chunkExists(x - 1, z)) {
                populate(this, x - 1, z);
            }
            if (chunkExists(x, z - 1) && !provideChunk(x, z - 1).isTerrainPopulated && chunkExists(x + 1, z - 1) && chunkExists(x, z - 1) && chunkExists(x + 1, z)) {
                populate(this, x, z - 1);
            }
            if (chunkExists(x - 1, z - 1) && !provideChunk(x - 1, z - 1).isTerrainPopulated && chunkExists(x - 1, z - 1) && chunkExists(x, z - 1) && chunkExists(x - 1, z)) {
                populate(this, x - 1, z - 1);
            }
        }
        lastQueriedChunkXPos = x;
        lastQueriedChunkZPos = z;
        lastQueriedChunk = chunks[i1];
        return chunks[i1];
    }

    private Chunk func_542_c(int i, int j) {
        if (chunkLoader == null) {
            return null;
        }
        try {
            Chunk chunk = chunkLoader.loadChunk(worldObj, i, j);
            if (chunk != null) {
                chunk.lastSaveTime = worldObj.worldTime;
            }
            return chunk;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private void saveExtraChunkData(Chunk chunk) {
        if (chunkLoader == null) {
            return;
        }
        try {
            chunkLoader.saveExtraChunkData(worldObj, chunk);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void saveChunk(Chunk chunk) {
        if (chunkLoader == null) {
            return;
        }
        try {
            chunk.lastSaveTime = worldObj.worldTime;
            chunkLoader.saveChunk(worldObj, chunk);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j) {
        Chunk chunk = provideChunk(i, j);
        if (!chunk.isTerrainPopulated) {
            chunk.isTerrainPopulated = true;
            if (chunkProvider != null) {
                chunkProvider.populate(ichunkprovider, i, j);
                chunk.setChunkModified();
            }
        }
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        int i = 0;
        int j = 0;
        if (iprogressupdate != null) {
            for (int k = 0; k < chunks.length; k++) {
                if (chunks[k] != null && chunks[k].needsSaving(flag)) {
                    j++;
                }
            }

        }
        int l = 0;
        for (int i1 = 0; i1 < chunks.length; i1++) {
            if (chunks[i1] == null) {
                continue;
            }
            if (flag && !chunks[i1].neverSave) {
                saveExtraChunkData(chunks[i1]);
            }
            if (!chunks[i1].needsSaving(flag)) {
                continue;
            }
            saveChunk(chunks[i1]);
            chunks[i1].isModified = false;
            if (++i == 2 && !flag) {
                return false;
            }
            if (iprogressupdate != null && ++l % 10 == 0) {
                iprogressupdate.setLoadingProgress((l * 100) / j);
            }
        }

        if (flag) {
            if (chunkLoader == null) {
                return true;
            }
            chunkLoader.saveExtraData();
        }
        return true;
    }

    public boolean func_532_a() {
        if (chunkLoader != null) {
            chunkLoader.func_814_a();
        }
        return chunkProvider.func_532_a();
    }

    public boolean func_536_b() {
        return true;
    }

    private Chunk blankChunk;
    private IChunkProvider chunkProvider;
    private IChunkLoader chunkLoader;
    private Chunk chunks[];
    private World worldObj;
    int lastQueriedChunkXPos;
    int lastQueriedChunkZPos;
    private Chunk lastQueriedChunk;
}
