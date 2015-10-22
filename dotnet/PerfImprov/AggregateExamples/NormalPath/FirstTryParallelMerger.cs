using System.Collections.Generic;
using System.Threading.Tasks;

namespace AggregateExamples.NormalPath
{
    internal class FirstTryParallelMerger
    {
        public static Company FirstTryParallelMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            Parallel.ForEach(smallCompanies,
                smallCompany => bigCompany.Merge(smallCompany));
            return bigCompany;
        }
    }
}