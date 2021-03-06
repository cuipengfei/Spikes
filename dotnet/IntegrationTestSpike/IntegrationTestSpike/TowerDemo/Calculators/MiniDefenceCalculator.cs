﻿using IntegrationTestSpike.TowerDemo.Models;

namespace IntegrationTestSpike.TowerDemo.Calculators
{
    public class MiniDefenceCalculator : Calculator
    {
        public override TowerType TowerType
        {
            get { return TowerType.MiniDenfence; }
        }

        public override int Calculate()
        {
            return 3;
        }
    }
}