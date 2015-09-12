using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bowling
{
    public class LastFrame:Frame
    {
        public int ThirdTry { get; set; }

        protected override int CalculateScore()
        {
            return FirstTry+SecondTry+ThirdTry;
        }
    }
}
