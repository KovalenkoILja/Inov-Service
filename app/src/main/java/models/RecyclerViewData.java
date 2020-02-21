package models;

public class RecyclerViewData {

  private String imgUrl;
  private String title;
  private String text;
  private String href;
  private TypeOfCatalogItems type;

  public RecyclerViewData() {
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public TypeOfCatalogItems getType() {
    return type;
  }

  public void setType(TypeOfCatalogItems type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "RecyclerViewData{" +
        "imgUrl='" + imgUrl + '\'' +
        ", title='" + title + '\'' +
        ", text='" + text + '\'' +
        ", href='" + href + '\'' +
        ", type=" + type +
        '}';
  }
}

