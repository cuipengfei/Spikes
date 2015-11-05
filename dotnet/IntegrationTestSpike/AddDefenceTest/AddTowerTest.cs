using System.Collections.Generic;
using IntegrationTestSpike.WithoutIOC;
using IntegrationTestSpike.WithoutIOC.Models;
using IntegrationTestSpike.WithoutIOC.Steps;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace AddDefenceTest
{
    [TestClass]
    public class AddTowerTest
    {
        [TestMethod]
        public void ShouldAddTowerToDefencelessLands()
        {
            //duplicate logic of prepare step, data provider, and launcher
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
            GlobalContext.Instance.MinimumDefence = 5;
            GlobalContext.Instance.LastId = 5;
            var calculateStep = new CalculateStep(GlobalContext.Instance);
            //duplicate logic of prepare step, data provider, and launcher

            Assert.AreEqual(0, GlobalContext.Instance.ExistingLands[0].Towers.Count); //before: no tower
            calculateStep.Do();
            Assert.AreEqual(1, GlobalContext.Instance.ExistingLands[0].Towers.Count); //after: one tower
            Assert.AreEqual("6", GlobalContext.Instance.ExistingLands[0].Towers[0].ID);
        }
    }
}