package com.ap.component.items;

import com.ap.model.Crafting;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class CraftingCmp implements Component {
    public final static ComponentMapper<CraftingCmp> mapper = ComponentMapper.getFor(CraftingCmp.class);
    public Crafting craft;

    public CraftingCmp(Crafting craft) {
        this.craft = craft;
    }
}
