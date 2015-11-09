using IntegrationTestSpike.WithoutIOC.Models;

namespace IntegrationTestSpike.WithoutIOC.Calculators
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