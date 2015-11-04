namespace IntegrationTestSpike.HardMode.Providers
{
    internal abstract class DataProvider<T>
    {
        public abstract T ReadData();
    }
}