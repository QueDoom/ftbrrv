package net.quedoom.ftbrrv.stuff.recipe;

import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.quedoom.ftbrrv.FTBRRV;
import net.quedoom.quet.api.rrv.ReliableClientRecipeTypeHelper;

import java.util.List;

public class LootCrateClientRecipesType extends ReliableClientRecipeTypeHelper {
    protected LootCrateClientRecipesType(Component displayName, String name, List<ItemStack> displayAndWorkstation, int width, int height) {
        super(displayName, name, displayAndWorkstation, width, height);
    }

    @Override
    protected String namespace() {
        return FTBRRV.MOD_ID;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {

    }
}
