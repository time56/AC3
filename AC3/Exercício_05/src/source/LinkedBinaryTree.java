package source;
import java.util.Iterator;
import exceptions.BoundaryViolationException;
import exceptions.EmptyTreeException;
import exceptions.InvalidPositionException;
import exceptions.NonEmptyTreeException;
import pilha.ArrayStack;
import position.Position;
import tad_lista_de_nodos.NodePositionList;
import tad_lista_de_nodos.PositionList;

// Implementação da interface BinaryTree usando uma estrutura encadeada.
public class LinkedBinaryTree<E> implements BinaryTree<E>{
	protected BTPosition<E> root; // referência para a raiz
	protected int size; // número de nodos
	
	// Cria uma árvore binária vazia.
	public LinkedBinaryTree() {
		root = null; // inicia com a árvore vazia.
		size = 0;
	}
	
	// Retorna o número de nodos da árvore.
	public int size() {
		return size;
	}
	
	// Retorna se o nodo é um interno.
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		checkPosition(v); // método auxiliar
		return (hasLeft(v) || hasRight(v));
	}
	
	// Retorna se um nodo é a raiz.
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return (v == root());
	}
	
	// Retorna se um nodo tem o filho da esquerda.
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		return (vv.getLeft() != null);
	}
	
	// Retorna a raiz da árvore.
	public Position<E> root() throws EmptyTreeException {
		if (root == null) throw new EmptyTreeException("The tree is empty.");
		return root;
	}
	
	// Retorna o filho da esquerda de um nodo.
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException{
		BTPosition<E> vv = checkPosition(v);
		Position<E> leftPos = (Position<E>) vv.getLeft();
		if (leftPos == null) throw new BoundaryViolationException("No left child");
		return leftPos;
	}
	
	// Retorna o pai de um nodo.
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> parentPos = (Position<E>) vv.getParent();
		if (parentPos == null) throw new BoundaryViolationException("No parent");
		return parentPos;
	}
	
	// Retorna uma coleção iterável contendo os filhos de um nodo.
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		PositionList<Position<E>> children = new NodePositionList<Position<E>>();
		if (hasLeft(v)) children.addLast(left(v));
		if (hasRight(v)) children.addLast(right(v));
		return children;
	}
	
	// Retorna uma coleção iterável (inorder) contendo os nodos da árvore.
	public Iterable<Position<E>> positionsInorder(){
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		if (size != 0) preorderPositions(root(), positions); // atribui as posições usando caminhamento prefixado 
		return positions;
	}
	
	// Retorna uma coleção iterável contendo os nodos da árvore.
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		if (size != 0) preorderPositions(root(), positions); // atribui as posições usando caminhamento prefixado
		return positions;
	}
	
	// Retorna um iterador sobre os elementos armazenados nos nodos
	public Iterator<E> iterator(){
		Iterable<Position<E>> positions = positions();
		PositionList<E> elements = new NodePositionList<E>();
		for (Position<E> pos : positions) elements.addLast(pos.element());
		return elements.iterator(); // um iterator sobre os elementos
	}
	
	// Substitui o elemento armazenado no nodo.
	public E replace(Position<E> v, E o) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		E temp = v.element();
		vv.setElement(o);
		return temp;
	}
	
	// Método de acesso adicional
	// Retorna o irmão de um nodo
	public Position<E> sibling(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> parentPos = vv.getParent();
		if(parentPos != null) {
			BTPosition<E> sibPos;
			BTPosition<E> leftPos = parentPos.getLeft();
			if (leftPos == vv) sibPos = parentPos.getRight();
			else sibPos = parentPos.getLeft();
			if (sibPos != null) return sibPos;
		}
		throw new BoundaryViolationException("No sibling");
	}
	
	// Métodos de acesso adicionais
	// Insere a raiz em uma árvore vazia
	public Position<E> addRoot(E e) throws NonEmptyTreeException {
		if (!isEmpty()) throw new NonEmptyTreeException("Tree already has a root");
		size = 1;
		root = createNode(e, null, null, null);
		return root;
	}
	
	// Insere o filho da esquerda em um nodo.
	public BTPosition<E> insertLeft(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> leftPos = (Position<E>) vv.getLeft();
		if (leftPos != null) throw new InvalidPositionException("Node already has a left child");
		BTPosition<E> ww = createNode(e, vv, null, null);
		vv.setLeft(ww);
		size++;
		return ww;
	}
	
	// Insere o filho da direita em um nodo.
	public BTPosition<E> insertRight(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> rightPos = (Position<E>) vv.getRight();
		if (rightPos != null) throw new InvalidPositionException("Node already has a left child");
		BTPosition<E> ww = createNode(e, vv, null, null);
		vv.setRight(ww);
		size++;
		return ww;
	}
	
	// Remove um nodo com zero ou um filho.
	public E remove(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> leftPos = vv.getLeft();
		BTPosition<E> rightPos = vv.getRight();
		if (leftPos != null && rightPos != null) throw new InvalidPositionException("Cannot remove node with two children");
		BTPosition<E> ww; // o único filho de v, se houver
		if (leftPos != null) ww = leftPos;
		else if(rightPos != null) ww = rightPos;
		else // v é folha
			ww = null;
		if (vv == root) { // v é a raiz
			if (ww != null) ww.setParent(null);
			root = ww;
		} else { // v não é raiz
			BTPosition<E> uu = vv.getParent();
			if (vv == uu.getLeft()) uu.setLeft(ww);
			else uu.setRight(ww);
			if(ww != null) ww.setParent(uu);
		}
		size--;
		return v.element();
	}
	
	// Conecta duas árvores para serem subárvores de um nodo externo.
	public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		if (isInternal(v)) throw new InvalidPositionException("Cannot attach from internal node");
		if(!T1.isEmpty()) {
			BTPosition<E> r1 = checkPosition(T1.root());
			vv.setLeft(r1);
			r1.setParent(vv); // T1 deve ser invalidada 
		}
		if (!T2.isEmpty()) {
			BTPosition<E> r2 = checkPosition(T2.root());
			vv.setRight(r2);
			r2.setParent(vv); // T2 deve ser invalidada
		}
	}

	// Se v é um nodo de árvore binária, converte para BTPosition, caso contrário lança exceção.
	protected BTPosition<E> checkPosition(Position<E> v) throws InvalidPositionException{
		if (v == null || !(v instanceof BTPosition)) throw new InvalidPositionException("The position is invalid");
		return (BTPosition<E>) v;
	}
	
	// Cria um novo nodo de uma árvore binária
	protected BTPosition<E> createNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right){
		return new BTNode<E>(element, parent, left, right);
	}
	
	// Cria uma lista que armazena os nodos da subárvore de um nodo ordenados de acordo com o 
	// caminhamento prefixado da subárvore.
	protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException{
		pos.addLast(v);
		if (hasLeft(v)) preorderPositions(left(v), pos); // Recursão sobre o filho da esquerda
		if (hasRight(v)) preorderPositions(right(v), pos); // Recursão sobre o filho da direita
	}
	
	public boolean isEmpty() { 
		return (size == 0); 
	}
	
	public boolean isExternal(Position<E> v) throws InvalidPositionException { 
		return !isInternal(v);
	}
	
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> rightPos = (Position<E>) vv.getRight();
		if (rightPos == null) throw new BoundaryViolationException("No right child");
		return rightPos;
	}
	
	// Retorna se um nodo tem o filho da direita.
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		return (vv.getRight() != null);
	}
	
	// Recebe uma expressão e retorna uma árvore binária
	public static LinkedBinaryTree<String> buildExpression(String expressao) {
		String s;
		ArrayStack<LinkedBinaryTree<String>> pilha = new ArrayStack<LinkedBinaryTree<String>>();
		LinkedBinaryTree<String> T = new LinkedBinaryTree<String>();
		
		for (int i = 0; i < expressao.length(); i++) {
			if ((expressao.charAt(i) != '(') && (expressao.charAt(i) != ')')) {
				s = String.valueOf(expressao.charAt(i));
				LinkedBinaryTree<String> T0 = new LinkedBinaryTree<String>();
				T0.addRoot(s);
				pilha.push(T0);
			} else if(expressao.charAt(i) == '(') {
				
			} else {
				LinkedBinaryTree<String> T1 = new LinkedBinaryTree<String>();				
				LinkedBinaryTree<String> T2 = new LinkedBinaryTree<String>();
				
				T2 = pilha.pop();
				T = pilha.pop();
				T1 = pilha.pop();
				T.attach(T.root(), T1, T2);
				pilha.push(T);
			}
		}
		return pilha.pop();
	}
	
	// Método que faz o caminhamento pré-fixado da árvore.
	public String binaryPreorder(LinkedBinaryTree<E> T, Position<E> v) {
		String s = "";
		s += v.element();
		for (Position<E> i: T.children(v)) {
			if (T.hasLeft(v)) {
				s += T.binaryPreorder(T, i);
			} else if(T.hasRight(v)) {
				s += T.binaryPreorder(T, i);
			}
		}
		return s;
	}
	
	// Método que faz o caminhamento pós-fixado da árvore.
	public String binaryPostorder(LinkedBinaryTree<E> T, Position<E> v) {
		String s = "";
		for (Position<E> i: T.children(v)) {
			if (T.hasLeft(v)) {
				s += T.binaryPostorder(T, i);
			} else if(T.hasRight(v)) {
				s += T.binaryPostorder(T, i);
			}
		}
		s += v.element();
		return s;
	}
	
	// Método que avalia a árvore e retorna o resultado do cálculo da expressão que a árvore representa.
	public double evaluateExpression(LinkedBinaryTree<E> T, Position<E> v) {
		double  x, y;
		String operador;
		
		if (T.isInternal(v)) {
			operador = (String) v.element();
			x = evaluateExpression(T, T.left(v));
			y = evaluateExpression(T, T.right(v));
			switch (operador) {
			case "+":
				return x + y;
			case "-":
				return x - y;
			case "/":
				return x / y;
			case "*":
				return x * y;
			case "X":
				return x * y;
			default:
				return Double.parseDouble((String) v.element());
			}
			} else {
			return Double.parseDouble((String) v.element());
		}
	}
}
