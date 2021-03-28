package source;
import position.Position;
import tad_lista_de_nodos.PositionList;

// Interface para um nodo de uma árvore. O nodo da árvore 
// mantêm um elemento, um nodo pai, um nodo para a filhos
// como um TAD Lista de Nodos.
public interface TreePosition<E> extends Position<E> {
// Define o elemento a ser armazenado nesta posição
public void setElement(E o);
// Retorna o elemento armazenado nesta posição
public E getElement();
// Retorna os filhos desta posição
public PositionList<Position<E>> getChildren();
// Define os filhos desta posição
public void setChildren(PositionList<Position<E>> c);
// Retorna o pai desta posição
public TreePosition<E> getParent();
// Define o pai desta posição
public void setParent(TreePosition<E> v);
}
