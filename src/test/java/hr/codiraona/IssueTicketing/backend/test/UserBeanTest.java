package hr.codiraona.IssueTicketing.backend.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import hr.codiraona.IssueTicketing.backend.dao.UserDAO;
import hr.codiraona.IssueTicketing.backend.dto.UserDTO;
import hr.codiraona.IssueTicketing.backend.model.Company;
import hr.codiraona.IssueTicketing.backend.model.Location;
import hr.codiraona.IssueTicketing.backend.model.Role;
import hr.codiraona.IssueTicketing.backend.model.User;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.spi.PersistenceUnitTransactionType;
import org.eclipse.persistence.config.PersistenceUnitProperties;

@Ignore
public class UserBeanTest {

	@PersistenceContext(unitName = "IssueTicketing-backend")
	private EntityManager em;

	private User user;
	private UserDTO userDTO;
	private UserDAO userBean;
	private Company company;
	private Role role;
    private Map<String, String> properties;

	
	@Before
	public void setUp() {
        setUpPersistenceForTesting();
        userBean = new UserDAO(em);
        user = new User();
		user.setCompany(em.find(Company.class, 1));
		user.setLocation(em.find(Location.class, 100));
		user.setRole(em.find(Role.class, 2));
		user.setEmail("bilandzic.iva@gmail.com");
		user.setFirstName("Iva");
		user.setLastName("Bilandzic Siljevinac");
		user.setUsername("ibilandzic");
		user.setPassword("12345");
		
		userDTO = new UserDTO();
		userDTO.setCompany(em.find(Company.class, 1));
		userDTO.setLocation(em.find(Location.class, 100));
		userDTO.setRole(em.find(Role.class, 2));
		userDTO.setEmail("bilandzic.iva@gmail.com");
		userDTO.setFirstName("Iva");
		userDTO.setLastName("Bilandzic Siljevinac");
		userDTO.setUsername("ibilandzic");
		userDTO.setMobileNumber("22222");
		
	}
	
	@After
	public void tearDown(){
		em.getTransaction().begin();
		em.remove(user);
		em.getTransaction().commit();
	}
	
	

	@Test
	public void testCreateUser() {
		assertTrue(userBean.createUser(user));
	}

	@Test
	public void testUpdateUser() {
		em.getTransaction().begin();;
		userBean.createUser(user);
		em.getTransaction().commit();
		assertTrue(userBean.updateUser(userDTO));
	}

	@Test
	public void testSetPassword() {
		
	}

	@Test
	public void testLogIn() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTicketsReportedByUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTicketAssignedToUser() {
		fail("Not yet implemented");
	}

    private void setUpPersistenceForTesting() {
        properties = new HashMap<String, String>();
        properties.put(PersistenceUnitProperties.TRANSACTION_TYPE, PersistenceUnitTransactionType.RESOURCE_LOCAL.name());
        properties.put(PersistenceUnitProperties.JDBC_DRIVER,"org.apache.derby.jdbc.ClientDriver");
        properties.put(PersistenceUnitProperties.JDBC_URL, "jdbc:derby://localhost:1530/D:/Database/Ticketing");
        properties.put(PersistenceUnitProperties.JDBC_USER, "admin");
        properties.put(PersistenceUnitProperties.JDBC_PASSWORD, "admin");
        em = Persistence.createEntityManagerFactory("IssueTicketing-backend", properties).createEntityManager(properties);
    }


}
