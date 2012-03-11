package imagemagician;

import org.junit.*;
import junit.framework.JUnit4TestAdapter;

import java.io.*;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.endsWith;

/**
 * @author adorechic
 */
public class ResizeCommandTest {
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ResizeCommandTest.class);
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test(expected = MagicianException.class)
	public void 未ビルドテスト() {
		new ResizeCommand().getCommand();
	}

	@Test
	public void 通常ケース() {
		ConvertCommand command =
				new ResizeCommand()
				.src("test".getBytes(),ImageType.PNG)
				.resize(400)
				.define("aaa")
				.autoOrient()
				.colorSpace("RGB")
				.strip()
				.build();

		List<String> list = command.getCommand();
		assertThat(list.get(0),is("/usr/local/ImageMagick/bin/convert"));
		assertThat(list.get(1),is(startsWith("/tmp/imagemagician-")));
		assertThat(list.get(1),is(endsWith(".png")));
		assertThat(list.get(2),is("-resize"));
		assertThat(list.get(3),is("400x400>"));
		assertThat(list.get(4),is("-colorspace"));
		assertThat(list.get(5),is("RGB"));
		assertThat(list.get(6),is("-auto-orient"));
		assertThat(list.get(7),is("-strip"));
		assertThat(list.get(8),is("png:-"));

		command.clean();
	}

	@Test
	public void 最小限指定ケース() {
		ConvertCommand command =
				new ResizeCommand()
				.src("test".getBytes(), ImageType.GIF)
				.resize(200, 150, ' ')
				.build();

		List<String> list = command.getCommand();
		assertThat(list.get(0),is("/usr/local/ImageMagick/bin/convert"));
		assertThat(list.get(1),is(startsWith("/tmp/imagemagician-")));
		assertThat(list.get(1),is(endsWith(".gif")));
		assertThat(list.get(2),is("-resize"));
		assertThat(list.get(3),is("200x150 "));
		assertThat(list.get(4),is("gif:-"));

		command.clean();
	}
	@Test
	public void jpegdefine指定ケース() {
		ConvertCommand command =
				new ResizeCommand()
				.src("test".getBytes(), ImageType.JPG)
				.resize(200, 150, ' ')
				.build();

		List<String> list = command.getCommand();
		assertThat(list.get(0),is("/usr/local/ImageMagick/bin/convert"));
		assertThat(list.get(1),is(startsWith("/tmp/imagemagician-")));
		assertThat(list.get(1),is(endsWith(".jpg")));
		assertThat(list.get(2),is("-define"));
		assertThat(list.get(3),is("jpeg:size=200x150"));
		assertThat(list.get(4),is("-resize"));
		assertThat(list.get(5),is("200x150 "));
		assertThat(list.get(6),is("jpg:-"));

		command.clean();
	}

	@Test
	public void 一時ファイル出力テスト() throws IOException {
		ConvertCommand command =
				new ResizeCommand()
				.src("test".getBytes(), ImageType.GIF)
				.build();

		String path = command.getCommand().get(1);

		FileInputStream fis = new FileInputStream(path);
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(b);
		int c;
		while ((c = fis.read()) != -1) {
			bos.write(c);
		}
		bos.flush();
		fis.close();
		bos.close();
		b.close();
		command.clean();
		assertThat(b.toByteArray(),is("test".getBytes()));
	}
}
