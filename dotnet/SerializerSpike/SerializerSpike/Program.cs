using System.IO;
using KellermanSoftware.CompareNetObjects;
using Polenter.Serialization;
using YAXLib;

namespace SerializerSpike
{
    internal class Program
    {
        private static void Main(string[] args)
        {
            TryYax();
        }

        private static string CompareObjs(object o1, object o2)
        {
            var compareLogic = new CompareLogic();
            compareLogic.Config.MaxDifferences = 10;
            compareLogic.Config.CompareChildren = true;
            compareLogic.Config.ComparePrivateFields = true;
            compareLogic.Config.CompareFields = true;
            compareLogic.Config.MaxStructDepth = 10;
            var result = compareLogic.Compare(o1, o2);
            return result.DifferencesString;
        }

        private static void TryYax()
        {
            var obj = CreateFakeObject();
            obj.SetValues();

            var serializer = new YAXSerializer(typeof(Parameters),
                YAXExceptionHandlingPolicies.DoNotThrow,YAXExceptionTypes.Error,
                YAXSerializationOptions.DontSerializeCyclingReferences|YAXSerializationOptions.DontSerializeNullObjects|YAXSerializationOptions.DontSerializePropertiesWithNoSetter);
            var someString = serializer.Serialize(obj);
            File.WriteAllText("yax.xml", someString);

            var back = serializer.DeserializeFromFile("yax.xml");
        }

        private static void TrySharpSerializer(object obj)
        {
            var serializer = new SharpSerializer();
            serializer.Serialize(obj, "test.xml");
            var obj2 = serializer.Deserialize("test.xml");
        }

        private static Parameters CreateFakeObject()
        {
            return new Parameters();
        }
    }
}