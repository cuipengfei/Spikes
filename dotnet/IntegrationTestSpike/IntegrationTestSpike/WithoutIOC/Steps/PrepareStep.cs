namespace IntegrationTestSpike.WithoutIOC.Steps
{
    public class PrepareStep : BaseStep
    {
        public PrepareStep(GlobalContext context) : base(context)
        {
        }

        public override void Do()
        {
            _context.ExistingLands = landDataProvider.ReadData();
            _context.ExistingTowers = towerDataProvider.ReadData();
        }
    }
}