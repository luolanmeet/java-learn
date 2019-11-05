package pers.http;

/**
 * 模拟Sevlet规范
 */
public abstract class Servlet {

    public void service(Request request, Response response) throws Exception {

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    public abstract void doGet(Request request, Response response) throws Exception;

    public abstract void doPost(Request request, Response response) throws Exception;

}
