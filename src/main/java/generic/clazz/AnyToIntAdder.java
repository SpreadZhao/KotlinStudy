package generic.clazz;

public class AnyToIntAdder<IN extends Number> implements IPowerfulAdder<IN, Integer> {
    @Override
    public Integer add(IN arg1, IN arg2) {
        if (arg1 instanceof Double && arg2 instanceof Double) {
            return ((Double) ((Double) arg1 + (Double) arg2)).intValue();
        } else {
            return (Integer) arg1 + (Integer) arg2;
        }
    }
}
