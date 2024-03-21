package generic.clazz;

public class IntToStringAdder implements IPowerfulAdder<Integer, String> {
    @Override
    public String add(Integer arg1, Integer arg2) {
        int res = arg1 + arg2;
        return Integer.toString(res);
    }
}
