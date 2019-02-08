package brown.system.client.library;


import brown.system.kryoserver.library.KryoServer;
import brown.system.setup.ISetup;
import brown.system.setup.library.SimpleSetup;
import brown.communication.messages.library.ErrorMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.communication.messages.library.StringMessage;
import brown.logging.library.SystemLogging;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the TP Client object.
 */
public class TPClientTest {


    private class TestClient extends TPClient {
        public String string;
        public String error;

        private TestClient(String host, Integer port, ISetup setup) {

            super(host, port, setup);
            CLIENT.addListener(new Listener() {
                public void received(Connection connection, Object message) {
                    if (message instanceof RegistrationMessage) {
                        onRegistration((RegistrationMessage) message);
                    } else if (message instanceof ErrorMessage) {
                        error = ((ErrorMessage) message).error;
                        onErrorMessage((ErrorMessage) message);
                    } else if (message instanceof StringMessage) {
                        string = ((StringMessage) message).message;
                    }
                }
            });
            CLIENT.sendTCP(new RegistrationMessage(-1, "testing"));
        }
    }

    private class TestServer extends KryoServer {
        public TestServer(int port, ISetup setup) {
            super(port, setup);
                kryoServer.addListener(new Listener() {
                public void received(Connection connection, Object message) {
                    if (message instanceof RegistrationMessage) {
                        Integer theID = 1;
                        connection.sendTCP(15000);
                        connection.setTimeout(60000);
                        kryoServer.sendToTCP(connection.getID(), new RegistrationMessage(theID));
                        kryoServer.sendToTCP(connection.getID(), new ErrorMessage(0, "test error"));
                        kryoServer.sendToTCP(connection.getID(), new StringMessage(0, "test string"));
                }}});
        }
    }

    @Test
    public void testConstructor() {
        TPClient tpClient = new TestClient("localhost", 2121, new SimpleSetup());
        assertTrue(tpClient.CLIENT != null);
        assertTrue(tpClient.CLIENT instanceof Client);
        assertEquals(tpClient.ID, null);
    }

    @Test
    public void testRegistration() throws InterruptedException {
        TestServer testServer = new TestServer(2121, new SimpleSetup());
        Thread.sleep(1000);
        TestClient testClient = new TestClient("localhost", 2121, new SimpleSetup());
        Thread.sleep(1000);
        System.out.println(testClient.ID);
        assertTrue(testClient.ID ==  1);
        assertEquals((testClient).error, "test error");
        assertEquals((testClient).string, "test string");
    }

    public static void main(String[] args) throws InterruptedException{
        TPClientTest clientTest = new TPClientTest();
        clientTest.testConstructor();
        clientTest.testRegistration();
    }
}
