package za.ioc.robotapocalypse.enums;

import java.util.Arrays;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

public enum Resource {
  Water,
  Food,
  Medication,
  Ammunition;

  public static Resource getResource(String name){
    return Arrays.stream(Resource.values())
            .filter(resource -> resource.name().equalsIgnoreCase(name))
                .findFirst()
                .get();
  }

}
