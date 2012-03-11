package imagemagician;

import org.junit.*;
import junit.framework.JUnit4TestAdapter;

import java.io.*;

import static org.junit.Assert.assertThat;

/**
 * @author adorechic
 */
public class MagicianTest {
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(MagicianTest.class);
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void PNG変換テスト() throws IOException{
		byte[] src = read(getFullPath("/test01.png"));

		ConvertCommand command =
				new ResizeCommand()
				.src(src,ImageType.PNG)
				.resize(400)
				.colorSpace("RGB")
				.autoOrient()
				.strip()
				.build();

		Magician magician = new Magician();
		MagickFuture future = magician.convert(command);
		future.get();
	}

	@Test
	public void GIF変換テスト() throws IOException{

		byte[] src = read(getFullPath("/test02.gif"));

		ConvertCommand command =
				new ResizeCommand()
				.src(src,ImageType.GIF)
				.resize(200)
				.colorSpace("RGB")
				.autoOrient()
				.strip()
				.build();

		Magician magician = new Magician();
		MagickFuture future = magician.convert(command);
		future.get();
	}

	@Test
	public void JPG変換テスト() throws IOException{

		byte[] src = read(getFullPath("/test03.jpg"));

		ConvertCommand command =
				new ResizeCommand()
				.src(src,ImageType.JPG)
				.resize(200)
				.colorSpace("RGB")
				.autoOrient()
				.strip()
				.build();

		Magician magician = new Magician();
		MagickFuture future = magician.convert(command);
		future.get();
	}

	private void write(byte[] data, String path) throws IOException {
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(path);
			fos.write(data);
		} finally {
			if(fos != null) fos.close();
		}
	}

	private String getFullPath(String path) {
		return getClass().getResource(path).getPath();
	}

	private byte[] read(String path) throws IOException {
		FileInputStream fis = null;
		ByteArrayOutputStream b = null;
		BufferedOutputStream bos = null;
		try {
			fis = new FileInputStream(path);
			b = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(b);

			int c;
			while ((c = fis.read()) != -1){
				bos.write(c);
			}
			bos.flush();
			return b.toByteArray();
		} finally {
			if(fis != null) fis.close();
			if(bos != null) bos.close();
			if(b != null) b.close();
		}

	}
}
