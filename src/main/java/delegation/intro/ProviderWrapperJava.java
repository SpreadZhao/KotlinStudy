package delegation.intro;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProviderWrapperJava<T> implements IPLDataProvider<T> {

    private final IPLDataProvider<T> realProvider;

    ProviderWrapperJava(IPLDataProvider<T> realProvider) {
        this.realProvider = realProvider;
    }

    @Override
    public void queryData() {
        realProvider.queryData();
    }

    @NotNull
    @Override
    public List<T> getList() {
        return realProvider.getList();
    }
}
