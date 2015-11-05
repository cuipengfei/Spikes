using System.Collections.Generic;
using IntegrationTestSpike.WithIOC.Models;
using IntegrationTestSpike.WithIOC.Providers;

namespace IntegrationTestSpike.WithIOC.Steps
{
    public abstract class BaseStep
    {
        protected readonly GlobalContext _context;
        public DataProvider<List<Land>> LandDataProvider { get; set; }
        public DataProvider<List<Tower>> TowerDataProvider { get; set; }

        protected BaseStep(GlobalContext context)
        {
            _context = context;
        }

//        public void Init(DataProvider<List<Land>> landDataProvider, DataProvider<List<Tower>> towerDataProvider)
//        {
//            this.LandDataProvider = landDataProvider;
//            this.TowerDataProvider = towerDataProvider;
//        }

        public abstract void Do();
    }
}