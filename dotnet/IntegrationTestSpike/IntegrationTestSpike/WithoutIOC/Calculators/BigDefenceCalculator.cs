using IntegrationTestSpike.WithoutIOC.Models;

namespace IntegrationTestSpike.WithoutIOC.Calculators
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