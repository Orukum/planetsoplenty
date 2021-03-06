package foodisgood_orukum.mods.pop.space.testingworlds;

import foodisgood_orukum.mods.pop.block.POPBlocks;
import foodisgood_orukum.mods.pop.network.POPPacketHandlerServer;
import foodisgood_orukum.mods.pop.network.POPPacketUtils;
import foodisgood_orukum.mods.pop.space.*;
import foodisgood_orukum.mods.pop.POPLog;
import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.DimensionManager;
import micdoodle8.mods.galacticraft.api.world.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.GCCoreEntityCreeper;
import micdoodle8.mods.galacticraft.core.entities.GCCoreEntitySkeleton;
import micdoodle8.mods.galacticraft.core.entities.GCCoreEntitySpider;
import micdoodle8.mods.galacticraft.core.entities.GCCoreEntityZombie;
import micdoodle8.mods.galacticraft.core.perlin.NoiseModule;
import micdoodle8.mods.galacticraft.core.perlin.generator.Gradient;
import micdoodle8.mods.galacticraft.core.world.gen.GCCoreCraterSize;
import micdoodle8.mods.galacticraft.core.world.gen.GCCoreMapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.GCCoreMapGenDungeon;
import micdoodle8.mods.galacticraft.mars.world.gen.GCMarsBiomeGenBase;
import micdoodle8.mods.galacticraft.moon.GCMoonConfigManager;
import micdoodle8.mods.galacticraft.moon.blocks.GCMoonBlock;
import micdoodle8.mods.galacticraft.moon.world.gen.GCMoonBiomeGenBase;
import micdoodle8.mods.galacticraft.moon.world.gen.GCMoonGenCaves;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomBoss;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomChests;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomEmpty;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomSpawner;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomTreasure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.ForgeDirection;    

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class LavaCeilingPlanet extends POPMoon {
    private static final int LAVA_START_HEIGHT = 200;
    
	/*static {
        POPDebugLavaMoonChunkManager.allowedBiomes.add(GCMoonBiomeGenBase.moonFlat);
        POPDebugLavaMoonChunkManager.allowedBiomes.add(GCMarsBiomeGenBase.marsFlat);
        POPDebugLavaMoonChunkManager.allowedBiomes.add(BiomeGenBase.hell);
        //TODO: make sure this is affecting only this world provider... look into how statics work
	}*/
    
	public LavaCeilingPlanet(int dimension) {
		super();
		dimensionId = dimension;
		//this.getSeed();
	}
	
	public LavaCeilingPlanet() {
		super();
		dimensionId = POPCelestials.lavaCeilingPlanet.dimensionId;
	}
	
	@Override
	public String getName() {
		return "Lava Moon Debug";
	}

	@Override
	public boolean addToList() {
		return true;
	}

	@Override
	public boolean autoRegister() {
		return true;
	}

	@Override
	public Class<? extends WorldProvider> getWorldProvider() {
		return LavaCeilingPlanet.class;
	}

	@Override
	public IGalaxy getParentGalaxy() {
		return POPCelestials.westGalaxy;
		//return GalacticraftCore.galaxyMilkyWay;
	}

	@Override
	public float getPlanetSize() {
		return .39F;
	}

	@Override
	public float getDistanceFromCenter() {
		return .1F;
	}

	@Override
	public float getPhaseShift() {
		return 5F;
	}

	@Override
	public float getStretchValue() {
		return (1F/.6F)/8.34F;
	}

	@Override
	public ResourceLocation getPlanetSprite() {
		return new ResourceLocation(PlanetsOPlenty.TEXTURE_DOMAIN, "textures/planets/nether.png");
	}
	
	@Override
	public float getGravity() {
		return POPPlanet.getGCGravityFactor(.7F);
	}
	
	@Override
	public double getMeteorFrequency() {
		return .3D;
	}
	
	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier>3;
	}
	
	@Override
	public float getSoundVolReductionAmount() {
		return .34F;
	}
	
    /*@Override
    public void setDimension(int var1)
    {
        this.dimensionId = var1;
        super.setDimension(var1);
    }

    @Override
    protected void generateLightBrightnessTable()
    {
        final float var1 = 0.0F;

        for (int var2 = 0; var2 <= 15; ++var2)
        {
            final float var3 = 1.0F - var2 / 15.0F;
            this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
        }
    }

    @Override
    public float[] calcSunriseSunsetColors(float var1, float var2)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Vec3 getFogColor(float var1, float var2)
    {
        return this.worldObj.getWorldVec3Pool().getVecFromPool((double) 0F / 255F, (double) 0F / 255F, (double) 0F / 255F);
    }

    @Override
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
    {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(0, 0, 0);
    }

    @Override
    public float calculateCelestialAngle(long par1, float par3)
    {
        final int var4 = (int) (par1 % 192000L);
        float var5 = (var4 + par3) / 192000.0F - 0.25F;

        if (var5 < 0.0F)
        {
            ++var5;
        }

        if (var5 > 1.0F)
        {
            --var5;
        }

        final float var6 = var5;
        var5 = 1.0F - (float) ((Math.cos(var5 * Math.PI) + 1.0D) / 2.0D);
        var5 = var6 + (var5 - var6) / 3.0F;
        return var5;
    }

    @Override
    public double getHorizon()
    {
        return 44.0D;
    }

    @Override
    public int getAverageGroundLevel()
    {
        return 44;
    }

    @Override
    public boolean canRespawnHere()
    {
        return !GCCoreConfigManager.forceOverworldRespawn;
    }

    @Override
    public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }*/
	
	@Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1) {
        final float var2 = this.worldObj.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
            var3 = 0.0F;
        else if (var3 > 1.0F)
            var3 = 1.0F;

        return var3 * var3 * 0.5F + 0.3F;
    }

    @Override
    public void registerWorldChunkManager() {
    	if (Loader.isModLoaded("BiomesOPlenty"))
    		this.worldChunkMgr = new POPDebugLavaMoonChunkManager(worldObj.getSeed()^dimensionId, WorldType.parseWorldType("BIOMESOP"));
    	else
    		this.worldChunkMgr = new POPDebugLavaMoonChunkManager(worldObj.getSeed()^dimensionId, WorldType.DEFAULT);
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new POPDebugLavaMoonChunkProvider(this.worldObj, this.worldObj.getSeed()+1, true);
    }

    @Override
    public boolean isSkyColored() {
        return false;
    }

    @Override
    public String getWelcomeMessage() {
        return "Welcome to Debug Lava Planet!";
    }

    @Override
    public String getDepartMessage() {
        return "Goodbye!";
    }

    @Override
    public boolean canSnowAt(int x, int y, int z) {
        return false;
    }

    @Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
    	return false;
    }

    @Override
    public int getHeight() {
        return 1234;
    }
    
    public class POPDebugLavaMoonChunkManager extends WorldChunkManager {
    	public POPDebugLavaMoonChunkManager(long seed, WorldType type) {
    		super (seed, type);
    	}
    	
    	@Override
        public float[] getTemperatures(float[] par1ArrayOfFloat, int x, int y, int width, int length) {
        	//if (y<LAVA_START_HEIGHT)
        	//	return super.getTemperatures(par1ArrayOfFloat, x, y, width, length);
            if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < width * length)
                par1ArrayOfFloat = new float[width * length];
            /*for (int x2=x; x2<(x+width); x2++)
            	for (int y2=y; y2<(y+length); y2++)
            			par1ArrayOfFload*/
            if (isDaytime())
            	Arrays.fill(par1ArrayOfFloat, 0, width * length, 3000);
            else
            	Arrays.fill(par1ArrayOfFloat, 0, width * length, 20);
            return par1ArrayOfFloat;
        }

        @Override
        public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
        	if (par3<LAVA_START_HEIGHT)
        		return super.getRainfall(par1ArrayOfFloat, par2, par3, par4, par5);
            if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
                par1ArrayOfFloat = new float[par4 * par5];

            Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, 2);
            return par1ArrayOfFloat;
        }

        /*@SuppressWarnings("rawtypes")
        @Override
        public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
        {
            return par4List.contains(GCMoonBiomeGenBase.moonFlat) ? new ChunkPosition(par1 - par3 + par5Random.nextInt(par3 * 2 + 1), 0, par2 - par3 + par5Random.nextInt(par3 * 2 + 1)) : null;
        }*/

        /*@SuppressWarnings("rawtypes")
        @Override
        public boolean areBiomesViable(int par1, int par2, int par3, List par4List) {
        	return true;
            //return par4List.contains(GCMoonBiomeGenBase.moonFlat);
        }*/
    }
    
	public short blockID = (short) POPBlocks.specialStone.blockID;
    
    public class POPDebugLavaMoonChunkProvider extends ChunkProviderGenerate {
        /*final short topBlockIDHigh = (short) Block.grass.blockID;
        final byte topBlockMetaHigh = 0;
        final short fillBlockIDHigh = (short) Block.anvil.blockID;
        final byte fillBlockMetaHigh = 0;
        final short lowerBlockIDHigh = (short) Block.glass.blockID;
        final byte lowerBlockMetaHigh = 2;
        final short topBlockIDLow = (short) Block.cake.blockID;
        final byte topBlockMetaLow = 0;
        final short fillBlockIDLow = (short) Block.stone.blockID;
        final byte fillBlockMetaLow = 0;
        final short lowerBlockIDLow = (short) Block.blockEmerald.blockID;
        final byte lowerBlockMetaLow = 3;*/
    	final byte blockMeta = 0;

        private final Random rand;
    	private final MapGenVillage villages;

        private final NoiseModule noiseGen1;
        private final NoiseModule noiseGen2;
        private final NoiseModule noiseGen3;
        private final NoiseModule noiseGen4;

        private final World worldObj;

        //private BiomeGenBase[] biomesForGeneration = { GCMoonBiomeGenBase.moonFlat };

        private final GCCoreMapGenBaseMeta caveGenerator = new GCMoonGenCaves();

        // DO NOT CHANGE
        private static final int MID_HEIGHT = 86;
        private static final int CHUNK_SIZE_X = 16;
        private static final int CHUNK_SIZE_Y = 256;
        private static final int CHUNK_SIZE_Z = 16;
        private static final int CRATER_PROB = 100;
        private static final int LAVA_DEPTH = 30;
        
        private IChunkProvider bopGenerator = null;

        public POPDebugLavaMoonChunkProvider(World par1World, long par2, boolean par4) {
            super(par1World, par2, par4);
            villages = new MapGenVillage();
        	if (Loader.isModLoaded("BiomesOPlenty"))
				try {
					bopGenerator = (IChunkProvider) Class.forName("biomesoplenty.world.ChunkProviderBOP").getConstructor(World.class, long.class, boolean.class).newInstance(par1World, par2, par4);
				} catch (Exception e) {
					POPLog.severe("Error with loading BOP chunk provider");
					e.printStackTrace();
					PacketDispatcher.sendPacketToServer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, POPPacketHandlerServer.EnumPacketServer.DEBUG_EXCEPTION_NOTIFY.index, e.getMessage()));
				}
            this.worldObj = par1World;
            this.rand = new Random(par2);
            this.noiseGen1 = new Gradient(this.rand.nextLong(), 4, 0.25);
            this.noiseGen2 = new Gradient(this.rand.nextLong(), 4, 1.25);
            this.noiseGen3 = new Gradient(this.rand.nextLong(), 1, 0.25);
            this.noiseGen4 = new Gradient(this.rand.nextLong(), 1, 0.25);
        }
        
        public void generateTerrain(int chunkX, int chunkZ, short[] idArray, byte[] metaArray) {
        	if (chunkZ>5)
        		return;
            this.noiseGen1.frequency = chunkX<-10 ? 0.125 : 0.0125;
            this.noiseGen2.frequency = 0.015;
            this.noiseGen3.frequency = 0.01;
            this.noiseGen4.frequency = 0.02;
            for (int x = 0; x < CHUNK_SIZE_X; x++) {
                for (int z = 0; z < CHUNK_SIZE_Z; z++) {
                    final double d = this.noiseGen1.getNoise(x + chunkX * 16, z + chunkZ * 16) * 8;
                    final double d2 = this.noiseGen2.getNoise(x + chunkX * 16, z + chunkZ * 16) * 24;
                    double d3 = this.noiseGen3.getNoise(x + chunkX * 16, z + chunkZ * 16) - 0.1;
                    d3 *= 4;

                    double yDev = 0;

                    if (d3 < 0.0D)
                        yDev = d;
                    else if (d3 > 1.0D)
                        yDev = d2;
                    else
                        yDev = d + (d2 - d) * d3;
                    yDev*=10*Math.sqrt((1730)*(1730)+(456)*(456))/832F;
                    yDev-=50;
                    for (int y = LAVA_START_HEIGHT+1; y < CHUNK_SIZE_Y; y++) {
                        if ((y < (MID_HEIGHT + yDev) && y<220)) {
                            idArray[getIndex(x, y, z)] = blockID;
                            metaArray[getIndex(x, y, z)] = this.blockMeta;
                        } else
                        	break;
                    }
                }
            }
        }
       
        @Override
        public Chunk provideChunk(int par1, int par2) {
        	final Chunk chunk;
        	if (bopGenerator!=null)
        		chunk = bopGenerator.provideChunk(par1, par2);
        	else
        		chunk = super.provideChunk(par1,  par2);
            this.rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
            final short[] ids = new short[32768*2];
            final byte[] meta = new byte[32768*2];
            Arrays.fill(ids, (short)0);
            Arrays.fill(meta, (byte)0);
            this.generateTerrain(par1, par2, ids, meta);
            //this.replaceBlocksForBiome(par1, par2, ids, meta);
            //if (par1>10)
            	this.createCraters(par1, par2, ids, meta);
            for (int x=0; x<CHUNK_SIZE_X; x++)
            	for (int z=0; z<CHUNK_SIZE_Z; z++) {
            		ids[getIndex(x, LAVA_START_HEIGHT, z)] = (short) POPBlocks.invisible.blockID;
		            for (int y = LAVA_START_HEIGHT+1; y < LAVA_START_HEIGHT+LAVA_DEPTH; y++)
		                if (ids[getIndex(x, y, z)]==0) {
		                    ids[getIndex(x, y, z)] = (short) POPBlocks.specialLavaStill.blockID;
		                    meta[getIndex(x, y, z)] = 0;
		                }
            	}
            for (int x=0; x<CHUNK_SIZE_X; x++)
                for (int z=0; z<CHUNK_SIZE_Z; z++)
                    for (int y=0; y<CHUNK_SIZE_Y; y++)
                    	if (chunk.getBlockID(x, y, z)!=0) {
                    		//chunk.setBlockIDWithMetadata(x, y, z, ids[getIndex(x, y, z)], meta[getIndex(x, y, z)]);
                    		ids[getIndex(x, y, z)] = (short) chunk.getBlockID(x, y, z);
                    		meta[getIndex(x, y, z)] = (byte) chunk.getBlockMetadata(x, y, z);
                    	}
            final Chunk var4 = new Chunk(this.worldObj, ids, meta, par1, par2);
            // GCCoreConfigManager.disableExternalModGen)
            // {
            // var4.isTerrainPopulated = true;
            // }
            var4.generateSkylightMap();
            return var4;
        }

        public void createCraters(int chunkX, int chunkZ, short[] chunkArray, byte[] metaArray) {
        	if (chunkX<8)
        		return;
            for (int cx = chunkX - 2; cx <= chunkX + 2; cx++)
                for (int cz = chunkZ - 2; cz <= chunkZ + 2; cz++)
                    for (int x = 0; x < CHUNK_SIZE_X; x++)
                        for (int z = 0; z < CHUNK_SIZE_Z; z++)
                            if (Math.abs(randFromPointSeed(cx * 16 + x, (cz * 16 + z) * 1000)) < this.noiseGen4.getNoise(x * CHUNK_SIZE_X + x, cz * CHUNK_SIZE_Z + z) / CRATER_PROB) {
                                final Random random = new Random(cx * 16 + x + (cz * 16 + z) * 5000);
                                final GCCoreCraterSize cSize = GCCoreCraterSize.sizeArray[random.nextInt(GCCoreCraterSize.sizeArray.length)];
                                final int size = random.nextInt(cSize.MAX_SIZE - cSize.MIN_SIZE) + cSize.MIN_SIZE;
                                this.makeCrater(cx * 16 + x, cz * 16 + z, chunkX * 16, chunkZ * 16, size, chunkArray, metaArray);
                            }
        }

        public void makeCrater(int craterX, int craterZ, int chunkX, int chunkZ, int size, short[] chunkArray, byte[] metaArray) {
            for (int x = 0; x < CHUNK_SIZE_X; x++)
                for (int z = 0; z < CHUNK_SIZE_Z; z++) {
                    double xDev = craterX - (chunkX + x);
                    double zDev = craterZ - (chunkZ + z);
                    if (xDev * xDev + zDev * zDev < size * size) {
                        xDev /= size;
                        zDev /= size;
                        final double sqrtY = xDev * xDev + zDev * zDev;
                        double yDev = sqrtY * sqrtY * 6;
                        yDev = 5 - yDev;
                        int helper = 0;
                        for (int y = 127; y > 0; y--) {
                            if (chunkArray[getIndex(x, y, z)] != 0 && helper <= yDev) {
                                chunkArray[getIndex(x, y, z)] = 0;
                                metaArray[getIndex(x, y, z)] = 0;
                                helper++;
                            }
                            if (helper > yDev)
                                break;
                        }
                    }
                }
        }

        @Override
        public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        	super.populate(par1IChunkProvider, par2, par3);
        	if (bopGenerator!=null)
        		bopGenerator.populate(par1IChunkProvider, par2, par3);
            BlockSand.fallInstantly = false;
            /*final int var4 = par2 * 16;
            final int var5 = par3 * 16;
            this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
            this.rand.setSeed(this.worldObj.getSeed());
            final long var7 = this.rand.nextLong() / 2L * 2L + 1L;
            final long var9 = this.rand.nextLong() / 2L * 2L + 1L;
            this.rand.setSeed(par2 * var7 + par3 * var9 ^ this.worldObj.getSeed());*/

            /*if (!GCMoonConfigManager.disableMoonVillageGen)
            {
                this.villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            }*/

            //this.decoratePlanet(this.worldObj, this.rand, var4, var5);
            //BlockSand.fallInstantly = false;
        }

        /*@SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int i, int j, int k) {
            if (par1EnumCreatureType == EnumCreatureType.monster) {
                final List monsters = new ArrayList();
                monsters.add(new SpawnListEntry(GCCoreEntityZombie.class, 8, 2, 3));
                monsters.add(new SpawnListEntry(GCCoreEntitySpider.class, 8, 2, 3));
                monsters.add(new SpawnListEntry(GCCoreEntitySkeleton.class, 8, 2, 3));
                monsters.add(new SpawnListEntry(GCCoreEntityCreeper.class, 8, 2, 3));
                return monsters;
            } else {
                return null;
            }
        }*/

        @Override
        public void recreateStructures(int par1, int par2) {
            this.villages.generate(this, this.worldObj, par1, par2, (byte[]) null);
        }
    }

	@Override
	public double getYCoordinateToTeleport() {
		return 1000;
	}

	@Override
	public boolean forceStaticLoad() {
		return false;
	}

	@Override
	public IPlanet getParentPlanet() {
		return POPCelestials.superflatWestPlanet;
	}
}
