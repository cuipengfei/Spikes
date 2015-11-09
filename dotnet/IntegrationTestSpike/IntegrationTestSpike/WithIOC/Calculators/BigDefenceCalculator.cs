using IntegrationTestSpike.WithIOC.Models;

namespace IntegrationTestSpike.WithIOC.Calculators
{
    public class BigDefenceCalculator:Calculator
    {
        public override int Calculate()
        {
            return 2;
        }

        public override TowerType TowerType
        {
            get { return TowerType.BigDenfence; }
        }
    }
}