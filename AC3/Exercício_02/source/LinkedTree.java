package source;

import java.util.Iterator;
import exceptions.BoundaryViolationException;
import exceptions.EmptyTreeException;
import exceptions.InvalidPositionException;
import exceptions.NonEmptyTreeException;
import position.Position;
import tad_lista_de_nodos.NodePositionList;
import tad_lista_de_nodos.PositionList;

//Um classe para a árvore ligada onde os nodos têm um número arbitrário de filhos.
public class LinkedTree<E> implements Tree<E> {
	protected TreePosition<E> root; // Referência para a raíz
	protected int size; // Número de Nodos

// Cria uma árvore vazia
	public LinkedTree() {
		root = null; // Inicia uma árvore vazia
		size = 0;
	}

// Retorna um número de nodos da árvore
	public int size() {
		return size;
	}

// Retorna se a árvore está vazia
	public boolean isEmpty() {
		return (size == 0);
	}

// Retorna se um nodo é interno
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		return !isExternal(v);
	}

// Retorna se um nodo é externo
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		return (vv.getChildren() == null) || vv.getChildren().isEmpty();
	}

//Retorna se um nodo é a raíz
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return (v == root());
	}

//Retorna a raíz da árvore
	public TreePosition<E> root() throws EmptyTreeException {
		if (root == null)
			throw new EmptyTreeException("The tree is empty");
		return root;
	}

//Retorna o pai de um nodo
	public TreePosition<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TreePosition<E> vv = checkPosition(v);
		TreePosition<E> parentPos = vv.getParent();
		if (parentPos == null)
			throw new BoundaryViolationException("No parent");
		return parentPos;
	}

//Retorna uma coleção iterável dos filhos de um nodo
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		return vv.getChildren();
	}

//Retorna uma coleção iterável dos nodos da árvore.
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		if (size != 0)
			preorderPositions(root(), positions);
		return positions;
	}

//Retorna um iterator dos elementos armazenados nos nodos
	public Iterator<E> iterator() {
		Iterable<Position<E>> positions = positions();
		PositionList<E> elements = new NodePositionList<E>();
		for (Position<E> pos : positions)
			elements.addLast(pos.element());
		return elements.iterator();
	}

//Troca o elemento de um nodo
	public E replace(Position<E> v, E o) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		E temp = v.element();
		vv.setElement(o);
		return temp;
	}

//Métodos de atualização adicionais
//Adiciona um nodo raíz para uma árvore vazia
	public TreePosition<E> addRoot(E e) throws NonEmptyTreeException {
		if (!isEmpty())
			throw new NonEmptyTreeException("Tree already has a root");
		size = 1;
		root = createNode(e, null, null);
		return root;
	}

//Troca os elementos de dos nodos
	public void swapElements(Position<E> v, Position<E> w) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		TreePosition<E> ww = checkPosition(w);
		E temp = w.element();
		ww.setElement(v.element());
		vv.setElement(temp);
	}

//Métodos auxiliares
//Se v é um bom nodo da árvore, cast para TreePosition, caso contrário, lança exceção
	protected TreePosition<E> checkPosition(Position<E> v) throws InvalidPositionException {
		if (v == null || !(v instanceof TreePosition))
			throw new InvalidPositionException("The position is invalid");
		return (TreePosition<E>) v;
	}

//Cria um novo nodo da árvore
	protected TreePosition<E> createNode(E element, TreePosition<E> parent, PositionList<Position<E>> children) {
		return new TreeNode<E>(element, parent, children);
	}

//Cria uma lista armazenando os nodos das subárvore de um nodo
//ordenado de acordo com a travessia das subárvores
	protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException {
		pos.addLast(v);
		for (Position<E> w : children(v))
			preorderPositions(w, pos);
	}

	public String toString() {
		return toString(this);
	}

	public static <E> String toString(LinkedTree<E> T) {
		String s = "";
		for (E i : T) {
			s += ", " + i;
		}
//OU
//for (Iterator<String> it = T.iterator(); it.hasNext();) {
//s += ", " + it.next();
//}
		s = (s.length() == 0 ? s : s.substring(2));
		return "[" + s + "]";
	}
}
//EXERCÍCIO PARA OS ALUNOS
//Implementar:
//depth
//height1
//height2
//parentheticRepresentation
//toStringPostorder
