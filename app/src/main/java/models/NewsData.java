package models;

public class NewsData {

  private String imgUrl;
  private String href;
  private String title;
  private String sticker;
  private String period;

  @Override
  public String toString() {
    return "NewsData{" +
        "imgUrl='" + imgUrl + '\'' +
        ", href='" + href + '\'' +
        ", title='" + title + '\'' +
        ", sticker='" + sticker + '\'' +
        ", period='" + period + '\'' +
        '}';
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSticker() {
    return sticker;
  }

  public void setSticker(String sticker) {
    this.sticker = sticker;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }


}
