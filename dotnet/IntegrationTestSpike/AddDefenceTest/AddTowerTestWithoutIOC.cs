using System.Collections.Generic;
using IntegrationTestSpike.WithIOC;
using IntegrationTestSpike.WithIOC.Calculators;
using IntegrationTestSpike.WithIOC.Providers;
using IntegrationTestSpike.WithIOC.Steps;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace AddDefenceTest
{
    [TestClass]
    public class AddTowerTestWithoutIOC
    {
        [TestMethod]
        public void WholeProcessTest()
        {
            var prepareStep = new PrepareStep(GlobalContext.Instance);
            prepareStep.LandDataProvider = new ExcelDataProvider();
            prepareStep.TowerDataProvider = new DatFileDataProvider();

            var bigAttackCalculator = new BigAttackCalculator();
            var bigDefendsCalculator = new BigDefenceCalculator();
            var miniAttackCalculator = new MiniAttackCalculator();
            var miniDefenceCalculator = new MiniDefenceCalculator();
            var normalTowerCalculator = new NormalTowerCalculator();

            var calculateStep = new CalculateStep(GlobalContext.Instance,
                new List<Calculator>
                {
                    bigAttackCalculator,
                    bigDefendsCalculator,
                    miniAttackCalculator,
                    miniDefenceCalculator,
                    normalTowerCalculator
                });

            //above is object creation

            Assert.AreEqual(null, GlobalContext.Instance.ExistingLands);
            prepareStep.Do();
            Assert.AreEqual(1, GlobalContext.Instance.ExistingLands.Count);

            GlobalContext.Instance.MinimumDefence = 5;
            Assert.AreEqual(0, GlobalContext.Instance.ExistingLands[0].Towers.Count);
            calculateStep.Do();
            Assert.AreEqual(23, GlobalContext.Instance.ExistingLands[0].Towers.Count);
        }

        [TestCleanup]
        public void CleanUp()
        {
            GlobalContext.Instance.ExistingLands = null;
            GlobalContext.Instance.ExistingTowers = null;
        }
    }
}