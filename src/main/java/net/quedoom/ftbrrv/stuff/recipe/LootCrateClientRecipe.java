package net.quedoom.ftbrrv.stuff.recipe;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewScreen;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LootCrateClientRecipe implements ReliableClientRecipe {
    private final WrappedLootCrate wlc;
    private final Identifier id;
    private final SlotContent crate;
    private final List<SlotContent> outs = new ArrayList<>();

    public LootCrateClientRecipe(WrappedLootCrate wlc) {
        this.wlc = wlc;
        this.id = Identifier.fromNamespaceAndPath(FTBQuestsAPI.MOD_ID, "/loot_crate/" + wlc.crate.getStringID());
        ItemStack cat = wlc.crateStack.copy();
        cat.set(ModDataComponents.LOOT_CRATE.get(), wlc.crate.getStringID());
        this.crate = SlotContent.of(cat);
        wlc.cycledOutputs().forEach(list -> outs.add(SlotContent.of(list)));
    }

    public ReliableClientRecipeType getType() { return LootCrateRecipeType.INSTANCE; }

    public Identifier getId() { return id; }

    public void bindSlots(RecipeViewMenu.SlotFillContext ctx) {
        ctx.bindSlot(0, crate);
        for (int i = 0; i < outs.size(); i++) {
            ctx.bindSlot(1 + i, outs.get(i));
            ctx.addAdditionalStackModifier(1 + i, (stack, tooltip) -> {
                // look the displayed stack up by value, so cycled stacks get a chance line too
                for (int j = 0; j < wlc.outputs.size(); j++) {
                    if (ItemStack.isSameItemSameComponents(stack, wlc.outputs.get(j))) {
                        String chance = ChatFormatting.GOLD + WeightedReward.chanceString(
                                wlc.sortedRewards.get(j).getWeight(), wlc.crate.getTable().getTotalWeight(true));
                        tooltip.add(Component.translatable("jei.ftbquests.lootcrates.chance", chance)
                                .withStyle(ChatFormatting.GRAY));
                        return;
                    }
                }
            });
        }
    }

    public List<SlotContent> getIngredients() { return List.of(crate); }
    public List<SlotContent> getResults() { return outs; }

    @Override
    public void renderRecipe(RecipeViewScreen screen, RecipePosition pos, GuiGraphicsExtractor g, int mx, int my, float pt) {
        LootCrateTextRenderer.drawText(g, wlc.crate, 0, WrappedLootCrate.ITEMSX * 18);
    }
}