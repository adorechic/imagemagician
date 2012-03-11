package imagemagician;

import org.junit.*;
import junit.framework.JUnit4TestAdapter;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author adorechic
 */
public class MagicianConfigTest {
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(MagicianConfigTest.class);
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testValues() {
		assertThat(MagicianConfig.BUFFER_SIZE,is(65536));
		assertThat(MagicianConfig.DATA_DIR.getAbsolutePath()
				,is("/tmp"));
		assertThat(MagicianConfig.IMAGE_MAGICK_BIN,is("/usr/local/ImageMagick/bin"));
	}
}
