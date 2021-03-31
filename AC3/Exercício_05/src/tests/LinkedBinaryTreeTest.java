package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import position.Position;
import source.LinkedBinaryTree;
import source.BTPosition;


class LinkedBinaryTreeTest {

	// Exercício 5
	@Test
	void test() {
 		LinkedBinaryTree<String> T = criarArvore();
 		LinkedBinaryTree<Integer> T2 = criarArvore2();
 		LinkedBinaryTree<String> pilha;
 		Iterable<Position<String>> posicoes;
 		Position<String> raiz;
 		String arvore;
 		
 		raiz = T.root();
 		
 		// Exercício 5A
		// Testa o método build expression que recebe uma expressão e retorna a árvore desta expressão
		pilha = LinkedBinaryTree.buildExpression("((((3+1)*3)/((9-5)+2))-((3*(7-4))+6))");	
		posicoes = pilha.positions();
		assertEquals("[-, /, *, +, 3, 1, 3, +, -, 9, 5, 2, +, *, 3, -, 7, 4, 6]", iterableToString(posicoes));
		
		// Exercício 5B
		// Testa o método de caminhamento pré-fixado para a árvore.
		arvore = T.binaryPreorder(T, raiz);
		assertEquals("-/*+313+-952+*3-746", arvore);
		
		// Exercício 5C
		// Testa o método de caminhamento pós-fixado para a árvore.
		arvore = T.binaryPostorder(T, raiz);
		assertEquals("31+3*95-2+/374-*6+-", arvore);
		
		// Exercício 5D
		// Testa o método que retorna o cálculo das expressões da árvore.
		double resultado = T.evaluateExpression(T, raiz);
		assertEquals(-13.0, resultado, "Deve ser -13.0");
		
		// Exercício 5E
		// Testa o método inorder
		String texto = T.inorder(T, raiz); 
		assertEquals("3+1*3/9-5+2-3*7-4+6", texto);
		
		// Exercício 5F
		// Testa o método makerBTSearch
 		String texto2 = T2.makerBTSearch(T2, T2.root());
 		assertEquals("12, 25, 31, 36, 42, 58, 62, 75, 90", texto2);
 		
 		// Exercício 5G
 		
 		// Exerício 5H
 		// Método euler
 		String euler = T.eulerTour(T, raiz);
 		assertEquals("-/*+333+111+*333*/+-999-555-+222+/-+*333*-777-444-*+666+-", euler);
 		
 		// Exercício 5I
 		
 		// Exercício 5J
 		
 		// Exercício 5K
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
		m1 = criarFilho(T, d1, "*", "L");
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
		m2 = criarFilho(T, a3, "*", "L");
		criarFilho(T, m2, "3", "L");
		s2 = criarFilho(T, m2, "-", "R");
		criarFilho(T, s2, "7", "L");
		criarFilho(T, s2, "4", "R");
		
		// Direita da raiz 2
		criarFilho(T, a3, "6", "R");
		
		return T;
	}

	public static LinkedBinaryTree<Integer> criarArvore2(){
		LinkedBinaryTree<Integer> T = new LinkedBinaryTree<Integer>();
		Position<Integer> raiz, d1, a3, a1, m1, m2;
		
		T.addRoot(58);
		// Raiz
		raiz =  (BTPosition<Integer>) T.root();
		
		// Esquerda da raiz
		d1 = criarFilho2(T, raiz, 31, "L");
		
		// Esquerda da raiz 1
		m1 = criarFilho2(T, d1, 25, "L");
		criarFilho2(T, m1, 12, "L");
		
		// Esquerda da raiz 2
		a1 = criarFilho2(T, d1, 42, "R");
		criarFilho2(T, a1, 36, "L");
		
		// Direita da raiz
		a3 = criarFilho2(T,raiz, 90, "R");
		
		// Direita da raiz 1
		m2 = criarFilho2(T, a3, 62, "L");
		criarFilho2(T, m2, 75, "R");
		
		return T;
	}
	
	public static Position<Integer> criarFilho2(LinkedBinaryTree<Integer> T, Position<Integer> parent, int e, String direction){
		Position<Integer> nodo;
		Position<Integer> p = (Position<Integer>) parent; 
		if (direction == "L") {
			nodo = T.insertLeft(p, e);
		} else if (direction == "R") {
			nodo = T.insertRight(p, e);
		} else {
			nodo = null;
		}
		return nodo;
	}
}
