using IntegrationTestSpike.TowerDemo.Models;

namespace IntegrationTestSpike.TowerDemo.Calculators
{
    public class BigAttackCalculator : Calculator
    {
        public override TowerType TowerType
        {
            get { return TowerType.BigAttack; }
        }

        public override int Calculate()
        {
            return 10;
        }
    }
}