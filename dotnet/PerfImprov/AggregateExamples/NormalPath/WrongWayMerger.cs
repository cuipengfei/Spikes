using System.Collections.Generic;
using System.Threading.Tasks;

namespace AggregateExamples.NormalPath
{
    internal class WrongWayMerger
    {
        public static Company WrongParallelMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            Parallel.ForEach(smallCompanies,
                smallCompany => bigCompany.Merge(smallCompany));
            return bigCompany;
        }
    }
}