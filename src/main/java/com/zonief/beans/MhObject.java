package com.zonief.beans;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MhObject implements Comparable<MhObject>{

  private String rsId;
  private String chromosome;
  private String position;
  private String result;
  private Map<String,String> additionalInfos;

  @Override
  public int compareTo(MhObject u) {
    if (getChromosome() == null || u.getChromosome() == null) {
      return 0;
    }
    return getChromosome().compareTo(u.getChromosome());
  }
}
