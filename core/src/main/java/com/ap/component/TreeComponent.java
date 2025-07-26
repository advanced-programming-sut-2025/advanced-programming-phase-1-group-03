package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class TreeComponent implements Component {
    public static final ComponentMapper<TreeComponent> mapper = ComponentMapper.getFor(TreeComponent.class);
    private boolean hasLeaf;
    private int numberOfAxeNeededToCut;

    public TreeComponent(boolean hasLeaf, int numberOfAxeNeededToCut) {
        this.hasLeaf = hasLeaf;
        this.numberOfAxeNeededToCut = numberOfAxeNeededToCut;
    }

    public void setNumberOfAxeNeededToCut(int numberOfAxeNeededToCut) {
        this.numberOfAxeNeededToCut = numberOfAxeNeededToCut;
    }

    public boolean hasLeaf() {
        return hasLeaf;
    }

    public void setHasLeaf(boolean hasLeaf) {
        this.hasLeaf = hasLeaf;
    }

    public int getNumberOfAxeNeededToCut() {
        return numberOfAxeNeededToCut;
    }
}
