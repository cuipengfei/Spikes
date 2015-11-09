using IntegrationTestSpike.WithoutIOC.Models;

namespace IntegrationTestSpike.WithoutIOC.Calculators
{
    public class BigDefendsCalculator:Calculator
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