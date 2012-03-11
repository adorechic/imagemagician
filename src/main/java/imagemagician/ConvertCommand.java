package imagemagician;

import java.util.List;

/**
 * convertコマンドを実行するためのDTOクラス
 * @author adorechic
 */
public interface ConvertCommand {
	/**
	 * 変換元の画像データをセットします。
	 * @param src - データ本体のbyte配列
	 * @param type - 画像タイプ
	 * @return
	 */
	ConvertOption src(byte[] src, ImageType type);

	/**
	 * {@link ProcessBuilder}の引数となるコマンドを返します。
	 * @return
	 */
	List<String> getCommand();

	/**
	 * 一時ファイルを削除します。
	 */
	void clean();
}
