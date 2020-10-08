package breakingumbrella.connectit.data.repositories.boundaries;

public interface OnFailure<T extends Exception> {
    void fail(T exc);
}
