package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import position.Position;
import source.LinkedBinaryTree;
import java.util.Iterator;
//import source.BTNode;
import source.BTPosition;
import exceptions.NonEmptyTreeException;
//import tad_lista_de_nodos.NodePositionList;
//import tad_lista_de_nodos.PositionList;

class LinkedBinaryTreeTest {

	@Test
	void test() {
		Position<String> raiz, esquerda1, direita1, direita2, direita3, nova_direita;
 		LinkedBinaryTree<String> T = criarArvore();
 		LinkedBinaryTree<String> T1, T2, pilha;
 		Iterable<Position<String>> lista, inorder, posicoes;
 		Iterator<String> iterador;
 		String arvore;
 		
 		// Testa se a árvore binária não está vazia.
		assertFalse(T.isEmpty());
		
		// Testa se o método que retorna o tamanho da árvore binária está funcionando.
		assertEquals(19, T.size(), "Deve retornar 19");
		
		// Define a posição raiz sendo a raiz de T e testa o método que verifica se uma posição é a raiz da árvore.
		raiz = T.root();
		assertTrue(T.isRoot(raiz));
		
		// Testa método que verifica se dado um nodo, ele possui filho a esquerda.
		assertTrue(T.hasLeft(raiz));
		
		// Atribui o filho da esquerda da posição raiz à variável esquerda1 e testa se é um nó externo (folha).
		esquerda1 = T.left(raiz);
		assertFalse(T.isExternal(esquerda1));
		
		// Testa o lançamento da exceção caso uma raiz tente ser adicionada com uma raiz já existente na árvore.
		assertThrows(NonEmptyTreeException.class, () -> {T.addRoot("+"); });
		
		// Testa método que verifica se dado um nodo, ele possui filho a direita.
		assertTrue(T.hasRight(esquerda1));
		
		// Atribui o filho da esquerda da posição raiz à variável direita2 e testa se é um nó interno.
		direita2 = T.right(esquerda1);
		assertTrue(T.isInternal(direita2));
		
		// Testa se o elemento do nodo direita2 é o símbolo "+".
		assertEquals("+", direita2.element(), "Deve retornar a string +");
		
		// Testa se o método replace está substituindo o valor do elemento.
		String temp;
		temp = T.replace(direita2, "X");
		assertEquals("+", temp, "Deve retornar a string +");
		assertEquals("X", direita2.element(), "Deve retornar a string X");
		
		// Atribui a variável direita1 o irmão da variável esquerda1.
		direita1 = T.sibling(esquerda1);
		assertEquals("+", direita1.element(), "Deve retornar a string +");
		
		// Testa se o método que retorna o pai de um nodo está funcionando.
		assertEquals(raiz, T.parent(direita1));
		
		// Remove o nodo externo da direita da variável direita1.
		String el;
		direita3 = T.right(direita1);
		assertTrue(T.isExternal(direita3));
		el = T.remove(direita3);
		assertFalse(T.hasRight(direita1));
		assertEquals("6", el, "Deve retornar a string 6");
		
		// Testa o método que anexa duas subárvores a um nodo externo.
		T1 = new LinkedBinaryTree<String>();
		T1.addRoot("8");
		T2 = new LinkedBinaryTree<String>();
		T2.addRoot("2");
		nova_direita = T.insertRight(direita1, "+");
		assertTrue(T.isExternal(nova_direita));
		T.attach(nova_direita, T1, T2);
		assertFalse(T.isExternal(nova_direita));
		
		// Testa se o método children está retornando os filhos de um dado nodo.
		lista = T.children(raiz);
		assertEquals("[/, +]", iterableToString(lista), "Deve retornar [/, +]");
	
		// Testa o método que retorna árvore, percorrendo um caminho prefixado desde a raiz. 
	    inorder = T.positionsInorder();
		assertEquals("[-, /, X, +, 3, 1, 3, X, -, 9, 5, 2, +, X, 3, -, 7, 4, +, 8, 2]", iterableToString(inorder));
		
		// Testa o método que retorna as posições da árvore, percorrendo um caminho prefixado desde a raiz. 
		posicoes = T.positions();
		assertEquals("[-, /, X, +, 3, 1, 3, X, -, 9, 5, 2, +, X, 3, -, 7, 4, +, 8, 2]", iterableToString(posicoes));
		
		// Testa o método que retorna o iterador das posições da árvore.
		iterador = T.iterator();
		assertEquals("[-, /, X, +, 3, 1, 3, X, -, 9, 5, 2, +, X, 3, -, 7, 4, +, 8, 2]", iteratorToString(iterador));
		
		// Testa o método build expression que recebe uma expressão e retorna a árvore desta expressão
		pilha = LinkedBinaryTree.buildExpression("((((3+1)*3)/((9-5)+2))-((3*(7-4))+6))");	
		posicoes = pilha.positions();
		assertEquals("[-, /, *, +, 3, 1, 3, +, -, 9, 5, 2, +, *, 3, -, 7, 4, 6]", iterableToString(posicoes));
		
		// Testa o método de caminhamento pré-fixado para a árvore.
		arvore = T.binaryPreorder(T, raiz);
		assertEquals("-/X+313X-952+X3-74+82", arvore);
		
		// Testa o método de caminhamento pós-fixado para a árvore.
		arvore = T.binaryPostorder(T, raiz);
		assertEquals("31+3X95-2X/374-X82++-", arvore);
		
		// Testa o método que retorna o cálculo das expressões da árvore.
		double resultado = T.evaluateExpression(pilha, pilha.root());
		assertEquals(-13.0, resultado, "Deve ser -13.0");
	}
	
	public String iteratorToString(Iterator<String> iterador) {
		String s = "";
		while(iterador.hasNext()) {
			s += ", " + iterador.next();
		};
		s = (s.length() == 0 ? s : s.substring(2));
		s = "[" + s + "]";
		return s;
	}
	
	public String iterableToString(Iterable<Position<String>> lista) {
		String s = "";
		for (Position<String> i : lista) { s += ", " + i.element(); }
		s = (s.length() == 0 ? s : s.substring(2));
		s = "[" + s + "]";
		return s;
	}
	
	public Position<String> criarFilho(LinkedBinaryTree<String> T, Position<String> parent, String e, String direction){
		Position<String> nodo;
		Position<String> p = (Position<String>) parent; 
		if (direction == "L") {
			nodo = T.insertLeft(p, e);
		} else if (direction == "R") {
			nodo = T.insertRight(p, e);
		} else {
			nodo = null;
		}
		return nodo;
	}
	
	public LinkedBinaryTree<String> criarArvore(){
		LinkedBinaryTree<String> T = new LinkedBinaryTree<String>();
		Position<String> raiz, d1, a3, a1, a2, m1, m2, s1, s2;
		
		T.addRoot("-");
		// Raiz
		raiz =  (BTPosition<String>) T.root();
		
		// Esquerda da raiz
		d1 = criarFilho(T, raiz, "/", "L");
		
		// Esquerda da raiz 1
		m1 = criarFilho(T, d1, "X", "L");
		a2 = criarFilho(T, m1, "+", "L");
		criarFilho(T, a2, "3","L");
		criarFilho(T, a2, "1", "R");
		criarFilho(T, m1, "3", "R");
		
		// Esquerda da raiz 2
		a1 = criarFilho(T, d1, "+", "R");
		s1 = criarFilho(T, a1, "-", "L");
		criarFilho(T, s1, "9", "L");
		criarFilho(T, s1, "5", "R");
		criarFilho(T, a1, "2", "R");
		
		// Direita da raiz
		a3 = criarFilho(T,raiz, "+", "R");
		
		// Direita da raiz 1
		m2 = criarFilho(T, a3, "X", "L");
		criarFilho(T, m2, "3", "L");
		s2 = criarFilho(T, m2, "-", "R");
		criarFilho(T, s2, "7", "L");
		criarFilho(T, s2, "4", "R");
		
		// Direita da raiz 2
		criarFilho(T, a3, "6", "R");
		
		return T;
	}

}
