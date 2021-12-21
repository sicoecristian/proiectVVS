import org.junit.BeforeClass;
import org.junit.Test;
import services.ServerService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ServerServiceTest {
    static ServerService serverService;

    @BeforeClass
    public static void before() {
        try {
            serverService = new ServerService(8080, "src/main/java/website", "Stopped");
        } catch (Exception e) {
            System.out.println("Wrong Server Inputs");
        }
    }

    @Test
    public void testGetAndSetWebsiteFilesPathOk() {
        serverService = new ServerService();
        serverService.setWebsiteFilesPath("test website path");
        assertEquals("test website path", serverService.getWebsiteFilesPath());
    }

    @Test
    public void testGetAndSetRequestOk() {
        serverService = new ServerService();
        serverService.setRequest("test request");
        assertEquals("test request", serverService.getRequest());
    }

    @Test
    public void checkIfWebServerNotNull() {
        assertNotNull(serverService);
    }


    @Test
    public void testWebsiteFilesPathOk() {
        serverService = new ServerService(8888, "src/main/java/website", "Stopped");
    }


    @Test
    public void testServerStatusOk() {
        serverService = new ServerService(8080, "src/main/java/website", "Stopped");
    }
    @Test
    public void testGetAndSetPortOk(){
        serverService = new ServerService();
        serverService.setPort(1000);
        assertEquals(1000,serverService.getPort());
    }

    @Test
    public void testGetAndSetServerStatusOk(){
        serverService = new ServerService();
        serverService.setServerStatus("test server status");
        assertEquals("test server status",serverService.getServerStatus());
    }


}
