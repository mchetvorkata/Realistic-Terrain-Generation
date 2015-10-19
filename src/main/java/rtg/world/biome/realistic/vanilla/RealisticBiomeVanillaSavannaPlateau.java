package rtg.world.biome.realistic.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import rtg.config.ConfigRTG;
import rtg.world.biome.BiomeBase;
import rtg.world.gen.surface.vanilla.SurfaceVanillaSavannaPlateau;
import rtg.world.gen.terrain.vanilla.TerrainVanillaSavannaPlateau;

public class RealisticBiomeVanillaSavannaPlateau extends RealisticBiomeVanillaBase
{	
	public static Block topBlock = BiomeGenBase.savannaPlateau.topBlock;
	public static Block fillerBlock = BiomeGenBase.savannaPlateau.fillerBlock;
	
	public RealisticBiomeVanillaSavannaPlateau()
	{
		super(
			BiomeGenBase.savannaPlateau,
			BiomeBase.climatizedBiome(BiomeGenBase.river, Climate.HOT),
			new TerrainVanillaSavannaPlateau(0f, 77f, 63f, 280f),
			new SurfaceVanillaSavannaPlateau(topBlock, fillerBlock, 300f, true, false)
		);
		
		this.setRealisticBiomeName("Vanilla Savanna Plateau");
		this.biomeCategory = BiomeCategory.HOT;
		this.biomeWeight = ConfigRTG.weightVanillaSavannaPlateau;
	}	
}