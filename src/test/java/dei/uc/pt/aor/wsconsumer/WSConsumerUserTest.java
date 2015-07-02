package dei.uc.pt.aor.wsconsumer;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;






import dei.uc.pt.aor.data.User;
import dei.uc.pt.aor.data.UserCollection;
import dei.uc.pt.aor.xml.TransformXML;
import static org.junit.Assert.assertEquals;
//dependências
//import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WSConsumerUserTest {

	@InjectMocks
	WSConsumerUser wsuser;
	
	@Mock
	User user;
	
	@Mock
	UserCollection userc;
	
	@Mock
	TransformXML txml;
	
	@Mock
	int value;
	
	//	private Response sampleResponse;

	//dependências
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		user = new User();
		userc = new UserCollection();
		txml = new TransformXML();
	}

	@Test
	public void testTotalUsers() {
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/totalusers");
		Response response = target.request().get();
//		verify
		
		assertEquals(response,"200");
		
		
//		when(wsuser.totalUsers()).thenReturn(value);
//		int value1 = 
		
		System.out.println("Checked successfully web service <totaluSers>");
	}

}