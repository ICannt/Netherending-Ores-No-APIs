package org.icannt.netherendingores.common.entity;

import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * Created by ICannt on 09/02/18.
 */
public class EntityNetherfish extends EntitySilverfish {

	private EntityNetherfish.AISummonNetherfish summonNetherfish;
	
	public EntityNetherfish(World worldIn) {
		super(worldIn);
	}

	@Override
		protected void initEntityAI() {
			super.initEntityAI();
	        this.summonNetherfish = new EntityNetherfish.AISummonNetherfish(this);
	        this.tasks.addTask(1, new EntityAISwimming(this));
	        this.tasks.addTask(3, this.summonNetherfish);
	        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
	        //this.tasks.addTask(5, new EntitySilverfish.AIHideInStone(this));
	        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
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
	
	static class AISummonNetherfish extends EntityAIBase {

		public AISummonNetherfish(EntityNetherfish entityNetherfish) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean shouldExecute() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
