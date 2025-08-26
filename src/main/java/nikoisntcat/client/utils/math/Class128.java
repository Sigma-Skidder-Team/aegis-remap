package nikoisntcat.client.utils.math;

import java.util.List;

import org.joml.Vector3i;

public enum Class128 {
    field1497(new Vector3i(0, 1, 0)),
    field1498(new Vector3i(0, 0, 0)),
    field1499(new Vector3i(0, 0, -1)),
    field1500(new Vector3i(1, 0, 0)),
    field1503(new Vector3i(0, 0, 1)),
    field1504(new Vector3i(-1, 0, 0)),
    field1505(null),
    field1506(new Vector3i(0, -1, 0));

    public final Vector3i field1502;

    Class128(Vector3i var3) {
        this.field1502 = var3;
    }

    public static List method1019() {
        return new Class137();
    }

    public static List method1020() {
        return new Class138();
    }
}
