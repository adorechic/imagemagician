package imagemagician;

/**
 * convertコマンドへのオプションを扱うインターフェイス
 * @author adorechic
 */
public interface ConvertOption {
	ConvertOption define(String str);
	ConvertOption resize(int size);
	ConvertOption resize(int width, int height);
	ConvertOption resize(int width, int height, char c);
	ConvertOption colorSpace(String str);
	ConvertOption autoOrient();
	ConvertOption strip();
	ConvertOption autoDefineJpgSizeOff();

	/**
	 * セットしてある画像データを一時ファイルとして保存し、コマンド実行可能な状態にします。
	 * 実行後は{@link imagemagician.ConvertCommand#clean()}を実行してください。
	 * @throws MagicianException
	 * @return
	 */
	ConvertCommand build();
}
