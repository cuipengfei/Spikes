using IntegrationTestSpike.WithoutIOC;
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
            var prep = new PrepareStep(GlobalContext.Instance);
            prep.Init(new ExcelDataProvider(), new DatFileDataProvider());

            Assert.AreEqual(null, GlobalContext.Instance.ExistingLands);
            prep.Do();
            Assert.AreEqual(1, GlobalContext.Instance.ExistingLands.Count);

            var calc = new CalculateStep(GlobalContext.Instance);

            GlobalContext.Instance.MinimumDefence = 5;
            Assert.AreEqual(0, GlobalContext.Instance.ExistingLands[0].Towers.Count);
            calc.Do();
            Assert.AreEqual(1, GlobalContext.Instance.ExistingLands[0].Towers.Count);
        }
    }
}