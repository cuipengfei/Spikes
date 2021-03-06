﻿using System.Collections.Generic;
using IntegrationTestSpike.TowerDemo.Models;

namespace IntegrationTestSpike.TowerDemo
{
    public class GlobalContext
    {
        public static readonly GlobalContext Instance = new GlobalContext();

        private GlobalContext()
        {
            LastId = 1;
            MinimumDefence = 5;
        }

        public int LastId { get; set; }

        public List<Land> ExistingLands { get; set; }
        public List<Tower> ExistingTowers { get; set; }
        public int MinimumDefence { get; set; }
    }
}