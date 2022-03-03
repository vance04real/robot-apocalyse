package za.ioc.robotapocalypse.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Data
@NoArgsConstructor
public class RobotResponse {

  @SerializedName("model")
  @JsonProperty("model")
  public String model;

  @SerializedName("serialNumber")
  @JsonProperty("serialNumber")
  public String serialNumber;


  @SerializedName("manufacturedDate")
  @JsonProperty("manufacturedDate")
  public Date manufacturedDate;

  @SerializedName("category")
  @JsonProperty("category")
  public String category;
}
