package pers.servlet;

import pers.http.Request;
import pers.http.Response;
import pers.http.Servlet;

public class FirstServlet extends Servlet {

    @Override
    public void doGet(Request request, Response response) throws Exception {
        doPost(request, response);
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        response.write("FirstServlet");
    }

}
