using IntegrationTestSpike.TowerDemo.Models;

namespace IntegrationTestSpike.TowerDemo.Calculators
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