package foodisgood_orukum.mods.pop.space;

import foodisgood_orukum.mods.pop.POPConfigManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.WorldProvider;
import micdoodle8.mods.galacticraft.api.world.*;

/**
 * Don't forget to fill in moons!
 * @author foodisgoodyesiam
 *
 */
public abstract class POPPlanet extends POPWorld implements IPlanet {
	public IMoon[] moons = null;
	
	public POPPlanet() {
		super();
	}
	
	/**
	 * Multiplies the solar multiplier for this planet by a special provided factor
	 * @param solarSpecialFactor the special multiplier for this planet
	 */
	protected POPPlanet(double solarSpecialFactor) {
		solarMultiplier = 1;
		specialMultiplier = solarSpecialFactor;
	}
	
	/**
	 * Inits solar multiplier for this planet. Note that this can be called more than once if conditions change for some reason.
	 * @param specialFactor The special multiplier for this planet.
	 */
	@Override
	public void initSolar(double specialFactor) {
		if (getParentGalaxy() instanceof POPGalaxy) {
			POPGalaxy galaxy = (POPGalaxy) getParentGalaxy();
			if (galaxy.suns.length == 0)
				solarMultiplier = .01;
			else {
				solarMultiplier = 0;
				for (POPStar star : galaxy.suns)
					solarMultiplier+=(star.getBrightness());
				solarMultiplier*=(this.getDistanceFromCenter()*this.getDistanceFromCenter());
			}
			solarMultiplier*=specialFactor;
		} else {
			solarMultiplier = specialFactor;
		}
		//TODO: Add section here accounting for atmosphere type
	}
	/* Interesting functions to implement:
	 * isBlockHighHumidity
	 * canDoLightning
	 * canBlockFreeze
	 * isSkyColored
	 * canDoRainSnowIce
	 * getFuelUsageMultiplier
	 * canSpaceshipTierPass
	 * canMineBlock
	 * canSnowHere
	 * doesXZShowFog
	 * getFallDamageMultiplier
	 * canRespawnHere
	 * drawClouds?
	 * getMeteorFrequency
	 * setCloudRenderer
	 * 
	 */
	
	@Override
	public float getStretchValue() {
		return 1F;
		//TODO: Calculate this based on stars sizes and distance... (perhaps eventually implement mass of stars, as well? and possibly of planets)
	}
}
