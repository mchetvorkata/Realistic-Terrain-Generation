package teamrtg.rtg.modules.vanilla.biomes;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import teamrtg.rtg.api.tools.deco.DecoCactus;
import teamrtg.rtg.api.tools.deco.DecoDeadBush;
import teamrtg.rtg.api.tools.deco.DecoReed;
import teamrtg.rtg.api.tools.deco.DecoShrub;
import teamrtg.rtg.api.tools.deco.DecoTree;
import teamrtg.rtg.api.tools.deco.DecoTree.TreeCondition;
import teamrtg.rtg.api.tools.deco.DecoTree.TreeType;
import teamrtg.rtg.api.tools.feature.tree.vanilla.WorldGenTreesRTG;
import teamrtg.rtg.api.tools.surface.SurfaceRiverOasis;
import teamrtg.rtg.api.util.BiomeUtils;
import teamrtg.rtg.api.util.math.CanyonColour;
import teamrtg.rtg.api.world.RTGWorld;
import teamrtg.rtg.api.world.biome.TerrainBase;
import teamrtg.rtg.api.world.biome.surface.part.BlockPart;
import teamrtg.rtg.api.world.biome.surface.part.HeightSelector;
import teamrtg.rtg.api.world.biome.surface.part.SurfacePart;
import teamrtg.rtg.modules.vanilla.RTGBiomeVanilla;

public class RTGBiomeVanillaMesaPlateauM extends RTGBiomeVanilla {
    public static Biome standardBiome = Biomes.MESA_CLEAR_ROCK;
    public static Biome mutationBiome = Biome.getBiome(BiomeUtils.getId(standardBiome) + MUTATION_ADDEND);

    public RTGBiomeVanillaMesaPlateauM() {

        super(
                mutationBiome,
            Biomes.RIVER
        );
        this.noLakes = true;
    }

    @Override
    public TerrainBase initTerrain() {
        return new TerrainBase() {
            private float[] height = new float[] {18.5f, 0.4f};
            private int heightLength = height.length;
            private float strength = 20f;

            @Override
            public float generateNoise(RTGWorld rtgWorld, int x, int y, float biomeWeight, float border, float river) {
                river *= 0.5f;
                return terrainPlateau(x, y, rtgWorld.simplex, river, height, biomeWeight, border, strength, heightLength, 50f, true);
            }
        };
    }

    @Override
    public SurfacePart initSurface() {
        SurfacePart surface = new SurfacePart();
        surface.add(new SurfaceRiverOasis(this));
        surface.add(new HeightSelector(50, 255).setMinNoise(PARTS.DEPTH_NOISE)
            .add(new BlockPart(CanyonColour.MESA)));
        return surface;
    }


    @Override
    public void initDecos() {
        DecoShrub decoShrub = new DecoShrub();
        decoShrub.chance = 10;
        addDeco(decoShrub);

        DecoCactus decoCactus = new DecoCactus();
        decoCactus.strengthFactor = 25f;
        decoCactus.soilBlock = Blocks.SAND.getStateFromMeta(1);
        decoCactus.soilMeta = (byte)1;
        decoCactus.sandOnly = false;
        decoCactus.maxRiver = 0.8f;
        addDeco(decoCactus);

        DecoReed decoReed = new DecoReed();
        decoReed.loops = 5;
        decoReed.maxRiver = 0.8f;
        addDeco(decoReed);

        DecoDeadBush decoDeadBush = new DecoDeadBush();
        decoDeadBush.strengthFactor = 5f;
        addDeco(decoDeadBush);

        DecoTree decoTree = new DecoTree(new WorldGenTreesRTG());
        decoTree.loops = 20;
        decoTree.treeType = TreeType.WORLDGEN;
        decoTree.treeCondition = TreeCondition.X_DIVIDED_BY_STRENGTH;
        decoTree.distribution = new DecoTree.Distribution(24f, 1f, 0f);
        decoTree.treeConditionChance = 0;
        decoTree.treeConditionFloat = 4f;
        decoTree.treeConditionNoise = 0f;
        decoTree.minY = 74;
        addDeco(decoTree);
    }

    @Override
    public void initConfig() {

    }
}
