package com.zonief;

import java.util.ArrayList;
import com.zonief.beans.MhObject;
import com.zonief.services.DataParser;

public class Main {

  public static void main(String[] args) {
    DataParser dataParser = new DataParser();
    var result = new ArrayList<MhObject>();
    var mhObjects = dataParser.getMhObjects();
    var clinvarObjects = dataParser.getClinvarObjects();
    var count = 0;
    for(String key:mhObjects.keySet()){
      MhObject clinvarObject = clinvarObjects.get(key);
      if(clinvarObject != null){
        MhObject mhObject = mhObjects.get(key);
        if(mhObject.getResult().equals(clinvarObject.getResult())){
          result.add(clinvarObject);
          count++;
        }
      }
    }
    System.out.println("--------------------------------------------------");
    System.out.println("Potential pathologies:");
    var pathologiesList = new ArrayList<String>();
    for(MhObject mhObject:result){
      String clndnincl = mhObject.getAdditionalInfos().get("CLNDNINCL");
      if(!pathologiesList.contains(clndnincl + ": " + mhObject.getAdditionalInfos().get("CLNSIG"))){
        pathologiesList.add(clndnincl + ": " + mhObject.getAdditionalInfos().get("CLNSIG"));
      }
    }
    pathologiesList.forEach(System.out::println);
    System.out.println("--------------------------------------------------");
    System.out.println("Objects from kit file: " + mhObjects.size());
    System.out.println("Objects from Clinvar Database: " + clinvarObjects.size());
    System.out.println("Total matches: " + count);
  }
}

