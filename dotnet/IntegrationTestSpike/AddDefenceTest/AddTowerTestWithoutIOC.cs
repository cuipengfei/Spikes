using System.Collections.Generic;
using IntegrationTestSpike.WithoutIOC;
using IntegrationTestSpike.WithoutIOC.Calculators;
using IntegrationTestSpike.WithoutIOC.Providers;
using IntegrationTestSpike.WithoutIOC.Steps;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace AddDefenceTest
{
    [TestClass]
    public class AddTowerTestWithoutIOC
    {
        [TestMethod]
        public void WholeProcessTest()
        {
            GlobalContext.Instance.LastId = 10;
            GlobalContext.Instance.MinimumDefence = 5;
            var prepareStep = new PrepareStep(GlobalContext.Instance);
            prepareStep.Init(new ExcelDataProvider(), new DatFileDataProvider());

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

            prepareStep.Init(new ExcelDataProvider(), new DatFileDataProvider());

            Assert.AreEqual(null, GlobalContext.Instance.ExistingLands);
            prepareStep.Do();
            Assert.AreEqual(1, GlobalContext.Instance.ExistingLands.Count);

            GlobalContext.Instance.MinimumDefence = 5;
            Assert.AreEqual(0, GlobalContext.Instance.ExistingLands[0].Towers.Count);
            calculateStep.Do();
            Assert.AreEqual(1, GlobalContext.Instance.ExistingLands[0].Towers.Count);
        }
    }
}