using System.Collections.Generic;
using IntegrationTestSpike.WithoutIOC.Calculators;
using IntegrationTestSpike.WithoutIOC.Providers;
using IntegrationTestSpike.WithoutIOC.Steps;

namespace IntegrationTestSpike.WithoutIOC
{
    public class Launcher
    {
        public static void Launch()
        {
            var steps = LoadSteps();
            steps.ForEach(step => step.Do());
        }

        private static List<BaseStep> LoadSteps()
        {
            GlobalContext.Instance.LastId = 10;
            GlobalContext.Instance.MinimumDefence = 5;
            var prepareStep = new PrepareStep(GlobalContext.Instance);
            prepareStep.Init(new ExcelDataProvider(), new DatFileDataProvider());

            var bigAttackCalculator = new BigAttackCalculator();
            var bigDefendsCalculator = new BigDefenceCalculator();

            var calculateStep = new CalculateStep(GlobalContext.Instance,
                new List<Calculator> {bigAttackCalculator, bigDefendsCalculator});
            var steps = new List<BaseStep>
            {
                prepareStep,
                calculateStep
            };
            return steps;
        }
    }
}