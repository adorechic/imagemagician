package imagemagician;

/**
 * ImageMagickの実行結果を表現するFutureインターフェイス
 * @author adorechic
 */
public interface MagickFuture {
	/**
	 * 変換処理が終了するまで待機し、変換された画像を返します。
	 * @throws MagicianException
	 * @return
	 */
	byte[] get();
}
