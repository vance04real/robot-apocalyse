package za.ioc.robotapocalypse.enums;

import org.springframework.http.HttpStatus;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/
public enum ResponseCodesEnum {

  SUCCESS_CREATION(201, "Created Successfully ", HttpStatus.CREATED),

  SUCCESS_UPDATE(200, "Update Success ", HttpStatus.OK),

  SUCCESS(200, "Success", HttpStatus.OK),

  NO_SURVIVOR_FOUND(404 , "No Survivor found for the supplied id", HttpStatus.NOT_FOUND),

  SURVIVOR_CANT_REPORT(400 , "Survivor cannot report themselves", HttpStatus.BAD_REQUEST),

  SURVIVOR_ALREADY_INFECTED(400 , "Survivor already infect", HttpStatus.BAD_REQUEST),

  SURVIVOR_FLAGGED(200 , "Survivor flagged as infected", HttpStatus.OK),

  SURVIVOR_FLAGGED_PENDING_COUNT(200 , "Survivor is flagged but pending enough count to be deemed as infected", HttpStatus.OK),

  MISSING_FIELDS( 400, "One of the fields cannot be empty. Please try again", HttpStatus.BAD_REQUEST),

  NO_RECORDS_FOR_NOW_TO_COMPUTE(404 , "There are no records to compute for now. Please try again", HttpStatus.NOT_FOUND),

  NO_INFECTED_SURVIVORS_FOUND(404 , "No infected survivors found.", HttpStatus.NOT_FOUND),

  NO_NONEINFECTED_SURVIVORS_FOUND(404 , "No infected survivors found.", HttpStatus.NOT_FOUND),

  GENERAL_SYSTEM_ERR(10,
      "We are unable to process your request now due to a system error. Please try again later.",
      HttpStatus.INTERNAL_SERVER_ERROR),

  GENERAL_DATABASE_AND_SQL_ERROR(11,
      "We are unable to process your request now due to a database error. Please try again later.",
      HttpStatus.INTERNAL_SERVER_ERROR),

  NOT_IMPLEMENTED(12, "The operation is currently not implemented.", HttpStatus.NOT_IMPLEMENTED),

  MISSING_BODY(400, "There are missing field(s) in request. Please check request and try again.",
      HttpStatus.BAD_REQUEST),

  INTERNAL_SERVER_ERROR(500, "Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final int code;
  private final String description;
  private final HttpStatus httpStatus;

  ResponseCodesEnum(int code, String description, HttpStatus httpStatus) {
    this.code = code;
    this.description = description;
    this.httpStatus = httpStatus;
  }

  public String getDescription() {
    return description;
  }

  public int getCode() {
    return code;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public boolean isSuccess() {
    return httpStatus.is2xxSuccessful();
  }
}
