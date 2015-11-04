using System.Collections.Generic;
using IntegrationTestSpike.HardMode.Models;
using IntegrationTestSpike.HardMode.Providers;

namespace IntegrationTestSpike.HardMode.Steps
{
    public abstract class BaseStep
    {
        protected readonly GlobalContext _context;
        protected DataProvider<List<Land>> landDataProvider;
        protected DataProvider<List<Tower>> towerDataProvider;

        public BaseStep(GlobalContext context)
        {
            _context = context;
        }

        public void Init(DataProvider<List<Land>> landDataProvider, DataProvider<List<Tower>> towerDataProvider)
        {
            this.landDataProvider = landDataProvider;
            this.towerDataProvider = towerDataProvider;
        }

        public abstract void Do();
    }
}