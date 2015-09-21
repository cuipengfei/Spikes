using System.Linq;
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
            var reverseTwiceBeSameProp = Prop.ForAll<int[]>(xs => xs.Reverse().Reverse().SequenceEqual(xs));
            Check.QuickThrowOnFailure(reverseTwiceBeSameProp);
        }

        [Test]
        public void SelfDefinedPropTest()
        {
            Check.QuickThrowOnFailure(new CounterSpec().ToProperty());
        }
    }
}