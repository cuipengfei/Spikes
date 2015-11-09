using IntegrationTestSpike.WithIOC.Models;

namespace IntegrationTestSpike.WithIOC.Calculators
{
    public abstract class Calculator
    {
        public abstract TowerType TowerType { get; }
        public abstract int Calculate();
    }
}