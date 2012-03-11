package imagemagician;

import java.io.*;
import java.util.concurrent.*;

/**
 * @author adorechic
 */
public class Magician implements MagickFuture {
	private Process process;
	private FutureTask<byte[]> productOut;
	private FutureTask<byte[]> errorOut;
	private ConvertCommand command;

	public Magician() {}

	private Magician(ConvertCommand command) {
		this.command = command;
	}

	public MagickFuture convert(ConvertCommand command) {
		try {
			return new Magician(command).startProcess();
		} catch (IOException e) {
			throw new MagicianException(e);
		}
	}

	private MagickFuture startProcess() throws IOException {
		this.process = new ProcessBuilder(command.getCommand()).start();
		this.productOut = listens(process.getInputStream());
		this.errorOut = listens(process.getErrorStream());
		new Thread(productOut).start();
		new Thread(errorOut).start();

		return this;
	}

	private File destTmpFile(byte[] src, String ext) throws IOException {
		FileOutputStream fos = null;
		try {
			File tmpFile = File.createTempFile("imagemagician-", "." + ext, MagicianConfig.DATA_DIR);
			fos = new FileOutputStream(tmpFile);
			fos.write(src);
			return tmpFile;
		} finally {
			if (fos != null) fos.close();
		}
	}

	private FutureTask<byte[]> listens(final InputStream is) {
		return new FutureTask<byte[]>(
				new Callable<byte[]>() {
					@Override
					public byte[] call() throws Exception {
						return consumeOutput(is);
					}
				}
		);
	}

	private byte[] consumeOutput(InputStream inputStream) throws IOException {
		BufferedInputStream bis = null;
		ByteArrayOutputStream b = null;
		BufferedOutputStream bo = null;
		try {
			bis = new BufferedInputStream(inputStream, MagicianConfig.BUFFER_SIZE);
			b = new ByteArrayOutputStream();
			bo = new BufferedOutputStream(b);
			int c;
			while ((c = inputStream.read()) != -1) {
				bo.write(c);
			}
			bo.flush();
			return b.toByteArray();
		} finally {
			if (bis != null) bis.close();
			if (bo != null) bo.close();
			if (b != null) b.close();
		}
	}

	@Override
	public byte[] get() {
		String error = null;
		try {
			error = new String(errorOut.get());
			byte[] dst = productOut.get();
			process.waitFor();

			int rc = process.exitValue();
			if (rc != 0) {
				StringBuilder sb = new StringBuilder("rc=").append(rc);
				if (error != null && error.length() != 0) {
					sb.append("error=").append(error);
				}
				throw new MagicianException(sb.toString());
			}

			return dst;
		} catch (Exception e) {
			throw new MagicianException(error, e);
		} finally {
			try {
				command.clean();
				process.getInputStream().close();
				process.getErrorStream().close();
				process.getOutputStream().close();
			} catch (IOException e) {
				throw new MagicianException(command.toString(), e);
			}
		}
	}

}
