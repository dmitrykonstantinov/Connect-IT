package breakingumbrella.connectit.data.repositories.boundaries;

public interface OnSuccess<TResult> {
    void success(TResult result);
}