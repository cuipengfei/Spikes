namespace IntegrationTestSpike.WithIOC.Steps
{
    public class PrepareStep : BaseStep
    {
        public PrepareStep(GlobalContext context) : base(context)
        {
        }

        public override void Do()
        {
            _context.ExistingLands = LandDataProvider.ReadData();
            _context.ExistingTowers = TowerDataProvider.ReadData();
        }
    }
}