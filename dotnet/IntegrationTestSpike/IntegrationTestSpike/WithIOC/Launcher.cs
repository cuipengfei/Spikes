using System.Collections.Generic;
using Autofac;
using IntegrationTestSpike.WithIOC.Models;
using IntegrationTestSpike.WithIOC.Providers;
using IntegrationTestSpike.WithIOC.Steps;

namespace IntegrationTestSpike.WithIOC
{
    public class Launcher
    {
        public static void Launch()
        {
            var scope = RegisterComponents();
            var steps = FindSteps(scope);
            steps.ForEach(step => step.Do());
        }

        public static ILifetimeScope RegisterComponents()
        {
            var builder = new ContainerBuilder();
            GlobalContext.Instance.LastId = 10;
            GlobalContext.Instance.MinimumDefence = 5;

            builder.RegisterInstance(GlobalContext.Instance);

            builder.RegisterType<DatFileDataProvider>().As<DataProvider<List<Tower>>>();
            builder.RegisterType<ExcelDataProvider>().As<DataProvider<List<Land>>>();

            builder.RegisterType<PrepareStep>().PropertiesAutowired();
            builder.RegisterType<CalculateStep>().PropertiesAutowired();
            var scope = builder.Build().BeginLifetimeScope();
            return scope;
        }

        private static List<BaseStep> FindSteps(ILifetimeScope scope)
        {
            return new List<BaseStep> {scope.Resolve<PrepareStep>(), scope.Resolve<CalculateStep>()};
        }
    }
}