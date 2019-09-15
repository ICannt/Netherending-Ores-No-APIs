package org.icannt.netherendingores.lib;

import org.icannt.netherendingores.NetherendingOres;
import org.icannt.netherendingores.common.entity.EntityNetherfish;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Created by ICannt on 09/02/18.
 */
public class EntityInit {

	public static void registerEntities() {
		if (Config.netherfish) registerEntity("netherfish", EntityNetherfish.class, Config.NETHERFISH_ENTITY_ID, 50, 12325908, 16761600);
	}
	
	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int colorP, int colorS) {
		EntityRegistry.registerModEntity(new ResourceLocation(Info.MOD_ID + ":" + name), entity, name, id, NetherendingOres.instance, range, 1, true, colorP, colorS);
	}
}
