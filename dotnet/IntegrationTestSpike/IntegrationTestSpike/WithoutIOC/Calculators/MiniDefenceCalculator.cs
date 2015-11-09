using IntegrationTestSpike.WithoutIOC.Models;

namespace IntegrationTestSpike.WithoutIOC.Calculators
{
    public class MiniDefenceCalculator : Calculator
    {
        public override int Calculate()
        {
            return 3;
        }

        public override TowerType TowerType
        {
            get { return TowerType.MiniDenfence;}
        }
    }
}