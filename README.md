imagemagician
======================
im4javaライクなJava用imagemagickクライアントです。
ProcessBuilderによるコマンド発行を利用し非同期に処理を行います。

使い方
------

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

