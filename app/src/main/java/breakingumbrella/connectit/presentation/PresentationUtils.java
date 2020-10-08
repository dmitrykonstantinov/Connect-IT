package breakingumbrella.connectit.presentation;

public abstract class PresentationUtils {

    public static int linearize(int i, int j, int fieldYSize) {
        return (i * fieldYSize) + j;
    }

    public static int[] deLinearize(int position, int sizeY) {
        return new int[]{position / sizeY, position % sizeY};
    }

}
