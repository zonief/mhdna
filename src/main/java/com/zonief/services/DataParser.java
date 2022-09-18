package com.zonief.services;

import com.zonief.beans.MhObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataParser {

  public Map<String, MhObject> getMhObjects(){

    BufferedReader reader;
    Map<String,MhObject> mhObjects = new HashMap<>();
    try {
      reader = new BufferedReader(new FileReader("tmp/MyHeritage_raw_dna_data.csv"));
      String line = reader.readLine();
      while (line != null) {
        if(!line.contains("#")){
          var values = line.split(",");
          mhObjects.put(
              values[0].replace("\"","") + values[2].replace("\"","") + values[1].replace("\"",""),
              MhObject.builder()
              .rsId(values[0].replace("\"",""))
              .chromosome(values[1].replace("\"",""))
              .position(values[2].replace("\"",""))
              .result(values[3].replace("\"",""))
              .build());
        }
        // read next line
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return mhObjects;
  }

  public Map<String,MhObject> getClinvarObjects(){

    BufferedReader reader;
    Map<String,MhObject> clinvarObjects = new HashMap<>();
    Map<String, String> subValuesMap = new HashMap<>();
    try {
      reader = new BufferedReader(new FileReader("tmp/clinvar.vcf"));
      String line = reader.readLine();
      while (line != null) {
        if(!line.contains("#")){
          var values = line.split("\t");
          var subValues = values[7].split(";");
          for(String subValue:subValues){
            var keyVal = subValue.split("=");
            subValuesMap.put(keyVal[0],keyVal[1]);
          }
          clinvarObjects.put("rs" + subValuesMap.get("RS") + values[1] + values[0],
              MhObject.builder()
                .rsId("rs" + subValuesMap.get("RS"))
                .chromosome(values[0])
                .position(values[1])
                .result(values[3]+values[4])
                .additionalInfos(subValuesMap)
              .build());
        }
        // read next line
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return clinvarObjects;
  }

}
