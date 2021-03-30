package tad_lista_de_nodos;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import position.Position;

/* Uma classe iterator Simples para listas. Os elementos da clise são retornados por
* este iterator. Nenhuma cópia da lista é realizada, de forma que qualquer mudança
* na lista é refletida por este iterator. */

public class ElementIterator<E> implements Iterator<E> {
	protected PositionList<E> list;
	protected Position<E> cursor;

	// Cria um elemento iterator
	public ElementIterator(PositionList<E> L) {
		list = L;
		cursor = (list.isEmpty()) ? null : list.first();
	}

	// Retorna se o iterator tem ou não um próximo objeto.
	public boolean hasNext() { return (cursor != null); }

	// Retorna o próximo objeto do iterator.
	public E next() throws NoSuchElementException {
		if (cursor == null) throw new NoSuchElementException("No next element");
		E toReturn = cursor.element();
		cursor = (cursor == list.last()) ? null : list.next(cursor);
		return toReturn;
	}

	// Dispara um {@link UnsupportedOperationException} para todos os casos, porque
	// a remoção não é uma operação suportada por este iterator.
	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("remove");
	}
}