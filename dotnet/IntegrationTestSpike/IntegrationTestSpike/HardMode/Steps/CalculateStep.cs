using System.Linq;
using IntegrationTestSpike.HardMode.Models;

namespace IntegrationTestSpike.HardMode.Steps
{
    internal class CalculateStep : BaseStep
    {
        private readonly int _height = 10;

        public CalculateStep(GlobalContext context) : base(context)
        {
        }

        public override void Do()
        {
            var defenceLessLands = _context.ExistingLands.Where(CanNotDefende);
            foreach (var defenceLessLand in defenceLessLands)
            {
                defenceLessLand.Towers.Add(new Tower {Height = _height, ID = (_context.LastId + 1).ToString()});
            }
        }

        private bool CanNotDefende(Land land)
        {
            return DefenceLevel(land) < _context.MinimumDefence;
        }

        private static int DefenceLevel(Land land)
        {
            return land.Towers.Count/Area(land);
        }

        private static int Area(Land land)
        {
            return (land.LowerRightX - land.UpperLeftX)*(land.UpperLeftX - land.LowerRightY);
        }
    }
}