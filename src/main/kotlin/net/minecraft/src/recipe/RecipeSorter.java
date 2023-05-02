package net.minecraft.src.recipe;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.crafting.CraftingManager;
import net.minecraft.src.crafting.CraftingRecipe;

import java.util.Comparator;

public class RecipeSorter
    implements Comparator
{

    public RecipeSorter(CraftingManager craftingmanager)
    {
        craftingManager = craftingmanager;
    }

    public int compareRecipes(CraftingRecipe craftingrecipe, CraftingRecipe craftingrecipe1)
    {
        if(craftingrecipe1.getRecipeSize() < craftingrecipe.getRecipeSize())
        {
            return -1;
        }
        return craftingrecipe1.getRecipeSize() <= craftingrecipe.getRecipeSize() ? 0 : 1;
    }

    public int compare(Object obj, Object obj1)
    {
        return compareRecipes((CraftingRecipe)obj, (CraftingRecipe)obj1);
    }

    final CraftingManager craftingManager; /* synthetic field */
}
