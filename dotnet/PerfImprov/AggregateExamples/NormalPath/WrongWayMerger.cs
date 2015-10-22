using System.Collections.Generic;
using System.Threading.Tasks;
using AggregateExamples.Cards;

namespace AggregateExamples.NormalPath
{
    internal class WrongWayMerger
    {
        public static Company WrongParallelMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            Parallel.ForEach(smallCompanies,
                smallCompany => { bigCompany.EvaluatedMarketValue += smallCompany.EvaluatedMarketValue; });
            return bigCompany;
        }
    }
}