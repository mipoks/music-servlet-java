package design.kfu.helper.view;

public class Alert {

    public final static String COLOR_SUCCESS = "success";
    public final static String HEAD_DANGER = "Произошла ошибка!";
    public final static String COLOR_DANGER = "danger";

    private String body;
    private String color = "light";
    private String head;

    public Alert(String body, String color, String head) {
        this.body = body;
        this.color = color;
        this.head = head;
    }

    public Alert() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
