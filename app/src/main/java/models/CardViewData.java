package models;

public class CardViewData {

  private String ID;
  private String backgroundImageUrl;
  private String itemImageUrl;
  private String title;
  private String text;
  private String href;

  public CardViewData(String id,
      String backgroundImageUrl,
      String item_url,
      String title,
      String text) {
    this.ID = id;
    this.backgroundImageUrl = backgroundImageUrl;
    this.itemImageUrl = item_url;
    this.title = title;
    this.text = text;
  }

  public CardViewData() {
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getBackgroundImageUrl() {
    return backgroundImageUrl;
  }

  public void setBackgroundImageUrl(String backgroundImageUrl) {
    this.backgroundImageUrl = backgroundImageUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getItemImageUrl() {
    return itemImageUrl;
  }

  public void setItemImageUrl(String itemImageUrl) {
    this.itemImageUrl = itemImageUrl;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "CardViewData{" +
        "ID='" + ID + '\'' +
        ", backgroundImageUrl='" + backgroundImageUrl + '\'' +
        ", itemImageUrl='" + itemImageUrl + '\'' +
        ", title='" + title + '\'' +
        ", text='" + text + '\'' +
        ", href='" + href + '\'' +
        '}';
  }
}
