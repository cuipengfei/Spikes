using System;
using NUnit.Framework;
using HowToGetCoverage;
using Rhino.Mocks;

namespace HowToGetCoverageTest
{
    [TestFixture]
    public class NumAdderTest
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
