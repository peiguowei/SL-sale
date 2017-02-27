package com.demo.sl;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
@ContextConfiguration(locations = "classpath:spring.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
}
