using System.Collections.Generic;
using IntegrationTestSpike.WithIOC.Models;
using IntegrationTestSpike.WithIOC.Providers;

namespace IntegrationTestSpike.WithIOC.Steps
{
    public abstract class BaseStep
    {
        protected readonly GlobalContext _context;

        protected BaseStep(GlobalContext context)
        {
            _context = context;
        }

        public DataProvider<List<Land>> LandDataProvider { get; set; }
        public DataProvider<List<Tower>> TowerDataProvider { get; set; }

        public abstract void Do();
    }
}