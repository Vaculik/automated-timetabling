package cz.muni.fi.model.structural;

import cz.muni.fi.model.structural.dual.DualGraph;
import cz.muni.fi.model.structural.primal.PrimalGraph;

/**
 * Created by vacullik on 22/02/2017.
 */
public class StructuralModel {
    private PrimalGraph primalGraph;
    private DualGraph dualGraph;

    public StructuralModel(PrimalGraph primalGraph, DualGraph dualGraph) {
        if (primalGraph == null) {
            throw new NullPointerException("Parameter primalGraph is null.");
        }
        if (dualGraph == null) {
            throw new NullPointerException("Parameter dualGraph is null.");
        }
        this.primalGraph = primalGraph;
        this.dualGraph = dualGraph;
    }

    public PrimalGraph getPrimalGraph() {
        return primalGraph;
    }

    public DualGraph getDualGraph() {
        return dualGraph;
    }
}
