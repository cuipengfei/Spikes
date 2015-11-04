using System.Collections.Generic;
using IntegrationTestSpike.HardMode.Providers;
using IntegrationTestSpike.HardMode.Steps;

namespace IntegrationTestSpike.HardMode
{
    internal class Launcher
    {
        public static void Launch()
        {
            var steps = LoadSteps();
            steps.ForEach(step => step.Do());
        }

        private static List<BaseStep> LoadSteps()
        {
            var prepareStep = new PrepareStep(GlobalContext.Instance);
            prepareStep.Init(new ExcelDataProvider(), new DatFileDataProvider());
            var steps = new List<BaseStep>
            {
                prepareStep,
                new CalculateStep(GlobalContext.Instance)
            };
            return steps;
        }
    }
}