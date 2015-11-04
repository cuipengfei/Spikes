using System.Collections.Generic;
using IntegrationTestSpike.HardMode;
using IntegrationTestSpike.HardMode.Models;
using IntegrationTestSpike.HardMode.Steps;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace AddDefenceTest
{
    [TestClass]
    public class AddTowerTest
    {
        [TestMethod]
        public void ShouldAddTowerToDefencelessLands()
        {
            GlobalContext.Instance.ExistingLands = new List<Land>
            {
                new Land
                {
                    LowerRightX = 10,
                    LowerRightY = 10,
                    UpperLeftX = 1,
                    UpperLeftY = 20,
                    Towers = new List<Tower>()
                }
            };
            var calculateStep = new CalculateStep(GlobalContext.Instance);

            Assert.AreEqual(GlobalContext.Instance.ExistingLands[0].Towers.Count, 0); //before: no tower
            calculateStep.Do();
            Assert.AreEqual(GlobalContext.Instance.ExistingLands[0].Towers.Count, 1); //before: no tower
        }
    }
}