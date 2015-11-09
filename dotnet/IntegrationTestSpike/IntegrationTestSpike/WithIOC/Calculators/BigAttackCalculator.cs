using IntegrationTestSpike.WithIOC.Models;

namespace IntegrationTestSpike.WithIOC.Calculators
{
    public class BigAttackCalculator:Calculator
    {
        public override int Calculate()
        {
            return 10;
        }

        public override TowerType TowerType { get{return TowerType.BigAttack;}  }
    }
}