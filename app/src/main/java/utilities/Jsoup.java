package utilities;

import static models.Urls.URL;
import static org.jsoup.Jsoup.connect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.CardViewData;
import models.NewsData;
import models.PageContent;
import models.PriceListData;
import models.RecyclerViewData;
import models.TypeOfCatalogItems;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import tasks.JsoupAsyncTask;

public class Jsoup {

  public static PageContent GetMainPageContent(JsoupAsyncTask jsoupAsyncTask, String[] strings)
      throws IOException {

    PageContent pageContent = new PageContent();

    Document doc;
    jsoupAsyncTask.PublishProgress("Получаем главную страницу...");
    doc = connect(URL).get();

    jsoupAsyncTask.PublishProgress("Получаем данные...");
    for (Element item : doc.select("ul.slides > li")) {

      CardViewData data = new CardViewData();
      data.setID(item.attr("id"));
      String style = item.attr("style");
      String substring = style.substring(style.indexOf("'") + 1);
      data.setBackgroundImageUrl(
          substring.subSequence(0, substring.indexOf("'")).toString());
      data.setTitle(item.select("div[class=title]").text());
      data.setText(item.select("div[class=text-block]").text());
      data.setItemImageUrl(item.select("img[class=plaxy]").attr("src"));
      data.setHref(item.select("a[class=btn btn-default]").attr("href"));

      pageContent.getCardViewData().add(data);
    }

    for (Element element : doc.select("div[class=col-md-3 col-sm-6] > div[class=item]")) {
      RecyclerViewData data = new RecyclerViewData();
      data.setImgUrl(element.select("img").attr("src"));
      data.setTitle(element.select("span[class=top-text]").text());
      data.setText(element.select("span[class=desc-text]").text());

      pageContent.getRecyclerViewData().add(data);
    }

    jsoupAsyncTask.PublishProgress("Почти все говото...");

    return pageContent;
  }

  public static PageContent GetCatalogContent(JsoupAsyncTask jsoupAsyncTask, String[] strings)
      throws IOException {

    PageContent pageContent = new PageContent();

    String href = strings[0];

    Document doc;
    jsoupAsyncTask.PublishProgress("Получаем страницу...");
    doc = connect(URL + href).get();

    jsoupAsyncTask.PublishProgress("Получаем данные...");
    for (Element element : doc.select("div[class=image] > a[href]")) {
      RecyclerViewData data = new RecyclerViewData();
      data.setImgUrl(element.select("img").attr("src"));
      data.setTitle(element.select("img").attr("title"));
      data.setHref(element.attr("href"));

      data.setType(element.select("img")
          .attr("itemprop").equals("image")
          ? TypeOfCatalogItems.ITEM : TypeOfCatalogItems.CATALOG);

      pageContent.getRecyclerViewData().add(data);
    }
    jsoupAsyncTask.PublishProgress("Почти все говото...");

    return pageContent;
  }

  public static PageContent GetPricesCatalogContent(JsoupAsyncTask jsoupAsyncTask, String[] strings)
      throws IOException {

    PageContent pageContent = new PageContent();

    String href = strings[0];

    Document doc;

    jsoupAsyncTask.PublishProgress("Получаем страницу...");
    doc = connect(URL + href).get();

    jsoupAsyncTask.PublishProgress("Получаем данные...");

    pageContent.setTitleStr(doc.select("#pagetitle").text());

    for (Element element : doc.select("aside[class=sidebar] > ul > li")) {
      RecyclerViewData data = new RecyclerViewData();

      data.setTitle(element.text());
      data.setHref(element.select("a[href]").attr("href"));

      data.setType(element.select("img")
          .attr("itemprop").equals("image")
          ? TypeOfCatalogItems.ITEM : TypeOfCatalogItems.CATALOG);

      pageContent.getRecyclerViewData().add(data);
    }

    return pageContent;
  }

  public static PageContent GetItemContent(JsoupAsyncTask jsoupAsyncTask, String[] strings)
      throws IOException {

    PageContent pageContent = new PageContent();

    String href = strings[0];

    Document doc;

    jsoupAsyncTask.PublishProgress("Получаем страницу...");
    doc = connect(URL + href).get();

    jsoupAsyncTask.PublishProgress("Получаем данные...");

    pageContent.setTitleStr(
        doc.select("#pagetitle").text()
    );

    pageContent.setPriceStr(
        doc.select("div[class=info]")
            .select("span[class=price_val]").text());

    pageContent.setDescStr(
        doc.select("div [class=content]").text()
    );

    for (Element element : doc.select("ul[class=slides items] > li")) {
      CardViewData data = new CardViewData();
      data.setItemImageUrl(element.select("a[href]").attr("href"));

      pageContent.getCardViewData().add(data);
    }

    for (Element item : doc.select("tr[class=char]")) {
      pageContent.getTableRows().put(
          item.select("td[class=char_name]").text(),
          item.select("td[class=char_value]").text()
      );
    }

    jsoupAsyncTask.PublishProgress("Почти все говото...");

    return pageContent;
  }

  public static PageContent GetPriceListContent(JsoupAsyncTask jsoupAsyncTask, String[] strings)
      throws IOException {

    PageContent pageContent = new PageContent();

    String href = strings[0];

    Document doc;

    jsoupAsyncTask.PublishProgress("Получаем страницу...");
    doc = connect(URL + href).get();

    jsoupAsyncTask.PublishProgress("Получаем данные...");

    for (Element e : doc.select("table[class=table table-striped] > tbody")) {

      List<PriceListData> modelList = new ArrayList<>();
      String str = "";

      for (Element item : e.select("tr")) {
        if (item.children().size() >= 4) {

          PriceListData model = new PriceListData();
          model.setNumber(item.child(0).text());
          model.setTitle(item.child(1).text());
          model.setLight(item.child(2).text());
          model.setPrice(item.child(3).text());
          modelList.add(model);
        } else {
          str = item.text();
        }
      }
      pageContent.getPriceList().put(str, modelList);
    }

    jsoupAsyncTask.PublishProgress("Почти все говото...");

    return pageContent;
  }

  public static PageContent GetNewsFeedContent(JsoupAsyncTask jsoupAsyncTask, String[] strings)
      throws IOException {

    PageContent pageContent = new PageContent();

    String href = strings[0];

    Document doc;

    jsoupAsyncTask.PublishProgress("Получаем страницу...");
    doc = connect(URL + href).get();

    jsoupAsyncTask.PublishProgress("Получаем данные...");

    for (Element element : doc.select("div[class=col-md-4 col-sm-6]")) {
      NewsData data = new NewsData();

      data.setTitle(element.select("div[class=title]").text());
      data.setPeriod(element.select("div[class=period]").text());
      data.setSticker(element.select("div[class=sticker-block]").text());
      data.setHref(element.select("a[href]").attr("href"));
      data.setImgUrl(element.select("img").attr("src"));

      pageContent.getNewsData().add(data);
    }
    jsoupAsyncTask.PublishProgress("Почти все говото...");

    return pageContent;
  }

  public static PageContent GetSinglePageContent(JsoupAsyncTask jsoupAsyncTask, String[] strings)
      throws IOException {

    PageContent pageContent = new PageContent();

    String href = strings[0];

    Document doc;

    jsoupAsyncTask.PublishProgress("Получаем страницу...");
    doc = connect(URL + href).get();

    jsoupAsyncTask.PublishProgress("Получаем данные...");
    pageContent.setImageUrl(doc.select("div[class=detailimage image-left] > a[href]")
        .attr("href"));
    pageContent.setTitleStr(doc.getElementsByClass("introtext").text());
    pageContent.setDescStr(doc.select("div[class=content]").text());
    pageContent.setPeriod(doc.select("div[class=period]").text());

    jsoupAsyncTask.PublishProgress("Почти все говото...");

    return pageContent;
  }

  public static PageContent GetServicesContent(JsoupAsyncTask jsoupAsyncTask, String[] strings)
      throws IOException {

    PageContent pageContent = new PageContent();

    String href = strings[0];

    Document doc;

    jsoupAsyncTask.PublishProgress("Получаем страницу...");
    doc = connect(URL + href).get();

    jsoupAsyncTask.PublishProgress("Получаем данные...");

    if (!doc.select("div[class=item  clearfix]").isEmpty()) {
      for (Element element : doc.select("div[class=item  clearfix]")) {
        RecyclerViewData data = new RecyclerViewData();
        data.setTitle(element.getElementsByClass("title").text());
        data.setText(element.getElementsByClass("previewtext").text());
        data.setHref(element.getElementsByClass("image shine").
            select("a[href]").attr("href"));
        data.setImgUrl(element.select("img").attr("src"));

        if (element.getElementsByClass("link-block-more").isEmpty()) {
          data.setType(TypeOfCatalogItems.CATALOG);
        } else {
          data.setType(TypeOfCatalogItems.ITEM);
        }

        pageContent.getRecyclerViewData().add(data);
      }
    }  else if (!doc.select("div[class=col-md-6 col-sm-12]").isEmpty()) {
      for (Element element : doc.select("div[class=col-md-6 col-sm-12]")) {
        RecyclerViewData data = new RecyclerViewData();

        data.setHref(element.getElementsByClass("title").
            select("a[href]").attr("href"));
        data.setText(element.getElementsByClass("text").text());
        data.setTitle(element.getElementsByClass("title").text());
        String style = element.getElementsByClass("img_block").attr("style");
        String substring = style.substring(style.indexOf("(") + 1);
        data.setImgUrl(substring.subSequence(0, substring.indexOf(")")).toString());

        if (element.getElementsByClass("link-block-more").isEmpty()) {
          data.setType(TypeOfCatalogItems.CATALOG);
        } else {
          data.setType(TypeOfCatalogItems.ITEM);
        }

        pageContent.getRecyclerViewData().add(data);
      }
    } else if (!doc.getElementsByClass("item  wti clearfix").isEmpty()) {
      for (Element element : doc.getElementsByClass("item  wti clearfix")) {
        RecyclerViewData data = new RecyclerViewData();
        data.setTitle(element.getElementsByClass("title").text());
        data.setText(element.getElementsByClass("previewtext").text());
        data.setHref(element.getElementsByClass("title").
            select("a[href]").attr("href"));
        if (element.getElementsByClass("link-block-more").isEmpty()) {
          data.setType(TypeOfCatalogItems.CATALOG);
        } else {
          data.setType(TypeOfCatalogItems.ITEM);
        }
        pageContent.getRecyclerViewData().add(data);
      }
    }
    jsoupAsyncTask.PublishProgress("Почти все говото...");

    return pageContent;
  }
}
