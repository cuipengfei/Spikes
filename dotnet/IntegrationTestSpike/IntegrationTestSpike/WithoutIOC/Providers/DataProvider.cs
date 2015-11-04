namespace IntegrationTestSpike.WithoutIOC.Providers
{
    public abstract class DataProvider<T>
    {
        public abstract T ReadData();
    }
}