using IntegrationTestSpike.WithIOC.Models;

namespace IntegrationTestSpike.WithIOC.Calculators
{
    public class NormalTowerCalculator : Calculator
    {
        public override TowerType TowerType
        {
            get { return TowerType.Normal; }
        }

        public override int Calculate()
        {
            return 5;
        }
    }
}