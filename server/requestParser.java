package server;

import java.util.ArrayList;
import java.util.Arrays;

public class requestParser {

  private ArrayList<String> request;
  private String method;
  private ArrayList<String> params;

  public void parse() {
    ArrayList<String> requestArray = new ArrayList<String>(Arrays.asList(this.request.get(0).split(" ")));
    this.method = requestArray.get(0);
    this.params = new ArrayList<String>(requestArray.subList(1, requestArray.size()));
  }

  public requestParser(ArrayList<String> request) {
    this.request = request;
    parse();
  }

  public String getMethod() {
    return method;
  }

  public ArrayList<String> getParams() {
    return params;
  }

}
