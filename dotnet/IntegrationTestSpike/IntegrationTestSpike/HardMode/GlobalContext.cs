using System.Collections.Generic;
using IntegrationTestSpike.HardMode.Models;

namespace IntegrationTestSpike.HardMode
{
    internal class GlobalContext
    {
        public static readonly GlobalContext Instance = new GlobalContext();

        private GlobalContext()
        {
            LastId = 10;
            MinimumDefence = 2;
        }

        public int LastId { get; set; }

        public List<Land> ExistingLands { get; set; }
        public List<Tower> ExistingTowers { get; set; }
        public int MinimumDefence { get; set; }
    }
}