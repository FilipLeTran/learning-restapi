package com.infrasight.kodtest;

import java.util.*;

public class MapSortTotalManaged  {


    public MapSortTotalManaged() {}

    public Map<String, List<String>> sort(Set<Map.Entry<String, List<String>>> entries) {
        Comparator<Map.Entry<String, List<String>>> valueComparator = (o1, o2) -> {
            if (o1.getValue().size() == o2.getValue().size()) {
                return 0;
            } else if (o1.getValue().size() < o2.getValue().size()) {
                return 1;
            } else {
                return -1;
            }
        };
        List<Map.Entry<String, List<String>>> listOfEntries = new ArrayList<>(entries);
        listOfEntries.sort(valueComparator);
        LinkedHashMap<String, List<String>> sortedByValues = new LinkedHashMap<>(listOfEntries.size());
        for(Map.Entry<String, List<String>> entry : listOfEntries) {
            sortedByValues.put(entry.getKey(), entry.getValue());
        }
        return sortedByValues;
    }

}
