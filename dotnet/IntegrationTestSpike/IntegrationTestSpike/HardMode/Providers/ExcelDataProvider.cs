using System.Collections.Generic;
using IntegrationTestSpike.HardMode.Models;

namespace IntegrationTestSpike.HardMode.Providers
{
    internal class ExcelDataProvider : DataProvider<List<Land>>
    {
        public bool IsReadingWholeFile { get; set; }

        public override List<Land> ReadData()
        {
            if (IsReadingWholeFile)
            {
                return new List<Land>();
            }
            return new List<Land>
            {
                new Land {LowerRightY = 5, LowerRightX = 5, UpperLeftX = 1, UpperLeftY = 12, Towers = new List<Tower>()}
            };
        }
    }
}