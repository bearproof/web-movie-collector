package ro.isdc.utils;

public class Language {
  private String code2;
  public String getCode2() {
    return code2;
  }
  public void setCode2(String code2) {
    this.code2 = code2;
  }
  public String getCode3() {
    return code3;
  }
  public void setCode3(String code3) {
    this.code3 = code3;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  private String code3;
  private String name;
  public Language(String code2, String code3, String name) {
    super();
    this.code2 = code2;
    this.code3 = code3;
    this.name = name;
  }
}

