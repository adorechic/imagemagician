package imagemagician;

/**
 * Magician例外クラス
 * @author adorechic
 */
public class MagicianException extends RuntimeException{
	public MagicianException(String message) {
		super(message);
	}

	public MagicianException(String message, Throwable t) {
		super(message,t);
	}

	public MagicianException(Throwable t) {
		super(t);
	}
}
