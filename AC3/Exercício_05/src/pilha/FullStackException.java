package pilha;

@SuppressWarnings("serial")
public class FullStackException extends RuntimeException {
	public FullStackException(String err) {
		super(err);
	}
}
