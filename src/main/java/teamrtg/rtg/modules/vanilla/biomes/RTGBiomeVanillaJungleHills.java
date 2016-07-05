package teamrtg.rtg.modules.vanilla.biomes;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import teamrtg.rtg.api.config.BiomeConfig;
import teamrtg.rtg.api.tools.deco.DecoBoulder;
import teamrtg.rtg.api.tools.deco.DecoFallenTree;
import teamrtg.rtg.api.tools.deco.DecoFallenTree.LogCondition;
import teamrtg.rtg.api.tools.deco.DecoFlowersRTG;
import teamrtg.rtg.api.tools.deco.DecoGrass;
import teamrtg.rtg.api.tools.deco.DecoJungleCacti;
import teamrtg.rtg.api.tools.deco.DecoJungleGrassVines;
import teamrtg.rtg.api.tools.deco.DecoJungleLilypadVines;
import teamrtg.rtg.api.tools.deco.DecoTree;
import teamrtg.rtg.api.tools.deco.DecoTree.TreeCondition;
import teamrtg.rtg.api.tools.deco.DecoTree.TreeType;
import teamrtg.rtg.api.tools.deco.helper.DecoHelperThisOrThat;
import teamrtg.rtg.api.tools.deco.helper.DecoHelperThisOrThat.ChanceType;
import teamrtg.rtg.api.tools.feature.tree.rtg.TreeRTG;
import teamrtg.rtg.api.tools.feature.tree.rtg.TreeRTGCocosNucifera;
import teamrtg.rtg.api.tools.feature.tree.rtg.TreeRTGRhizophoraMucronata;
import teamrtg.rtg.api.tools.feature.tree.vanilla.WorldGenMegaJungleRTG;
import teamrtg.rtg.api.world.RTGWorld;
import teamrtg.rtg.api.world.biome.TerrainBase;
import teamrtg.rtg.api.world.biome.deco.DecoBaseBiomeDecorations;
import teamrtg.rtg.api.world.biome.surface.part.CliffSelector;
import teamrtg.rtg.api.world.biome.surface.part.SurfacePart;
import teamrtg.rtg.modules.vanilla.RTGBiomeVanilla;

public class RTGBiomeVanillaJungleHills extends RTGBiomeVanilla {

    public RTGBiomeVanillaJungleHills() {

        super(
                Biomes.JUNGLE_HILLS,
            Biomes.RIVER
        );
        this.noLakes = true;
    }

    @Override
    public TerrainBase initTerrain() {
        return new TerrainBase() {
            @Override
            public float generateNoise(RTGWorld rtgWorld, int x, int y, float biomeWeight, float border, float river) {
                return terrainHighland(x, y, rtgWorld.simplex, rtgWorld.cell, river, 10f, 68f, 55f, 10f);
            }
        };
    }

    @Override
    public SurfacePart initSurface() {
        SurfacePart surface = new SurfacePart();
        surface.add(new CliffSelector(1.5f)
            .add(PARTS.selectTopAndFill()
                .add(this.PARTS.SHADOW_STONE)));
        surface.add(new CliffSelector((x, y, z, rtgWorld) -> 1.5f - ((y - 60f) / 65f) + rtgWorld.simplex.noise3(x / 8f, y / 8f, z / 8f) * 0.5f)
            .add(PARTS.selectTop()
                .add(PARTS.STONE_OR_COBBLE)))
            .add(PARTS.selectFill()
                .add(PARTS.STONE));
        surface.add(PARTS.surfaceGeneric());
        return surface;
    }

    @Override
    public void initDecos() {
		// Blend of the WorldGenMegaJungle collection and some tall RTG Mangrove trees.

		TreeRTG mucronataTree = new TreeRTGRhizophoraMucronata(4, 5, 13f, 0.32f, 0.2f);
		mucronataTree.logBlock = Blocks.LOG.getStateFromMeta(3);
		mucronataTree.leavesBlock = Blocks.LEAVES.getStateFromMeta(3);
		mucronataTree.minTrunkSize = 3;
		mucronataTree.maxTrunkSize = 4;
		mucronataTree.minCrownSize = 10;
		mucronataTree.maxCrownSize = 27;
		this.addTree(mucronataTree);
        
		DecoTree mangroves = new DecoTree(mucronataTree);
		mangroves.loops = 3;
		mangroves.treeType = TreeType.RTG_TREE;
		mangroves.treeCondition = TreeCondition.RANDOM_CHANCE;
		mangroves.treeConditionChance = 2;
		mangroves.maxY = 160;
		
		DecoTree megaJungle = new DecoTree(new WorldGenMegaJungleRTG(false, 10, 27, 19, 20, Blocks.LOG.getStateFromMeta(3), Blocks.LEAVES.getStateFromMeta(3)));
		megaJungle.logBlock = Blocks.LOG.getStateFromMeta(3);
		megaJungle.leavesBlock = Blocks.LEAVES.getStateFromMeta(3);
		megaJungle.minTrunkSize = 3;
		megaJungle.maxTrunkSize = 4;
		megaJungle.minCrownSize = 10;
		megaJungle.maxCrownSize = 27;
		megaJungle.loops = 3;
		megaJungle.treeType = TreeType.WORLDGEN;
		megaJungle.treeCondition = TreeCondition.RANDOM_CHANCE;
		megaJungle.treeConditionChance = 2;
		megaJungle.maxY = 160;
		
		DecoHelperThisOrThat decoHelperThisOrThat = new DecoHelperThisOrThat(3, ChanceType.NOT_EQUALS_ZERO, megaJungle, mangroves);
		this.addDeco(decoHelperThisOrThat);
		
		// Add some palm trees for variety.
		
		TreeRTG nuciferaTree = new TreeRTGCocosNucifera();
		nuciferaTree.minTrunkSize = 7;
		nuciferaTree.maxTrunkSize = 9;
		nuciferaTree.minCrownSize = 6;
		nuciferaTree.maxCrownSize = 8;
		this.addTree(nuciferaTree);
		
		DecoTree palmCustom = new DecoTree(nuciferaTree);
		palmCustom.loops = 1;
		palmCustom.treeType = TreeType.RTG_TREE;
		palmCustom.treeCondition = TreeCondition.RANDOM_CHANCE;
		palmCustom.treeConditionChance = 3;
		palmCustom.maxY = 160;
		this.addDeco(palmCustom);
		
		// Another pass of the WorldGenMegaJungle collection for extra jungleness.
		this.addDeco(decoHelperThisOrThat);
		
		// Jungle logs.
		DecoFallenTree decoFallenTree = new DecoFallenTree();
		decoFallenTree.loops = 1;
		decoFallenTree.distribution.noiseDivisor = 100f;
		decoFallenTree.distribution.noiseFactor = 5f;
		decoFallenTree.distribution.noiseAddend = 0.8f;
		decoFallenTree.logCondition = LogCondition.NOISE_GREATER_AND_RANDOM_CHANCE;
		decoFallenTree.logConditionNoise = 0f;
		decoFallenTree.logConditionChance = 3;
		decoFallenTree.logBlock = Blocks.LOG;
		decoFallenTree.logMeta = (byte)3;
		decoFallenTree.leavesBlock = Blocks.LEAVES;
		decoFallenTree.leavesMeta = (byte)-1;
		decoFallenTree.minSize = 4;
		decoFallenTree.maxSize = 9;
		this.addDeco(decoFallenTree);
		
		// At this point, let's hand over some of the decoration to the base biome, but only about 85% of the time.
		DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
		decoBaseBiomeDecorations.notEqualsZeroChance = 6;
		decoBaseBiomeDecorations.loops = 1;
		this.addDeco(decoBaseBiomeDecorations);
		
		// A combo-deal of lilypads and vines. (This could probably be pulled out into individual decos.)
		DecoJungleLilypadVines decoJungleLilypadVines = new DecoJungleLilypadVines();
		this.addDeco(decoJungleLilypadVines);
		
		// A combo-deal of grass and vines. (This could probably be pulled out into individual decos.)
		DecoJungleGrassVines decoJungleGrassVines = new DecoJungleGrassVines();
		this.addDeco(decoJungleLilypadVines);
		
		// Flowers.
		DecoFlowersRTG decoFlowersRTG = new DecoFlowersRTG();
        decoFlowersRTG.flowers = new int[]{5}; // Only orange tulips fit in with the colour scheme.
        decoFlowersRTG.chance = 4;
        decoFlowersRTG.maxY = 120;
        decoFlowersRTG.strengthFactor = 2f;
        this.addDeco(decoFlowersRTG);
        
        // Tall cacti on red sand - matches the colour scheme nicely.
        DecoJungleCacti decoJungleCacti = new DecoJungleCacti();
		decoJungleCacti.strengthFactor = 8f;
		decoJungleCacti.maxY = 120;
		decoJungleCacti.sandOnly = false;
		decoJungleCacti.extraHeight = 7;
		decoJungleCacti.sandMeta = (byte)1;
		this.addDeco(decoJungleCacti);
		
        // Mossy boulders for the green.
		DecoBoulder decoBoulder = new DecoBoulder();
		decoBoulder.boulderBlock = Blocks.MOSSY_COBBLESTONE.getDefaultState();
		decoBoulder.chance = 16;
		decoBoulder.maxY = 95;
		decoBoulder.strengthFactor = 2f;
		this.addDeco(decoBoulder);
		
		// Grass filler.
		DecoGrass decoGrass = new DecoGrass();
		decoGrass.maxY = 128;
		decoGrass.strengthFactor = 12f;
        this.addDeco(decoGrass);
    }

    @Override
    public void initConfig() {
        config.addBlock(config.BEACH_BLOCK).setDefault(Blocks.SAND.getDefaultState());
        this.config.SCATTERED_FEATURE.setDefault(BiomeConfig.FeatureType.JUNGLE_TEMPLE.name());
        this.config.WATER_POND_CHANCE.setDefault(3);
    }
}
