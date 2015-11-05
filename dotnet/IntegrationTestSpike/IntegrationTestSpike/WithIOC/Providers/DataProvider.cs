namespace IntegrationTestSpike.WithIOC.Providers
{
    public abstract class DataProvider<T>
    {
        public abstract T ReadData();
    }
}