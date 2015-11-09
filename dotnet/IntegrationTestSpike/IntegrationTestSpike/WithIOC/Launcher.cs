using System.Collections.Generic;
using Autofac;
using Autofac.Configuration;
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
            builder.RegisterInstance(GlobalContext.Instance);
            builder.RegisterModule(new ConfigurationSettingsReader("autofac"));
            return builder.Build().BeginLifetimeScope();
        }

        private static List<BaseStep> FindSteps(ILifetimeScope scope)
        {
            return new List<BaseStep> {scope.Resolve<PrepareStep>(), scope.Resolve<CalculateStep>()};
        }
    }
}