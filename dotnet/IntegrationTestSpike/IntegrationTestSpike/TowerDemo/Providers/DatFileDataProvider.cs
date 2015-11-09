using System.Collections.Generic;
using IntegrationTestSpike.TowerDemo.Models;

namespace IntegrationTestSpike.TowerDemo.Providers
{
    public class DatFileDataProvider : DataProvider<List<Tower>>
    {
        public ReadingDatMode Mode { get; set; }

        public override List<Tower> ReadData()
        {
            if (Mode == ReadingDatMode.Hex)
            {
                return new List<Tower>();
            }
            return new List<Tower> {new Tower {Height = 5, ID = "4", Strenth = 15}};
        }
    }
}