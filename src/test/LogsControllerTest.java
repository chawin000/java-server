import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class LogsControllerTest {

    public Request request = mock(Request.class);
    public LogsController controller = new LogsController(new ArrayList<String>());

    @Before
    public void setup() {
        request.route = "/logs";
        request.method = "GET";
        request.authorization = new HashMap<String, String>();
    }

    @Test
    public void returnsUnauthorized() {
        request.authorization.put("Basic", "wrong:password");
        assertEquals(401, controller.send(request).status);
    }

    @Test
    public void returnsLogs() {
        request.authorization.put("Basic", "YWRtaW46aHVudGVyMg==\n");
        assertEquals(200, controller.send(request).status);
    }
}
