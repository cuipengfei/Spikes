using IntegrationTestSpike.WithoutIOC.Models;

namespace IntegrationTestSpike.WithoutIOC.Calculators
{
    public abstract class Calculator
    {
        public abstract int Calculate();

        public abstract TowerType TowerType { get; }
    }
}