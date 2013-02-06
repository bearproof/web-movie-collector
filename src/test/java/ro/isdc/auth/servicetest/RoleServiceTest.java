package ro.isdc.auth.servicetest;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.isdc.auth.domain.Role;
import ro.isdc.auth.helper.RoleHelper;
import ro.isdc.auth.service.crud.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class RoleServiceTest {

	@Autowired
	public RoleHelper helper;

	@Autowired
	public RoleService service;

	@Test
	public void createTest() {
		Role entity = service.create(helper.createRandomEntity());
		assertTrue(entity.getId() != null);
	}

	@Ignore
	@Test
	public void readTest() {

	}

	@Test
	public void updateTest() {
		Role entity = service.create(helper.createRandomEntity());
		String id = entity.getId();
		entity.setRoleName("ROLE_X");
		service.update(entity);
		Role updatedRole = service.getById(id);
		assertTrue(updatedRole.getRoleName().equals("ROLE_X"));
	}

	@Test
	public void deleteTest() {
		Role entity = service.create(helper.createRandomEntity());
		String id = entity.getId();
		assertTrue(id != null);
		service.delete(id);
		assertTrue(service.getById(id) == null);
	}
}
