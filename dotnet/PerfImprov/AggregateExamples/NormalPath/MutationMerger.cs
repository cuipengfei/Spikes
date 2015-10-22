using System.Collections.Generic;

namespace AggregateExamples.NormalPath
{
    internal class MutationMerger
    {
        public static Company MergeByMutation(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            foreach (var smallCompany in smallCompanies)
            {
                bigCompany.Merge(smallCompany);
            }
            return bigCompany;
        }
    }
}