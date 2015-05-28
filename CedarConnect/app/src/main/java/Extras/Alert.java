package Extras;

/**
 * Created by Saboor Salaam on 3/10/2015.
 */
public class Alert {
    String title, body, ok_message, alert_id, other_message;
    Boolean isThereOtherMessage = false;
    int alert_type;


    public Alert() {
        super();
    }

    public int getAlert_type() {
        return alert_type;
    }

    public void setAlert_type(int alert_type) {
        this.alert_type = alert_type;
    }

    public Alert(String title, String body, String ok_message, boolean isThereOtherMessage, String other_message, String alert_id, int alert_type) {
        this.title = title;
        this.body = body;
        this.ok_message = ok_message;
        this.alert_id = alert_id;
        this.other_message = other_message;
        this.isThereOtherMessage = isThereOtherMessage;
        this.alert_type = alert_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOk_message() {
        return ok_message;
    }

    public void setOk_message(String ok_message) {
        this.ok_message = ok_message;
    }

    public String getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(String alert_id) {
        this.alert_id = alert_id;
    }

    public String getOther_message() {
        return other_message;
    }

    public void setOther_message(String other_message) {
        this.other_message = other_message;
    }
}
