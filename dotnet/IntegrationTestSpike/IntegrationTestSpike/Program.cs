using System;
using System.Configuration;
using IntegrationTestSpike.WithoutIOC;

namespace IntegrationTestSpike
{
    public class Program
    {
        private static void Main(string[] args)
        {
            var a =ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            Launcher.Launch();
            Console.ReadLine();
        }
    }
}