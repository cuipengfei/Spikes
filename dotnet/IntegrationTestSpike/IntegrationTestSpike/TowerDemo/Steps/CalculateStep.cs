using System.Collections.Generic;
using System.Linq;
using IntegrationTestSpike.TowerDemo.Calculators;
using IntegrationTestSpike.TowerDemo.Models;

namespace IntegrationTestSpike.TowerDemo.Steps
{
    public class CalculateStep : BaseStep
    {
        private readonly int _height = 10;
        private readonly IEnumerable<Calculator> _calculators;

        public CalculateStep(GlobalContext context, IEnumerable<Calculator> calculators)
            : base(context)
        {
            _calculators = calculators;
        }

        public override void Do()
        {
            var defenceLessLands = _context.ExistingLands.Where(CanNotDefende).ToList();
            foreach (var calculator in _calculators)
            {
                AddTowers(defenceLessLands, calculator.Calculate(), calculator.TowerType);
            }
        }

        private void AddTowers(IEnumerable<Land> defenceLessLands, int num, TowerType type)
        {
            foreach (var defenceLessLand in defenceLessLands)
            {
                for (var i = 0; i < num; i++)
                {
                    defenceLessLand.Towers.Add(new Tower
                    {
                        Height = _height,
                        ID = (_context.LastId + 1).ToString(),
                        Type = type
                    });
                }
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