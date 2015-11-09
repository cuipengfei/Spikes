using IntegrationTestSpike.WithoutIOC.Models;

namespace IntegrationTestSpike.WithoutIOC.Calculators
{
    public abstract class Calculator
    {
        public abstract TowerType TowerType { get; }
        public abstract int Calculate();
    }
}