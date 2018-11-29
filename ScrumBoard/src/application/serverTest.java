package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

class serverTest {

	@Test
	void test() throws UnknownHostException, IOException {
		client CL = new client();
		CL.startConnection("127.0.0.1", 6656);
	    String response = CL.sendMessage("hello server");
	    assertEquals("hello client", response);
	}

}
