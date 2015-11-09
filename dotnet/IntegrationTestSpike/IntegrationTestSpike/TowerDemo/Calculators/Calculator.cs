using IntegrationTestSpike.TowerDemo.Models;

namespace IntegrationTestSpike.TowerDemo.Calculators
{
    public abstract class Calculator
    {
        public abstract TowerType TowerType { get; }
        public abstract int Calculate();
    }
}