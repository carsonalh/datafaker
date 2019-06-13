package general;

/**
 * An object for listening for events.
 *
 * @param <T> The type of data given by the event.
 */
public interface Listener<T> {
    /**
     * The method to be called on firing of the event.
     *
     * @param data The data given by the event.
     */
    void onSubmit(T data);
}
