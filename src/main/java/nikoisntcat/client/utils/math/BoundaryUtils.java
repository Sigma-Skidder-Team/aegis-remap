package nikoisntcat.client.utils.math;

public class BoundaryUtils {
    public static boolean inBoundary(double mouseX, double mouseY, int x, int y, int x1, int y1) {
        return mouseX > (double)x && mouseX < (double)x1 && mouseY > (double)y && mouseY < (double)y1;
    }
}