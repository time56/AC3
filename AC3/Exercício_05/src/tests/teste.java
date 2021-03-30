package tests;

import java.util.Iterator;

//import pilha.ArrayStack;
import position.Position;
import source.BTPosition;
import source.LinkedBinaryTree;

public class teste {
	public static void main(String[] args) {
		//LinkedBinaryTree<String> T = criarArvore();
		LinkedBinaryTree<String> arvore;
 		//Position<String> raiz;
		Position<String> nodoEsquerda1, nodoEsquerda2, nodoEsquerda3, nodoDireita3, nodoEsquerda4, nodoDireita4, nodoDireita1, nodoDireita2;
		Position<String> nodoEsquerda5, nodoDireita5;
		
		arvore = LinkedBinaryTree.buildExpression("(((5+2)*(2-1))/((2+9)+(7-2)-1))*8)");
		Position<String> raiz = arvore.root();
		nodoEsquerda1 = arvore.left(raiz);
		nodoDireita1 = arvore.right(raiz);
		nodoEsquerda2 = arvore.left(nodoEsquerda1);
		nodoDireita2 = arvore.right(nodoEsquerda1);
		nodoEsquerda3 = arvore.left(nodoEsquerda2);
		nodoDireita3 = arvore.right(nodoEsquerda2);
		nodoEsquerda4 = arvore.left(nodoDireita2);
		nodoDireita4 = arvore.right(nodoDireita2);
		nodoEsquerda5 = arvore.left(nodoEsquerda4);
		nodoDireita5 = arvore.right(nodoEsquerda4);
		//System.out.println("Esquerda: " + arvore.left(nodoDireita1).element());
		System.out.println(iterableToString(arvore.positions()));
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
	
	public static String iterableToString(Iterable<Position<String>> lista) {
		String s = "";
		for (Position<String> i : lista) { s += ", " + i.element(); }
		s = (s.length() == 0 ? s : s.substring(2));
		s = "[" + s + "]";
		return s;
	}
	
	public static Position<String> criarFilho(LinkedBinaryTree<String> T, Position<String> parent, String e, String direction){
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
	
	public static LinkedBinaryTree<String> criarArvore(){
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
	/*
	public static LinkedBinaryTree<String> criarArvore2(){
		LinkedBinaryTree<String> T2 = new LinkedBinaryTree<String>();
		Position<String> raiz, v2, v3;
		
		T2.addRoot("1");
		// Raiz
		raiz =  (BTPosition<String>) T2.root();
		
		// Esquerda da raiz
		v2 = criarFilho(T2, raiz, "2", "L");
		
		// Esquerda da raiz 1
		v3 = criarFilho(T2, raiz, "3", "R");

		
		return T2;
	}*/
}
