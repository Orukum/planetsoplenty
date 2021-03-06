package foodisgood_orukum.mods.pop;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class POPExtendedPlayer implements IExtendedEntityProperties {
	public static final String EXT_PROP_NAME = "POP Extended Player Tags";
	
	public final EntityPlayer player;
	
	public int increment;
	public boolean foodisgoodRegistered;
	//Store info here about what planets this player has discovered, etc
	
	public POPExtendedPlayer(EntityPlayer playerArg) {
		this.player = playerArg;
		if (!playerArg.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).hasKey(EXT_PROP_NAME) || playerArg.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(EXT_PROP_NAME)==null || playerArg.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(EXT_PROP_NAME).hasNoTags()) {
			//Initialize other variables here
			increment = 0;
			foodisgoodRegistered = false;
		} else
			loadNBTData(playerArg.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(EXT_PROP_NAME));
	}
	
	public static final void register(EntityPlayer playerArg) {
		playerArg.registerExtendedProperties(EXT_PROP_NAME, new POPExtendedPlayer(playerArg));
	}
	
	public static final POPExtendedPlayer get(EntityPlayer player) {
		if (player.getExtendedProperties(EXT_PROP_NAME)==null) {
			register(player);
			POPLog.severe("[Planets O Plenty] Player " + player.username + " failed to have extended properties registered when joining server? Registering now in get()", player);
		}
		return (POPExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		increment = compound.getInteger("DEBUG_Increment");
		if (player.username=="foodisgoodyesiam")
			foodisgoodRegistered = compound.getBoolean("FOODISGOOD_registered");
		else
			foodisgoodRegistered = false;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setInteger("DEBUG_Increment", increment);
		if (player.username=="foodisgoodyesiam")
			compound.setBoolean("FOODISGOOD_registered", foodisgoodRegistered);
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub
	}
}
