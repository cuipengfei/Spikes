using System.Collections.Generic;
using System.Linq;

namespace AggregateExamples.FunctionalPath
{
    internal class FunctionalParallelMerger
    {
        public static Company FunctionalParallelMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            return smallCompanies
                .AsParallel()
                .Aggregate(CreateShell,
                    (shell, smallCompany) => shell.Merge(smallCompany),
                    (shell1, shell2) => shell1.Merge(shell2),
                    bigCompany.Merge);
        }

        private static Company CreateShell()
        {
            return new Company();
        }
    }
}