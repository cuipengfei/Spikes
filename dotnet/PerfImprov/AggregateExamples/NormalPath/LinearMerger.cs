using System.Collections.Generic;

namespace AggregateExamples.NormalPath
{
    internal class LinearMerger
    {
        public static Company LinearMerge(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            foreach (var smallCompany in smallCompanies)
            {
                bigCompany.Merge(smallCompany);
            }
            return bigCompany;
        }
    }
}