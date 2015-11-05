using System.Collections.Generic;
using IntegrationTestSpike.WithIOC;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Autofac;
using IntegrationTestSpike.WithIOC.Steps;

namespace AddDefenceTest
{
    [TestClass]
    public class AddTowerTestWithIOC
    {
        [TestMethod]
        public void ShouldAddTowerToDefencelessLands()
        {
            var step = Launcher.RegisterComponents().Resolve<CalculateStep>();

            Assert.AreEqual(0, GlobalContext.Instance.ExistingLands[0].Towers.Count); //before: no tower
            step.Do();
            Assert.AreEqual(1, GlobalContext.Instance.ExistingLands[0].Towers.Count); //after: one tower
            Assert.AreEqual("6", GlobalContext.Instance.ExistingLands[0].Towers[0].ID);
        }
    }
}