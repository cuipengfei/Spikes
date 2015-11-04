namespace IntegrationTestSpike.HardMode.Providers
{
    public abstract class DataProvider<T>
    {
        public abstract T ReadData();
    }
}