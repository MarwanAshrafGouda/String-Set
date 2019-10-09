import java.util.LinkedHashMap;

public interface StringSetInterface {

    void union(StringSet S, LinkedHashMap<String, Integer> U);

    void intersect(StringSet S, LinkedHashMap<String, Integer> U);

    void complement(LinkedHashMap<String, Integer> U);

}
