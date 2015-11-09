using Autofac;
using Autofac.Configuration;
using IntegrationTestSpike.TowerDemo;
using IntegrationTestSpike.TowerDemo.Steps;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace AddDefenceTest
{
    [TestClass]
    public class AddTowerTestWithIOC
    {
        [TestMethod]
        public void WholeProcessTest()
        {
            var scope = RegisterComponents();
            var prep = scope.Resolve<PrepareStep>();
            var calc = scope.Resolve<CalculateStep>();

            prep.Do();
            Assert.AreEqual(1, GlobalContext.Instance.ExistingLands.Count);

            Assert.AreEqual(0, GlobalContext.Instance.ExistingLands[0].Towers.Count);
            calc.Do();
            Assert.AreEqual(23, GlobalContext.Instance.ExistingLands[0].Towers.Count);
        }

        public static ILifetimeScope RegisterComponents()
        {
            var builder = new ContainerBuilder();
            builder.RegisterInstance(GlobalContext.Instance);
            builder.RegisterModule(new ConfigurationSettingsReader("autofac"));
            return builder.Build().BeginLifetimeScope();
        }

        [TestCleanup]
        public void CleanUp()
        {
            GlobalContext.Instance.ExistingLands = null;
            GlobalContext.Instance.ExistingTowers = null;
        }
    }
}