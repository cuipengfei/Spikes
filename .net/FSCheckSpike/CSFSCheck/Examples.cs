using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using FsCheck.NUnit.CSharpExamples.ClassesToTest;
using NUnit.Framework;

namespace FsCheck.NUnit.CSharpExamples
{
    [TestFixture]
    public class Examples
    {
        [Test]
        public void RevRev()
        {
            Prop.ForAll<int[]>(
                xs => xs.Reverse().Reverse()
                    .SequenceEqual(xs))
                .QuickCheckThrowOnFailure();
        }

        [Test]
        public void SelfDefinedPropTest()
        {
            new CounterSpec().ToProperty().QuickCheckThrowOnFailure();
        }
    }
}