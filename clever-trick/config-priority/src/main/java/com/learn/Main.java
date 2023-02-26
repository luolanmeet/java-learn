package com.learn;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        ConfigPriorityManager<InventoryConfig> manager
                = new ConfigPriorityManager<>("geoCode", "plantCode", "locationCode", "attributeCode");

        InventoryConfig c0 = new InventoryConfig();
        c0.setAttributeCode("wh1");

        InventoryConfig c1 = new InventoryConfig();
        c1.setGeoCode("CHN");

        InventoryConfig c2 = new InventoryConfig();
        c2.setGeoCode("NA");

        InventoryConfig c3 = new InventoryConfig();
        c3.setGeoCode("CHN");
        c3.setLocationCode("U001");

        InventoryConfig c4 = new InventoryConfig();
        c4.setGeoCode("CHN");
        c4.setPlantCode("GZ001");
        c4.setLocationCode("D001");
        c4.setAttributeCode("common");

        Map<Integer, List<InventoryConfig>> sortedConfig
                = manager.add(c0).add(c1).add(c2).add(c3).add(c4).getSortedConfig();

        for (Map.Entry<Integer, List<InventoryConfig>> entry : sortedConfig.entrySet()) {

            System.out.println(String.format("========%d========", entry.getKey()));
            for (InventoryConfig config : entry.getValue()) {
                System.out.println(config);
            }

            System.out.println();
        }


    }
}