using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using NUnit.Framework;
using Rhino.Mocks;
using HowToGetCoverage;

namespace HowToGetCoverageTest
{
    [TestFixture]
    public class HowToMock
    {
        [Test]
        public void howToMockSomethingWithRihino()
        {
            var mockedComputer = MockRepository.GenerateMock<Computer>();
            mockedComputer.Stub(computer => computer.NumberOfCpus()).Return(16);

            Assert.AreEqual(mockedComputer.NumberOfCpus(), 16);
        }
    }
}
