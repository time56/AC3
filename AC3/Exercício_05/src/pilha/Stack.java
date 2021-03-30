package pilha;

/* Interface para uma pilha: uma coleção de objetos
 * que são inseridos e removidos de acordo com o princípio de o último que entra é o 
 * primeiro que sai. Esta interface inclui os principais métodos do Java.util.Stack
 * 
 * @author Roberto Tamassia
 * @author Michael Goodrich
 * @see EmptyStackException */
public interface Stack<E> {
	// Retorna o número de elementos na pilha.
	public int size();
	
	// @return True se a pilha é vazia, False em caso contrário.
	public boolean isEmpty();
	
	// @return o elemento que está no topo da pilha.
	// @exception EmptyStackException se a pilha estiver vazia.
	public E top() throws EmptyStackException;
	
	// @param elemento a ser inserido
	public void push(E element);
	
	// @return elemento removido.
	// @exception EmptyStackException se a pilha estiver vazia.
	public E pop() throws EmptyStackException;
}
