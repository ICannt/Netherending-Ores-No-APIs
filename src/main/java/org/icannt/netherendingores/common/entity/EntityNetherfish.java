package org.icannt.netherendingores.common.entity;

import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * Created by ICannt on 09/02/18.
 */
public class EntityNetherfish extends EntitySilverfish {

	public EntityNetherfish(World worldIn) {
		super(worldIn);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return super.getAmbientSound();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return super.getHurtSound(damageSourceIn);
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return super.getDeathSound();
	}
}
