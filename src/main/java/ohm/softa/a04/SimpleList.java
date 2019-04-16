package ohm.softa.a04;

import java.util.Iterator;
import java.util.function.Function;

public interface SimpleList<T> extends Iterable<T> {

	/**
	 * Add a given object to the back of the list.
	 */
	void add(T o);

	/**
	 * @return current size of the list
	 */
	int size();

	/**
	 * Generate a new list using the given filter instance.
	 * @return a new, filtered list
	 */
	default SimpleList<T> filter(SimpleFilter<T> filter) {
		SimpleList<T> result = new SimpleListImpl<T>();
		for(T o : this){
			if(filter.include(o)){
				result.add(o);
			}
		}
		return result;
	}

	default void addDefault(Class<T> clazz) throws IllegalAccessException, InstantiationException {
		this.add(clazz.newInstance());
	}

	default <R> SimpleList<R> map(Function<T,R> transform) {
		SimpleList<R> result;

		try {
			result = (SimpleList<R>) getClass().newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			result = new SimpleListImpl<R>();
		} catch (InstantiationException e) {
			e.printStackTrace();
			result = new SimpleListImpl<R>();
		}

		for(T o : this) {
			result.add(transform.apply(o));
		}

		return result;
	}
}
