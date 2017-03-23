package cz.muni.fi.model.perday;

import java.util.Comparator;


public class CoordinatesInTimeCompBySequence implements Comparator<CoordinatesInTime> {

    @Override
    public int compare(CoordinatesInTime o1, CoordinatesInTime o2) {
        return o1.getSequence() - o2.getSequence();
    }
}
