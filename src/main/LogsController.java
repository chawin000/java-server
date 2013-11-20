import java.util.LinkedList;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;

public class LogsController extends Controller {

    public static final String AUTH = "admin:hunter2";

    public LinkedList<String> logs;

    public LogsController(LinkedList<String> logs) {
        this.logs = logs;
    }

    public Response get() {
        Response response = null;
        if (authenticate()) {
            response = new Response(200);
            response.addBody(new TextBody(logs.toString()));
        }
        else {
            response = new Response(401);
            response.addBody(new TextBody("Authentication Required"));
        }
        return response;
    }

    public Boolean authenticate() {
        String auth = request.authorization.get("Basic");
        String credentials = null;
        if (auth != null) {
            try {
               credentials = new String(DatatypeConverter.parseBase64Binary(auth), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return AUTH.equals(credentials);
    }
}
