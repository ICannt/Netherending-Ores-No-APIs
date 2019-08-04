package org.icannt.netherendingores.lib;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.icannt.netherendingores.lib.MathUtil.minMax;

import org.icannt.netherendingores.common.registry.BlockRecipeData;
import org.icannt.netherendingores.common.registry.OreDictionaryOtherData;
import org.icannt.netherendingores.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by ICannt on 25/03/18.
 */

// TODO: Go through and filter everything to be within min/max ranges. Can't get this to work :/
public class Config {
	
	public static Boolean advancedDebugging = false;
	
	public static Boolean addCrystalChargedCertusQuartz = true;
	public static Boolean addOreDilithium = false;
	public static Boolean addOreTritanium = false;

	public static Boolean inductionSmelterFullOutput = false;
	public static float inductionSmelterFullOutputAmountFactor = 1f;
	public static float inductionSmelterFullOutputEnergyFactor = 2f;
	public static float inductionSmelterReducedOutputAmountFactor = 2/3f;
	public static float inductionSmelterReducedOutputEnergyFactor = 0.6f;
	
	public static Boolean pulverizerFullOutput = false;
	public static float pulverizerFullOutputAmountFactor = 1f;
	public static float pulverizerFullOutputEnergyFactor = 2f;
	public static float pulverizerReducedOutputAmountFactor = 2/3f;
	public static float pulverizerReducedOutputEnergyFactor = 0.6f;
	
	public static Boolean redstoneFurnaceFullOutput = false;
	public static float redstoneFurnaceFullOutputAmountFactor = 1f;
	public static float redstoneFurnaceFullOutputEnergyFactor = 2f;
	public static float redstoneFurnaceReducedOutputAmountFactor = 2/3f;
	public static float redstoneFurnaceReducedOutputEnergyFactor = 0.6f;
	
	public static Boolean immersiveEngineeringRecipes = true;
	public static Boolean industrialCraft2Recipes = true;
	public static Boolean mekanismRecipes = true;
	public static Boolean thermalExpansionRecipes = true;
	public static Boolean tinkersConstructRecipes = true;
	public static Boolean vanillaCraftingRecipes = true;
	public static Boolean vanillaFurnaceRecipes = true;
		
	private static int override = -1;
	private static int minMult = 0;
	private static int maxMult = 3;
	
    public static int netherfishEntityId = 667;
    public static boolean netherfishEnable = true;
    public static double netherfishAttackDamage = 1.0D;
    public static double netherfishKnockbackResistance = 1.0D;
    public static double netherfishMaxHealth = 10.0D;
    public static double netherfishMaxSpeed = 0.33D;
    public static boolean netherfishSetFire = true;
    public static boolean netherfishWAILA = true;
    
    public static boolean zombiePigmanAnger = true;
    public static int zombiePigmanAngerRangeRadius = 32;
    public static int zombiePigmanAngerRangeRadiusMin = 1;
    public static int zombiePigmanAngerRangeRadiusMax = 64;
    public static int zombiePigmanAngerRangeHeight = 16;
    public static int zombiePigmanAngerRangeHeightMin = 1;
    public static int zombiePigmanAngerRangeHeightMax = 32;
    public static boolean zombiePigmanAngerSilktouch = true;

    public static boolean oreExplosionEnable = true;
    public static double oreExplosionChance = 0.125D;
    public static double oreExplosionStrength = 4.0D;
    public static boolean oreExplosionFortune = true;
    public static boolean oreExplosionSilktouch = true;
	
	private static final String CATEGORY_GENERAL_SETTINGS = "general settings";
	private static final String CATEGORY_ORE_DICT_SETTINGS = "ore dictionary settings";
	private static final String CATEGORY_MACHINE_RECIPE_SETTINGS = "machine recipe settings";
	private static final String CATEGORY_RECIPE_INTEGRATION_SETTINGS = "recipe integration settings";
	private static final String CATEGORY_RECIPE_MULTIPLIER_OVERRIDE = "recipe multipliers override";
	private static final String CATEGORY_RECIPE_MULTIPLIER = "recipe multipliers";
	private static final String CATEGORY_MOB_NETHERRFISH = "mob netherfish settings";
	private static final String CATEGORY_MOB_ZOMBIE_PIGMAN = "mob zombie pigman settings";

	    
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            // Load order is different so the override will load first, the forge config sorter will change the position anyway.
            initGeneralSettingsConfig(cfg);
            initOreDictSettingsConfig(cfg);
            initRecipeIntegrationSettingsConfig(cfg);
            initMachineRecipeSettingsConfig(cfg);
            initRecipeMultiplierOverrideConfig(cfg);
            initRecipeMultiplierConfig(cfg);
            initNetherfishSettingsConfig(cfg);
            initZombiePigmanSettingsConfig(cfg);
        } catch (Exception e1) {
            Log.LOG.error("Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }
    
    //
    private static void initGeneralSettingsConfig(Configuration cfg) {
    	
    	cfg.addCustomCategoryComment(CATEGORY_GENERAL_SETTINGS, "General Settings");

    	advancedDebugging = cfg.getBoolean("Advanced debugging", CATEGORY_GENERAL_SETTINGS, advancedDebugging, "Enable advanced debugging. Show all trace level messages in debug.log. Only enable if you really need it.");
    	
    }

    //
    private static void initOreDictSettingsConfig(Configuration cfg) {
    	
    	cfg.addCustomCategoryComment(CATEGORY_ORE_DICT_SETTINGS, "Ore Dictionary Settings");
    	    	
    	boolean enabled = false;    	
    	for (OreDictionaryOtherData oD : OreDictionaryOtherData.values()) {
    		enabled = cfg.getBoolean(oD.getName(), CATEGORY_ORE_DICT_SETTINGS, oD.getDefaultSetting(), "Add " + oD.getModItemDescName() + " from " + oD.getModDescName() + " to the Ore Dictionary." + oD.getConfigExtraDesc());
    		oD.setEnabled(enabled);
		}
    	
    }
    
    //
    private static void initMachineRecipeSettingsConfig(Configuration cfg) {
    	
    	cfg.addCustomCategoryComment(CATEGORY_MACHINE_RECIPE_SETTINGS, "" 
    			+ "Settings for Thermal Expansion machine processing\r\n"
    			+ "Please ask the mod author trab if you need assistance understanding how this works");
    	
    	final float minFactor = 0.5f;
    	final float maxFullFactor = 3;
    	final float maxReducedFactor = 1;
    	
    	inductionSmelterFullOutput = cfg.getBoolean("Induction Smelter full output", CATEGORY_MACHINE_RECIPE_SETTINGS, inductionSmelterFullOutput, "Enable full Induction Smelter output. Do not reduce output for augment compensation, uses much more energy.");
    	inductionSmelterFullOutputAmountFactor = cfg.getFloat("Induction Smelter full output amount factor", CATEGORY_MACHINE_RECIPE_SETTINGS, inductionSmelterFullOutputAmountFactor, minFactor, maxFullFactor, "Induction Smelter full output amount factor.");
    	inductionSmelterFullOutputEnergyFactor = cfg.getFloat("Induction Smelter full output energy factor", CATEGORY_MACHINE_RECIPE_SETTINGS, inductionSmelterFullOutputEnergyFactor, minFactor, maxFullFactor, "Induction Smelter full output energy factor.");    	
    	inductionSmelterReducedOutputAmountFactor = cfg.getFloat("Induction Smelter reduced output amount factor", CATEGORY_MACHINE_RECIPE_SETTINGS, inductionSmelterReducedOutputAmountFactor, minFactor, maxReducedFactor, "Induction Smelter reduced output amount factor.");
    	inductionSmelterReducedOutputEnergyFactor = cfg.getFloat("Induction Smelter reduced output energy factor", CATEGORY_MACHINE_RECIPE_SETTINGS, inductionSmelterReducedOutputEnergyFactor, minFactor, maxReducedFactor, "Induction Smelter reduced output energy factor.");
    	
    	pulverizerFullOutput = cfg.getBoolean("Pulverizer full output", CATEGORY_MACHINE_RECIPE_SETTINGS, pulverizerFullOutput, "Enable full Pulverizer output. Do not reduce output for augment compensation, uses much more energy.");
    	pulverizerFullOutputAmountFactor = cfg.getFloat("Pulverizer full output amount factor", CATEGORY_MACHINE_RECIPE_SETTINGS, pulverizerFullOutputAmountFactor, minFactor, maxFullFactor, "Pulverizer full output amount factor.");
    	pulverizerFullOutputEnergyFactor = cfg.getFloat("Pulverizer full output energy factor", CATEGORY_MACHINE_RECIPE_SETTINGS, pulverizerFullOutputEnergyFactor, minFactor, maxFullFactor, "Pulverizer full output energy factor.");    	
    	pulverizerReducedOutputAmountFactor = cfg.getFloat("Pulverizer reduced output amount factor", CATEGORY_MACHINE_RECIPE_SETTINGS, pulverizerReducedOutputAmountFactor, minFactor, maxReducedFactor, "Pulverizer reduced output amount factor.");
    	pulverizerReducedOutputEnergyFactor = cfg.getFloat("Pulverizer reduced output energy factor", CATEGORY_MACHINE_RECIPE_SETTINGS, pulverizerReducedOutputEnergyFactor, minFactor, maxReducedFactor, "Pulverizer reduced output energy factor.");
    	
    	redstoneFurnaceFullOutput = cfg.getBoolean("Redstone Furnace full output", CATEGORY_MACHINE_RECIPE_SETTINGS, redstoneFurnaceFullOutput, "Enable full Redstone Furnace output. Do not reduce output for augment compensation, uses much more energy.");
    	redstoneFurnaceFullOutputAmountFactor = cfg.getFloat("Redstone Furnace full output amount factor", CATEGORY_MACHINE_RECIPE_SETTINGS, redstoneFurnaceFullOutputAmountFactor, minFactor, maxFullFactor, "Redstone Furnace full output amount factor.");
    	redstoneFurnaceFullOutputEnergyFactor = cfg.getFloat("Redstone Furnace full output energy factor", CATEGORY_MACHINE_RECIPE_SETTINGS, redstoneFurnaceFullOutputEnergyFactor, minFactor, maxFullFactor, "Redstone Furnace full output energy factor.");    	
    	redstoneFurnaceReducedOutputAmountFactor = cfg.getFloat("Redstone Furnace reduced output amount factor", CATEGORY_MACHINE_RECIPE_SETTINGS, redstoneFurnaceReducedOutputAmountFactor, minFactor, maxReducedFactor, "Redstone Furnace reduced output amount factor.");
    	redstoneFurnaceReducedOutputEnergyFactor = cfg.getFloat("Redstone Furnace reduced output energy factor", CATEGORY_MACHINE_RECIPE_SETTINGS, redstoneFurnaceReducedOutputEnergyFactor, minFactor, maxReducedFactor, "Redstone Furnace reduced output energy factor.");
    	
    }

    //
    private static void initRecipeIntegrationSettingsConfig(Configuration cfg) {
    	
    	cfg.addCustomCategoryComment(CATEGORY_RECIPE_INTEGRATION_SETTINGS, "Enable or disable recipe integrations");
    	
    	immersiveEngineeringRecipes = cfg.getBoolean("Immersive Engineering recipes", CATEGORY_RECIPE_INTEGRATION_SETTINGS, immersiveEngineeringRecipes, "Enable Immersive Engineering recipe integration");
    	industrialCraft2Recipes = cfg.getBoolean("Industrial Craft 2 recipes", CATEGORY_RECIPE_INTEGRATION_SETTINGS, industrialCraft2Recipes, "Enable Industrial Craft 2 recipe integration");
    	mekanismRecipes = cfg.getBoolean("Mekanism recipes", CATEGORY_RECIPE_INTEGRATION_SETTINGS, mekanismRecipes, "Enable Mekanism recipe integration");
    	thermalExpansionRecipes = cfg.getBoolean("Thermal Expansion recipes", CATEGORY_RECIPE_INTEGRATION_SETTINGS, thermalExpansionRecipes, "Enable Thermal Expansion recipe integration");
    	tinkersConstructRecipes = cfg.getBoolean("Tinkers' Construct recipes", CATEGORY_RECIPE_INTEGRATION_SETTINGS, tinkersConstructRecipes, "Enable Tinkers' Construct recipe integration");
    	vanillaCraftingRecipes = cfg.getBoolean("Vanilla crafting recipes", CATEGORY_RECIPE_INTEGRATION_SETTINGS, vanillaCraftingRecipes, "Enable crafting recipes to convert to oredict ores, only works with 1x recipe multiplier");
    	vanillaFurnaceRecipes = cfg.getBoolean("Vanilla furnace recipes", CATEGORY_RECIPE_INTEGRATION_SETTINGS, vanillaFurnaceRecipes, "Enable furnace recipes to smelt to oredict ores.");
    	
    }

    //
    private static void initRecipeMultiplierConfig(Configuration cfg) {
    	
    	cfg.addCustomCategoryComment(CATEGORY_RECIPE_MULTIPLIER, ""
    			+ "0 = No recipes/standard oredict, ideal for craftweaker.\r\n"
    			+ "    Oredict entries prefixed with \"neo\" for easy craftteaker use.\r\n"
    			+ "1 = Oredict mode uses the same oredict name as the target ore, also adds recipe to craft target ore if needed.\r\n"
    			+ "2 = Crush to dust at 2x rate with mod specific bonuses | Smelt to 2x oredict ore.\r\n"
    			+ "    Oredict entries prefixed with \"oreEnd\", \"oreNether\" or \"oreOverworld\" respectively.\r\n"
    			+ "3 = Crush to 4x oredict ore | Smelt to 3x oredict ore.\r\n"
    			+ "    Oredict entries prefixed with \"oreDenseEnd\", \"oreDenseNether\" or \"oreDenseOverworld\" respectively.\r\n");
    	
    	int multiplier = 0;    	
    	for (BlockRecipeData blockData : BlockRecipeData.values()) {
    		multiplier = cfg.get(CATEGORY_RECIPE_MULTIPLIER, StringUtil.spaceCapital(blockData.getName()), blockData.getDefaultRecipeMultiplier()).getInt();
    		multiplier = minMax(minMult, maxMult, multiplier);
    		if (override > -1) multiplier = minMax(minMult, maxMult, override);
    		blockData.setRecipeMultiplier(multiplier);
		} 
    	
    }
    
    //
    private static void initRecipeMultiplierOverrideConfig(Configuration cfg) {
    	
    	override = cfg.getInt("Override Multipliers", CATEGORY_RECIPE_MULTIPLIER_OVERRIDE, -1, -1, maxMult, "Change this setting to override all recipe multipliers, -1 means ignore.");
    	override = minMax(-1, maxMult, override);
    	
    }
    
    //
    private static void initNetherfishSettingsConfig(Configuration cfg) {
    	
    	cfg.addCustomCategoryComment(CATEGORY_MOB_NETHERRFISH, "Netherfish settings");
    	
        netherfishEnable = cfg.getBoolean("Netherfish enable", CATEGORY_MOB_NETHERRFISH, netherfishEnable, "Enable Netherfish so the mob is active.");
        netherfishAttackDamage = cfg.get(CATEGORY_MOB_NETHERRFISH, "Netherfish attack damage", netherfishAttackDamage, "Netherfish attack damage multiplier").getDouble();
        netherfishKnockbackResistance = cfg.get(CATEGORY_MOB_NETHERRFISH, "Netherfish knockback resistance", netherfishKnockbackResistance, "Netherfish knockback resistance multiplier").getDouble();
        netherfishMaxHealth = cfg.get(CATEGORY_MOB_NETHERRFISH, "Netherfish maximum health", netherfishMaxHealth, "Netherfish maximum health in half hearts").getDouble();
        netherfishMaxSpeed = cfg.get(CATEGORY_MOB_NETHERRFISH, "Netherfish maximum speed", netherfishMaxSpeed, "Netherfish maximum speed multiplier").getDouble();
        netherfishSetFire = cfg.getBoolean("Netherfish set fire", CATEGORY_MOB_NETHERRFISH, netherfishSetFire, "Enables the Netherfish to set the player on fire during attack.");
        netherfishWAILA = cfg.getBoolean("Netherfish WAILA/HWYLA", CATEGORY_MOB_NETHERRFISH, netherfishWAILA, "Enables the Netherfish spawn blocks to be hidden from WAILA/HWYLA i.e. show as Netherrack.");
    	    	
    }
    
    private static void initZombiePigmanSettingsConfig(Configuration cfg) {
    	
    	cfg.addCustomCategoryComment(CATEGORY_MOB_ZOMBIE_PIGMAN, "Zombie Pigman settings");
    	
    	zombiePigmanAnger = cfg.getBoolean("Zombie Pigman anger", CATEGORY_MOB_ZOMBIE_PIGMAN, zombiePigmanAnger, "Enables the Zombie Pigman anger reaction to mining ores.");
    	zombiePigmanAngerRangeRadius = cfg.getInt("Zombie Pigman anger range radius", CATEGORY_MOB_ZOMBIE_PIGMAN, zombiePigmanAngerRangeRadiusMin, zombiePigmanAngerRangeRadiusMax, zombiePigmanAngerRangeRadius, "Zombie Pigman anger reaction range square radius in blocks around the player.");
    	zombiePigmanAngerRangeHeight = cfg.getInt("Zombie Pigman anger range height", CATEGORY_MOB_ZOMBIE_PIGMAN, zombiePigmanAngerRangeHeightMin, zombiePigmanAngerRangeHeightMax, zombiePigmanAngerRangeHeight, "Zombie Pigman anger reaction range height in blocks up and down of the player.");
    	zombiePigmanAngerSilktouch = cfg.getBoolean("Zombie Pigman Anger Silktouch", CATEGORY_MOB_ZOMBIE_PIGMAN, zombiePigmanAngerSilktouch, "If ores are mined with a silk touch enchantment Zombie pigmen won't react.");
    	
    }
    
    private static void initOreExplosionConfig(Configuration cfg) {
    	//TODO
    }

}
