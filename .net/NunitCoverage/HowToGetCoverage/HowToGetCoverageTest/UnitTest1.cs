using System;
using NUnit.Framework;
using HowToGetCoverage;

namespace HowToGetCoverageTest
{
    [TestFixture]
    public class UnitTest1
    {
        [Test]
        public void shouldAddOneAndOne()
        {
            var adder = new NumAdder();
            var res = adder.Add(1, 1);
            Assert.AreEqual(res, 2);
        }
    }
}
