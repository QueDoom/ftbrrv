package net.quedoom.ftbrrv.stuff;

import cc.cassian.rrv.api.recipe.ItemView;
import dev.ftb.mods.ftbquests.integration.RecipeModHelper;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;

public class RRVRecipeHelper implements RecipeModHelper {
    public static final WrappedQuestCache QUESTS = new WrappedQuestCache();
    public static final WrappedLootCrateCache LOOT_CRATES = new WrappedLootCrateCache(
            removed -> removed.forEach(ItemView::excludeItem),        // TODO(verify): takes ItemStack or Item?
            added   -> { /* handled by the reload callback in the plugin */ });

    public String getHelperName() { return "RRV"; }
    protected void refreshQuests() { QUESTS.clear(); }
    protected void refreshLootcrates() { LOOT_CRATES.refresh(); }

    @Override
    public void refreshAll(Components component) {
        switch (component) {
            case QUESTS -> refreshQuests();
            case LOOT_CRATES -> refreshLootcrates();
        }
    }

    @Override
    public void refreshRecipes(QuestObjectBase object) {
        object.componentsToRefresh().forEach(this::refreshAll);
    }

    @Override
    public boolean isRecipeModAvailable() {
        return true;
    }
}
