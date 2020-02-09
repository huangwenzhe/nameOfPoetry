import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public class Request {
    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://www.gushiwen.org";
        HtmlPage htmlPage = webClient.getPage(url);
        HtmlElement body =  htmlPage.getBody();

        List<HtmlElement> list = body.getElementsByAttribute("div","class","cont");
        for(HtmlElement e : list){
            List<HtmlElement> element = e.getElementsByTagName("a");
            for(HtmlElement element1 : element){
                String s = element1.getAttribute("href");
          //      System.out.println(s);
                if(isPoetryPage(webClient,s)){
                    HtmlPage page = webClient.getPage(s);
                    HtmlElement ee = page.getBody();
                    List<HtmlElement> l = ee.getElementsByAttribute("div","class","typecont");
                    for(HtmlElement x : l){
                        List<HtmlElement> elements = x.getElementsByTagName("a");
                        System.out.println("elements = " + elements.size());
                        for(HtmlElement p : elements) {
                            String ss = p.getAttribute("href");
                        //    System.out.println(ss);
                            String newurl = "https://so.gushiwen.org" + ss;
                            HtmlPage ps = webClient.getPage(newurl);
                            HtmlElement bodys = ps.getBody();

                            String path = "//div[@class='cont']/h1/text()";
                            Object o =  bodys.getByXPath(path).get(0);
                            DomText domText = (DomText)o;

                            System.out.println(domText.asText());
                        }
                    }
                }
            }
        }
    }

    private static boolean isPoetryPage(WebClient webClient, String s) throws IOException {
        if(s.equals("/")){
            return false;
        }

        if(s.startsWith("\uFEFF")){
            s = s.substring(1);
        }
        HtmlPage  h  = webClient.getPage(s);
        HtmlElement body= h.getBody();
        List<HtmlElement> list = body.getElementsByAttribute("div","class","typecont");
       // System.out.println(list);
        if(list.isEmpty()){
            return false;
        }
        return true;
    }
}
