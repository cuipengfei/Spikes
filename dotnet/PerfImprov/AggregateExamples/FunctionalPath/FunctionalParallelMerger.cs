using System.Collections.Generic;
using System.Linq;
using AggregateExamples.Cards;

namespace AggregateExamples.FunctionalPath
{
    internal class FunctionalParallelMerger
    {
        public static Company FunctionalParallelMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            return smallCompanies.AsParallel().Aggregate(bigCompany, (buyer, seller) =>
                new Company {EvaluatedMarketValue = buyer.EvaluatedMarketValue + seller.EvaluatedMarketValue});
        }
    }
}