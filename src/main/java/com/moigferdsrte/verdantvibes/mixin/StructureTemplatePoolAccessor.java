package com.moigferdsrte.verdantvibes.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StructureTemplatePool.class)
public interface StructureTemplatePoolAccessor {
    @Accessor("templates")
    ObjectArrayList<StructurePoolElement> verdantVibes$getTemplates();

    @Accessor("rawTemplates")
    List<Pair<StructurePoolElement, Integer>> verdantVibes$getRawTemplates();

    @Mutable
    @Accessor("rawTemplates")
    void verdantVibes$setRawTemplates(List<Pair<StructurePoolElement, Integer>> rawTemplates);
}
