using System.Collections.Generic;
using AggregateExamples.Cards;

namespace AggregateExamples.NormalPath
{
    internal class MutationMerger
    {
        public static Company MergeByMutation(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            foreach (var smallCompany in smallCompanies)
            {
                bigCompany.EvaluatedMarketValue += smallCompany.EvaluatedMarketValue;
            }
            return bigCompany;
        }
    }
}