using IntegrationTestSpike.WithIOC.Models;

namespace IntegrationTestSpike.WithIOC.Calculators
{
    public class BigDefenceCalculator : Calculator
    {
        public override TowerType TowerType
        {
            get { return TowerType.BigDenfence; }
        }

        public override int Calculate()
        {
            return 2;
        }
    }
}