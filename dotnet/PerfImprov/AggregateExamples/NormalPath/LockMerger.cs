using System.Collections.Generic;
using System.Threading.Tasks;

namespace AggregateExamples.NormalPath
{
    internal class LockMerger
    {
        public static Company MergeWithLock(Company bigCompany, IEnumerable<Company> smallCompanies)
        {
            var token = new object();
            Parallel.ForEach(smallCompanies, smallCompany =>
            {
                lock (token)
                {
                    bigCompany.Merge(smallCompany);
                }
            });
            return bigCompany;
        }
    }
}