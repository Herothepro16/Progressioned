package net.Herothepro.progressioned.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Herothepro.progressioned.Progressioned;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Comparator;
import java.util.Optional;

public final class RecipeResult {
    private final ItemStack fixed;
    private final TagKey<Item> tag;
    private final int count;
    private ItemStack cached = ItemStack.EMPTY;

    private RecipeResult(ItemStack fixed, TagKey<Item> tag, int count) {
        this.fixed = fixed;
        this.tag = tag;
        this.count = count;
    }

    public static RecipeResult ofStack(ItemStack stack) {
        return new RecipeResult(stack, null, stack.getCount());
    }

    public static RecipeResult ofTag(TagKey<Item> tag, int count) {
        return new RecipeResult(ItemStack.EMPTY, tag, count);
    }

    public boolean isTag() {
        return tag != null;
    }

    public TagKey<Item> getTag() {
        return tag;
    }

    public ItemStack resolve() {
        if (tag == null) return fixed;
        if (!cached.isEmpty()) return cached;

        Optional<ItemStack> result = BuiltInRegistries.ITEM.getTag(tag).flatMap(set ->
                set.stream()
                        .filter(h -> h.value() != Items.AIR)
                        .min(Comparator
                                .comparingInt((Holder<Item> h) -> namespacePriority(h))
                                .thenComparing(h -> h.unwrapKey().map(k -> k.location().toString()).orElse("")))
                        .map(h -> new ItemStack(h.value(), count)));

        result.ifPresent(s -> cached = s);
        return result.orElse(ItemStack.EMPTY);
    }

    private static int namespacePriority(Holder<Item> holder) {
        String ns = holder.unwrapKey().map(k -> k.location().getNamespace()).orElse("");
        if (ns.equals("minecraft")) return 0;
        if (ns.equals(Progressioned.MOD_ID)) return 1;
        return 2;
    }

    private static final Codec<RecipeResult> TAG_CODEC = RecordCodecBuilder.create(i -> i.group(
            TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(r -> r.tag),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(r -> r.count)
    ).apply(i, RecipeResult::ofTag));

    public static final Codec<RecipeResult> CODEC = Codec.either(ItemStack.CODEC, TAG_CODEC).xmap(
            either -> either.map(RecipeResult::ofStack, r -> r),
            r -> r.tag == null ? Either.left(r.fixed) : Either.right(r)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, RecipeResult> STREAM_CODEC = StreamCodec.of(
            (buf, r) -> {
                buf.writeBoolean(r.tag != null);
                if (r.tag != null) {
                    buf.writeResourceLocation(r.tag.location());
                    buf.writeVarInt(r.count);
                } else {
                    ItemStack.STREAM_CODEC.encode(buf, r.fixed);
                }
            },
            buf -> {
                if (buf.readBoolean()) {
                    ResourceLocation id = buf.readResourceLocation();
                    int count = buf.readVarInt();
                    return ofTag(TagKey.create(Registries.ITEM, id), count);
                }
                return ofStack(ItemStack.STREAM_CODEC.decode(buf));
            }
    );
}