package foodisgood_orukum.mods.pop.space;

import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.common.DimensionManager;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.world.IMoon;
import micdoodle8.mods.galacticraft.mars.dimension.GCMarsTeleportType;
import micdoodle8.mods.galacticraft.moon.dimension.GCMoonTeleportType;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foodisgood_orukum.mods.pop.POPConfigManager;
import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import foodisgood_orukum.mods.pop.client.POPSkyProvider;
import foodisgood_orukum.mods.pop.dimension.POPDebugTeleportType;
import foodisgood_orukum.mods.pop.space.testingworlds.*;

public class POPCelestials {
	public static EastGalaxy eastGalaxy;
	public static WestGalaxy westGalaxy;
	public static NetherGalaxy northGalaxy;
	public static WestStar westStar;
	public static EastStarA eastStarA;
	public static EastStarB eastStarB;
	public static SuperflatEastPlanet superflatEastPlanet;
	public static SuperflatWestPlanet superflatWestPlanet;
	public static NetherPlanet netherPlanet;
	public static LavaPlanet lavaPlanet;
	public static LavaCeilingPlanet lavaCeilingPlanet;
	public static WestPlanet2 west2;
	public static WestPlanet3 west3;
	public static WestMoonGradient westMoonGradient;
	public static WestMoonBillowed westMoonBillowed;
	public static WestMoonRidged westMoonRidged;
	public static WestMoonAll westMoonAll;
	
	public static void preInit(FMLPreInitializationEvent event) {
		if (PlanetsOPlenty.debug) {
			eastGalaxy = new EastGalaxy();
			westGalaxy = new WestGalaxy();
			northGalaxy = new NetherGalaxy();
			westStar = new WestStar();
			westGalaxy.suns = new POPStar[1];
			westGalaxy.suns[0] = westStar;
			//westGalaxy.suns.add(westStar);
			eastStarA = new EastStarA();
			eastGalaxy.suns = new POPStar[2];
			//eastGalaxy.suns.add(eastStarA);
			eastGalaxy.suns[0] = eastStarA;
			eastStarB = new EastStarB();
			eastGalaxy.suns[1] = eastStarB;
			//eastGalaxy.suns.add(eastStarB);
			int currentDimension = POPConfigManager.idDimensionRangeStart;
			superflatEastPlanet = new SuperflatEastPlanet(currentDimension++);
			netherPlanet = new NetherPlanet();
			superflatWestPlanet = new SuperflatWestPlanet(currentDimension++);
			lavaPlanet = new LavaPlanet(currentDimension++);
			lavaCeilingPlanet = new LavaCeilingPlanet(currentDimension++);
			superflatWestPlanet.moons = new IMoon[1];
			superflatWestPlanet.moons[0] = lavaCeilingPlanet;
			west2 = new WestPlanet2(currentDimension++);
			west3 = new WestPlanet3(currentDimension++);
			westMoonAll = new WestMoonAll(currentDimension++);
			westMoonRidged = new WestMoonRidged(currentDimension++);
			westMoonBillowed = new WestMoonBillowed(currentDimension++);
			westMoonGradient = new WestMoonGradient(currentDimension++);
			west3.moons = new IMoon[4];
			west3.moons[3] = westMoonGradient;
			west3.moons[2] = westMoonBillowed;
			west3.moons[1] = westMoonRidged;
			west3.moons[0] = westMoonAll;
			
			superflatEastPlanet.initSolar(1);
			superflatWestPlanet.initSolar(1);
			lavaPlanet.initSolar(1);
			lavaCeilingPlanet.initSolar(1);
			west2.initSolar(1);
			west3.initSolar(1);
			westMoonGradient.initSolar(1);
			westMoonBillowed.initSolar(1);
			westMoonRidged.initSolar(1);
			westMoonAll.initSolar(1);
	
			GalacticraftRegistry.registerGalaxy(westGalaxy);
			GalacticraftRegistry.registerGalaxy(eastGalaxy);
			GalacticraftRegistry.registerGalaxy(northGalaxy);
			GalacticraftRegistry.registerCelestialBody(westStar);
			GalacticraftRegistry.registerCelestialBody(eastStarA);
			GalacticraftRegistry.registerCelestialBody(eastStarB);
			GalacticraftRegistry.registerCelestialBody(superflatEastPlanet);
			GalacticraftRegistry.registerCelestialBody(superflatWestPlanet);
			GalacticraftRegistry.registerCelestialBody(netherPlanet);
			GalacticraftRegistry.registerCelestialBody(lavaPlanet);
			GalacticraftRegistry.registerCelestialBody(lavaCeilingPlanet);
			GalacticraftRegistry.registerCelestialBody(west2);
			GalacticraftRegistry.registerCelestialBody(west3);
			GalacticraftRegistry.registerCelestialBody(westMoonGradient);
			GalacticraftRegistry.registerCelestialBody(westMoonBillowed);
			GalacticraftRegistry.registerCelestialBody(westMoonRidged);
			GalacticraftRegistry.registerCelestialBody(westMoonAll);
			
			GalacticraftRegistry.registerTeleportType(SuperflatEastPlanet.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(WorldProviderHell.class, new GCMarsTeleportType());
			GalacticraftRegistry.registerTeleportType(SuperflatWestPlanet.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(LavaPlanet.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(LavaCeilingPlanet.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(WestPlanet2.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(WestPlanet3.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(WestMoonGradient.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(WestMoonBillowed.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(WestMoonRidged.class, new POPDebugTeleportType());
			GalacticraftRegistry.registerTeleportType(WestMoonAll.class, new POPDebugTeleportType());
		}
	}
	
	public static void init(FMLInitializationEvent event) {
		if (PlanetsOPlenty.debug && !superflatEastPlanet.autoRegister()) {
			DimensionManager.registerProviderType(superflatEastPlanet.getDimensionID(), superflatEastPlanet.getWorldProvider(), false);
			DimensionManager.registerDimension(superflatEastPlanet.getDimensionID(), superflatEastPlanet.getDimensionID());
			FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(superflatEastPlanet.getDimensionID());
		}
		if (PlanetsOPlenty.debug && !superflatWestPlanet.autoRegister()) {
			DimensionManager.registerProviderType(superflatWestPlanet.getDimensionID(), superflatWestPlanet.getWorldProvider(), false);
			DimensionManager.registerDimension(superflatWestPlanet.getDimensionID(), superflatWestPlanet.getDimensionID());
			FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(superflatWestPlanet.getDimensionID());
		}
	}
}
