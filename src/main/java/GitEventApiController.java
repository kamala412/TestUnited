import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pojos.Event;


import java.io.*;
import java.util.*;

public class GitEventApiController {

    public static void main(String[] args) {
        fetchAndPrintGitHubEvents();
        countryCityMap();
    }

    private static void countryCityMap() {
        String line = "";
        String delimeter = ",";
        HashMap<String, Integer> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/towns.csv"))) {
            boolean isFirstRow = true;
            while ((line = br.readLine()) != null) {
                if(isFirstRow){
                    isFirstRow =false;
                }else{
                    String[] countyCity = line.split(delimeter);
                    if(map.containsKey(countyCity[1])){
                        map.put(countyCity[1], map.get(countyCity[1])+1);
                    }else{
                        map.put(countyCity[1], 0);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);
    }

    private static void fetchAndPrintGitHubEvents() {
        String url ="https://api.github.com/repos/octocat/hello-world/issues/events?per_page=10";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("accept","application/vnd.github.v3+json");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()!=200){
                System.out.println("Something went wrong while fetching the events"+response.getStatusLine().getStatusCode());
            }else{
                // System.out.println(response.getEntity());
                String responseString = EntityUtils.toString(response.getEntity());
                System.out.println(responseString);

                ObjectMapper mapper = new ObjectMapper();
                Event[] events = mapper.readValue(responseString, Event[].class);
                List<Event> list = Arrays.asList(events);

                list.forEach(e -> System.out.println(e.getEvent()));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
