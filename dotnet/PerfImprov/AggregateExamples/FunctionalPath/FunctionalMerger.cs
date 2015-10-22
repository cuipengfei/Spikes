using System.Collections.Generic;
using System.Linq;

namespace AggregateExamples.FunctionalPath
{
    internal class FunctionalMerger
    {
        public static Company FunctionalMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            return smallCompanies.Aggregate(bigCompany,
                (buyer, seller) =>
                {
                    buyer.Merge(seller);
                    return buyer;
                });
        }
    }
}