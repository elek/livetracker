package net.anzix.livetrack;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;

import javax.servlet.http.HttpServletRequest;

/**
 * Starting page.
 */
@At("/")
public class Index {

    @Inject
    HttpServletRequest request;

    private String url;

    @Get
    public void get() {
        this.url = request.getRequestURL().toString();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
