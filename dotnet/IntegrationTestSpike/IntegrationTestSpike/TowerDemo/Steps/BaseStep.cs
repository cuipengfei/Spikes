using IntegrationTestSpike.TowerDemo.Providers;

namespace IntegrationTestSpike.TowerDemo.Steps
{
    public abstract class BaseStep
    {
        protected readonly GlobalContext _context;

        protected BaseStep(GlobalContext context)
        {
            _context = context;
        }

        public ExcelDataProvider LandDataProvider { get; set; }
        public DatFileDataProvider TowerDataProvider { get; set; }

        public abstract void Do();
    }
}