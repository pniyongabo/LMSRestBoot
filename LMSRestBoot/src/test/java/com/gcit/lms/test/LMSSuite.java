package com.gcit.lms.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(Suite.class)
@SuiteClasses({ AuthorTest.class})
@ContextConfiguration(classes=LMSConfig.class, loader=AnnotationConfigContextLoader.class)
public class LMSSuite {

}
