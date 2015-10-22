using System.Collections.Generic;
using System.Linq;

namespace AggregateExamples.FunctionalPath
{
    internal class FunctionalParallelMerger
    {
        public static Company FunctionalParallelMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            var merged = smallCompanies.AsParallel()
                .Aggregate(() => new Company(), (buyer, seller) => buyer.Merge(seller),
                    (agg1, agg2) =>
                        agg1.Merge(agg2), f => f);
            return bigCompany.Merge(merged);
        }
    }
}