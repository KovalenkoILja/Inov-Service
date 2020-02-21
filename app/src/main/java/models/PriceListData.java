package models;

public class PriceListData {

  private String number;
  private String title;
  private String light;
  private String price;

  @Override
  public String toString() {
    return "PriceListData{" +
        "number='" + number + '\'' +
        ", title='" + title + '\'' +
        ", light='" + light + '\'' +
        ", price='" + price + '\'' +
        '}';
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLight() {
    return light;
  }

  public void setLight(String light) {
    this.light = light;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
