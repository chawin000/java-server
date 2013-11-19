import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.io.*;

@RunWith(JUnit4.class)
public class FileResponseTest {

    FileResponse response = new FileResponse(200, "resources/test.txt");
    FileResponse zipResponse = new FileResponse(200, "resources/test.text.zip");
    FileResponse docResponse = new FileResponse(200, "resources/Testing.doc");

    @Test
    public void initsWithStatus() {
        assertEquals(200, response.status);
    }

    @Test
    public void initsWithPath() {
        assertEquals("resources/test.txt", response.filepath);
    }

    @Test
    public void instantiatesFile() {
        assertEquals(File.class, response.file.getClass());
    }

    @Test
    public void addsContentLengthHeader() {
        assertEquals("27", response.headers.get("Content-Length"));
    }

    @Test
    public void getsCorrectMimeType() {
        assertEquals("text/plain", response.getMimeType());
        assertEquals("application/zip", zipResponse.getMimeType());
        assertEquals("application/msword", docResponse.getMimeType());
    }

    @Test
    public void addsContentTypeHeader() {
        assertEquals("text/plain", response.headers.get("Content-Type"));
    }

    @Test
    public void readsFile() throws IOException {
        assertEquals(byte[].class, response.readFile().getClass());
        assertEquals(new File("resources/test.txt").length(), response.readFile().length);
    }

    @Test
    public void returnsCombinedByteArray() throws IOException {
        int outputLength = response.responseString().getBytes().length + response.readFile().length;
        assertEquals(outputLength, response.output().length);
    }
}
