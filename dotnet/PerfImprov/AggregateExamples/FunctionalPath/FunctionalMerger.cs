using System.Collections.Generic;
using System.Linq;
using AggregateExamples.Cards;

namespace AggregateExamples.FunctionalPath
{
    internal class FunctionalMerger
    {
        public static Company FunctionalMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            return smallCompanies.Aggregate(bigCompany,
                (buyer, seller) =>
                    new Company {EvaluatedMarketValue = buyer.EvaluatedMarketValue + seller.EvaluatedMarketValue});
        }
    }
}