package ro.isdc.utils;

public class Country {
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  private String code;
  private String name;
  public Country(String code, String name) {
    super();
    this.code = code;
    this.name = name;
  }
}

