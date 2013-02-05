package ro.isdc.auth.helper;

/**
 * Entity Helper interface
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
 * @param Entity
 */
public interface EntityHelper<T> {

	T copyFrom(final T entity);

	T copyWithoutPkFrom(final T entity);

	T updateFrom(final T fromentity, T toEntity);

	T createEntityInstance();

	T createRandomEntity();
}
