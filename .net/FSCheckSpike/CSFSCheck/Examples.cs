using System.Linq;
using FsCheck.NUnit.CSharpExamples.ClassesToTest;
using NUnit.Framework;

namespace FsCheck.NUnit.CSharpExamples
{
    [TestFixture]
    public class Examples
    {
        //Simple boolean property
        public static bool RevRev(int[] xs)
        {
            return xs.Reverse().Reverse().SequenceEqual(xs);
        }

        // Note: should fail
        public static Property DifferentWayToWriteRevRev()
        {
            return Prop.ForAll<int[]>(xs => xs.Reverse().Reverse().SequenceEqual(xs));
        }

        //TODO: do not call toProperty.
        // Note this one should fail
        public static Property Counter_shouldFail()
        {
            return new CounterSpec().ToProperty();
        }

//        public static Property CounterShouldIncreaseGivenTimes()
//        {
//            return Prop.ForAll<int>(times =>
//            {
//                if (times >= 0)
//                {
//                    var counter = new Counter();
//                    for (int i = 0; i < times; i++)
//                    {
//                        counter.Inc();
//                    }
//                    return counter.Get() == times;
//                }
//                return true;
//            });
//        }

        [Test]
        public void all()
        {
            Check.QuickThrowOnFailureAll<Examples>();
        }
    }
}