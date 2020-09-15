package models;

public class Message {

  public boolean isMoveValidity() {
    return moveValidity;
  }

  public void setMoveValidity(boolean moveValidity) {
    this.moveValidity = moveValidity;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  private boolean moveValidity;

  private int code;

  private String message;

}
