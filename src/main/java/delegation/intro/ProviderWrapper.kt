package delegation.intro

class ProviderWrapper<T>(realProvider: IPLDataProvider<T>) : IPLDataProvider<T> by realProvider