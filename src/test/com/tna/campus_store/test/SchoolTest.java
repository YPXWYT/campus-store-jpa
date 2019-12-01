package com.tna.campus_store.test;

import com.tna.campus_store.repository.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SchoolTest {

	@Autowired
	private SchoolRepository schoolRepository;

	@Test
	public void testFind() {
		schoolRepository.findOne(1);
	}
}
