using System.Collections.Generic;
using IntegrationTestSpike.WithoutIOC.Models;
using IntegrationTestSpike.WithoutIOC.Providers;

namespace IntegrationTestSpike.WithoutIOC.Steps
{
    public abstract class BaseStep
    {
        protected readonly GlobalContext _context;
        protected DataProvider<List<Land>> landDataProvider;
        protected DataProvider<List<Tower>> towerDataProvider;

        protected BaseStep(GlobalContext context)
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