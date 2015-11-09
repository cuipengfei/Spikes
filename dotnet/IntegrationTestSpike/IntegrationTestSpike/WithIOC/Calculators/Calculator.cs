using IntegrationTestSpike.WithIOC.Models;

namespace IntegrationTestSpike.WithIOC.Calculators
{
    public abstract class Calculator
    {
        public abstract int Calculate();

        public abstract TowerType TowerType { get; }
    }
}