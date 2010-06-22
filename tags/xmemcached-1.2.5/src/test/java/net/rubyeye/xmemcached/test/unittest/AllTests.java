package net.rubyeye.xmemcached.test.unittest;

import java.io.IOException;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.rubyeye.xmemcached.test.unittest.buffer.BufferAllocatorTestSuite;
import net.rubyeye.xmemcached.test.unittest.codec.MemcachedDecoderUnitTest;
import net.rubyeye.xmemcached.test.unittest.codec.MemcachedEncoderUnitTest;
import net.rubyeye.xmemcached.test.unittest.commands.binary.BinaryCommandAllTests;
import net.rubyeye.xmemcached.test.unittest.commands.factory.TextCommandFactoryTest;
import net.rubyeye.xmemcached.test.unittest.commands.text.TextCommandsAllTests;
import net.rubyeye.xmemcached.test.unittest.hibernate.XmemcacheClientFactoryUnitTest;
import net.rubyeye.xmemcached.test.unittest.impl.MemcachedClientStateListenerUnitTest;
import net.rubyeye.xmemcached.test.unittest.impl.OptimezerTest;
import net.rubyeye.xmemcached.test.unittest.impl.SessionLocatorTest;
import net.rubyeye.xmemcached.test.unittest.monitor.StatisticsHandlerUnitTest;
import net.rubyeye.xmemcached.test.unittest.monitor.XMemcachedMBeanServerUnitTest;
import net.rubyeye.xmemcached.test.unittest.transcoder.TranscoderAllTests;
import net.rubyeye.xmemcached.test.unittest.utils.AddrUtilTest;
import net.rubyeye.xmemcached.test.unittest.utils.XMemcachedClientFactoryBeanUnitTest;

import com.google.code.yanf4j.util.ResourcesUtils;

/**
 * Run this test suite with jvm args "-ea"
 * 
 * @author dennis
 * 
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.rubyeye.xmemcached.test.unittest");
		// $JUnit-BEGIN$
		suite.addTestSuite(OptimezerTest.class);
		suite.addTestSuite(TextCommandFactoryTest.class);
		suite.addTestSuite(AddrUtilTest.class);
		suite.addTestSuite(SessionLocatorTest.class);
		suite.addTest(TextCommandsAllTests.suite());
		suite.addTest(BinaryCommandAllTests.suite());
		suite.addTest(TranscoderAllTests.suite());
		suite.addTestSuite(StatisticsHandlerUnitTest.class);
		suite.addTestSuite(MemcachedDecoderUnitTest.class);
		suite.addTestSuite(MemcachedEncoderUnitTest.class);
		suite.addTest(BufferAllocatorTestSuite.suite());
		suite.addTestSuite(XmemcacheClientFactoryUnitTest.class);
		suite.addTestSuite(XMemcachedClientFactoryBeanUnitTest.class);
		suite.addTestSuite(XMemcachedMBeanServerUnitTest.class);
		suite.addTestSuite(MemcachedClientStateListenerUnitTest.class);
		try {
			Properties properties = ResourcesUtils
					.getResourceAsProperties("test.properties");
			if (properties.get("test.memcached.servers") != null) {
				suite.addTestSuite(StandardHashMemcachedClientTest.class);
				suite.addTestSuite(ConsistentHashMemcachedClientTest.class);
				suite.addTestSuite(BinaryMemcachedClientUnitTest.class);
			}
		} catch (IOException e) {
			System.err
					.println("If you want to run the XMemcachedClientTest,please set the config file test.properties");
		}
		// $JUnit-END$
		return suite;
	}
}
