using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HowToGetCoverage
{
    public interface Computer
    {
        int NumberOfCpus();
    }

    public class ComputerImpl : Computer
    {
        public int NumberOfCpus()
        {
            return 8;
        }
    }
}
