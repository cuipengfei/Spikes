namespace IntegrationTestSpike.TowerDemo.Providers
{
    public abstract class DataProvider<T>
    {
        public abstract T ReadData();
    }
}