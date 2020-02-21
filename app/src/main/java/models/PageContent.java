package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageContent {

  private String titleStr;
  private String descStr;
  private String priceStr;
  private String imageUrl;
  private String period;
  private String href;

  private Map<String, String> tableRows;
  private List<CardViewData> cardViewData;
  private List<RecyclerViewData> recyclerViewData;
  private List<NewsData> newsData;
  private Map<String, List<PriceListData>> priceList;

  public PageContent() {
    this.cardViewData = new ArrayList<>();
    this.recyclerViewData = new ArrayList<>();
    this.newsData = new ArrayList<>();
    this.tableRows = new HashMap<>();
    this.priceList = new HashMap<>();
  }

  public List<CardViewData> getCardViewData() {
    return cardViewData;
  }

  public void setCardViewData(List<CardViewData> cardViewData) {
    this.cardViewData = cardViewData;
  }

  public List<RecyclerViewData> getRecyclerViewData() {
    return recyclerViewData;
  }

  public void setRecyclerViewData(List<RecyclerViewData> recyclerViewData) {
    this.recyclerViewData = recyclerViewData;
  }

  public String getTitleStr() {
    return titleStr;
  }

  public void setTitleStr(String titleStr) {
    this.titleStr = titleStr;
  }

  public Map<String, String> getTableRows() {
    return tableRows;
  }

  public void setTableRows(Map<String, String> tableRows) {
    this.tableRows = tableRows;
  }

  public String getDescStr() {
    return descStr;
  }

  public void setDescStr(String descStr) {
    this.descStr = descStr;
  }

  public String getPriceStr() {
    return priceStr;
  }

  public void setPriceStr(String priceStr) {
    this.priceStr = priceStr;
  }

  public Map<String, List<PriceListData>> getPriceList() {
    return priceList;
  }

  public void setPriceList(Map<String, List<PriceListData>> priceList) {
    this.priceList = priceList;
  }

  public List<NewsData> getNewsData() {
    return newsData;
  }

  public void setNewsData(List<NewsData> newsData) {
    this.newsData = newsData;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }
}
